import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ObservableInput, forkJoin, map } from 'rxjs';
import { GlobalConfig as config } from 'src/Utils/config/config';
import { NotificationServiceService } from 'src/app/shared/services/notification-service.service';
import { otmmServicesConstants } from 'src/Utils/config/constants';
import Cookie from 'js-cookie';
import { environment as env } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class OtmmService {
  otdsTicket: any;
  public jSession!: string;
  otdsToken: any;

  constructor(
    private http: HttpClient,
    private notificationService: NotificationServiceService
  ) {}

  createSession(url: string) {
    let otdsTicket = '';
    if (config.config.environment !== 'local') {
      otdsTicket = this.otdsTicket;
    } else {
      Cookie.get('OTDSTicket');
    }

    const httpOptions = {
      headers: new HttpHeaders()
        .set('Access-Control-Allow-Origin', '*')
        .set('Access-Control-Allow-Methods', 'OPTIONS,GET,POST,PUT,DELETE'),
      withCredentials: true,
      params: new HttpParams().append('OTDSTicket', this.otdsTicket),
    };
    return this.http
      .get(url + config.config.version + '/sessions', httpOptions)
      .pipe(
        map((response: any) => {
          this.jSession = response.session_resource.session.id;

          return response;
        })
      );
  }

  postSession() {
    let baseUrl: string = env.otmmHost + env.version + '/sessions';

    let data = new URLSearchParams();
    data.set('username', 'tsuper');
    data.set('password', 'Cordys@1234');
    data.set('withCredentials', 'true');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
      }),
      withCredentials: true,
    };
    return this.http.post(baseUrl, data.toString(), httpOptions).pipe(
      map((response: any) => {
        // this.jSession = response.session_resource.session.id;
        // console.log(this.jSession,'jsessjion')
        return response;
      })
    );
  }

  getSessioons() {
    let baseUrl: string = 'otmmapi/v6/sessions';
    const httpOptions = {
      headers: new HttpHeaders().set(
        'Content-Type',
        'application/x-www-form-urlencoded'
      ),
      withCredentials: true,
    };
    return this.http.get(baseUrl, httpOptions).pipe(
      map((response: any) => {
        this.jSession = response.session_resource.session.id;
        console.log(this.jSession, 'jsessjion in post session');
        return response;
      })
    );
  }

  findIconByName(fileName: any) {
    const unknownIcon = 'assets/images/mime_unknown96.png';
    const textIcon = 'assets/images/mime_text96.png';
    const officeIcon = 'assets/images/mime_office96.png';
    const xmlIcon = 'assets/images/mime_code96.png';
    const imageIcon = 'assets/images/mime_image96.png';
    const videoIcon = 'assets/images/mime_video96.png';
    const audioIcon = 'assets/images/mime_audio96.png';
    const pdfIcon = 'assets/images/mime_pdf96.png';
    const zipIcon = 'assets/images/mime_zip96.png';
    const layoutAIIcon = 'assets/images/mime_layout96.png';
    const dimensionIcon = 'assets/images/mime_3d_object96.png';

    let fileExt = this.findExtensionByFileName(fileName);

    if (fileExt === 'PDF') {
      return pdfIcon;
    } else if (
      fileExt === 'DOC' ||
      fileExt === 'DOCX' ||
      fileExt === 'EPS' ||
      fileExt === 'PPTX' ||
      fileExt === 'PPT' ||
      fileExt === 'XLS' ||
      fileExt === 'XLSX'
    ) {
      return officeIcon;
    } else if (
      fileExt === 'JPG' ||
      fileExt === 'JPEG' ||
      fileExt === 'PNG' ||
      fileExt === 'GIF' ||
      fileExt === 'NEF' ||
      fileExt === 'TIF' ||
      fileExt === 'BMP' ||
      fileExt === 'EPS' ||
      fileExt === 'CR2' ||
      fileExt === 'TGA' ||
      fileExt === 'DNG' ||
      fileExt == 'SVG' ||
      fileExt == 'PSD' ||
      fileExt == 'ICO'
    ) {
      return imageIcon;
    } else if (
      fileExt === 'MP4' ||
      fileExt === 'AVI' ||
      fileExt === 'MKV' ||
      fileExt === 'MOV' ||
      fileExt === 'FLV' ||
      fileExt === 'WMV' ||
      fileExt === 'MPEG' ||
      fileExt === '3GP'
    ) {
      return videoIcon;
    } else if (fileExt === 'TXT') {
      return textIcon;
    } else if (
      fileExt === 'AAC' ||
      fileExt === 'FLAC' ||
      fileExt === 'M4A' ||
      fileExt === 'WAV' ||
      fileExt === 'WMA' ||
      fileExt === 'MPG'
    ) {
      return videoIcon;
    } else if (
      fileExt === 'MP3' ||
      fileExt === 'AIF' ||
      fileExt === 'AIFF' ||
      fileExt === 'AIFC' ||
      fileExt === 'WAV' ||
      fileExt === 'WMA' ||
      fileExt === 'SND'
    ) {
      return audioIcon;
    } else if (fileExt === 'ZIP') {
      return zipIcon;
    } else if (fileExt === 'AI' || fileExt === 'INDD') {
      return layoutAIIcon;
    } else if (fileExt === 'DAE' || fileExt === 'OBJ') {
      return dimensionIcon;
    } else if (fileExt === 'XML') {
      return xmlIcon;
    } else {
      return unknownIcon;
    }
  }

  findExtensionByFileName(fileName: any) {
    if (!fileName || (fileName != null && fileName !== '')) {
      const fileRegx: any = /(?:\.([^.]+))?$/;
      let fileExt = fileRegx.exec(fileName)[1];

      if (fileExt && fileExt != null && fileExt !== '') {
        fileExt = fileExt.toUpperCase();
      }
      return fileExt;
    }
  }

  startUpload(files: any, data: any, isRevision: any): Observable<any> {
    return new Observable((observer) => {
      const uploadType = isRevision ? 'UPLOAD_REVISION' : 'UPLOAD';
      //const metadata = this.categoryService.getCategoryLevelDetailsByType(MPM_LEVELS.ASSET);
      const metadata = {
        METADATA_MODEL_ID: '',
        TEMPLATE_ID: '',
        SECURITY_POLICY_IDS: [],
      };

      const eventData = {
        otmmFieldValues: data.assetData.metadata,
        metadataModel: data.assetData.metadata_model_id,
        templateid: data.assetData.template_id,
        securityPolicyID: data.assetData.security_policy_list,
        folderId: data.assetData.folderId,
        data: data,
      };
      console.log('dede', eventData);
      if (eventData.folderId) {
        if (!isRevision) {
          this.uploadFilesViaHTTP(files, eventData).subscribe({
            next: (uploadResponse: { processId: any }) => {
              const job = {
                process_instance_id: uploadResponse.processId,
              };
              observer.next(uploadResponse);
            },
            error: (uploadError: any) => {
              observer.error(uploadError);
            },
          });
          console.log('dede', eventData);
        } else {
          this.assetCheckout([data.assetData.assetId]) //this.checkOutAsset([data.asset.asset_id])
            .subscribe({
              next: (checkOutAssetResponse: any) => {
                this.uploadVersionViaHTTP(files).subscribe({
                  next: (jobId: any) => {
                    const checkInData = {
                      assetId: data.assetData.assetId,
                      name: files[0].name,
                    };

                    // eventData.folderId
                    this.assetCheckIn(checkInData, jobId).subscribe({
                      next: (uploadDetails: any) => {
                        const job = {
                          process_instance_id: uploadDetails.processId,
                        };
                        observer.next(uploadDetails);
                      },
                      error: (checkInAssetError: any) => {
                        observer.error(checkInAssetError);
                      },
                    });
                  },
                  error: (uploadVersionsViaHTTPError: any) => {
                    observer.error(uploadVersionsViaHTTPError);
                  },
                });
              },
              error: (checkOutAssetError: any) => {
                observer.error(checkOutAssetError);
              },
            });
        }
      } else {
        observer.error('No folder found to upload the asset(s).');
      }
    });
  }

  uploadFilesViaHTTP(
    filesToUpload: any,
    eventData: any,
    isRevision?: any
  ): Observable<any> {
    return new Observable((observer) => {
      this.customImport(filesToUpload[0].name).subscribe({
        next: (createOTMMJobResponse: any) => {
          console.log(createOTMMJobResponse);
          const jobId = createOTMMJobResponse['job_handle'].job_id;
          this.assetsRendition(filesToUpload, jobId).subscribe({
            next: () => {
              this.importOTMMJob(
                filesToUpload,
                eventData.otmmFields,
                eventData.otmmFieldValues,
                eventData.folderId,
                eventData.templateid,
                eventData.metadataModel,
                eventData.securityPolicyID,
                jobId,
                isRevision
              ).subscribe(
                (importOTMMJobResponse: any) => {
                  eventData.files = filesToUpload;
                  eventData.processId =
                    importOTMMJobResponse['job_handle'].job_id;
                  if (eventData && eventData.data) {
                    if (eventData.processId && eventData.files) {
                      //   this.qdsService.createQDSImportJob(eventData.processId, this.otmmService.getFilePaths(eventData.files));

                      observer.next(eventData);
                    } else {
                      observer.error(
                        ' abc Something went wrong while uploading the file(s).'
                      );
                    }
                  } else {
                    observer.error(
                      ' vvvv Something went wrong while uploading the file(s).'
                    );
                  }
                },
                (importOTMMJobError: any) => {
                  observer.error(
                    ' aaaa Something went wrong while uploading the file(s).'
                  );
                }
              );
            },
            error: () => {
              observer.error(
                ' aaahg Something went wrong while creating jobs to upload the file(s).'
              );
            },
          });
        },
        error: (error: any) => {
          console.log(error);
        },
      });
    });
  }

  customImport(fileName: string) {
    const baseUrl = env.otmmHost + env.version + '/jobs/imports';
    const formData = new FormData();
    formData.append('file_name', fileName);
    const urlParam =
      'file_name=' + encodeURIComponent(JSON.stringify(formData));
    // urlParam='file_name: Acheron_logo.png
    console.log(this.jSession);
    Cookie.set('jsession', this.jSession);
    const httpOptions = {
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        'X-Requested-By': this.jSession.toString(),
      }),
    };
    if (this.jSession.toString === null || this.jSession.toString.length == 0) {
      console.log('jsession error');
    }

    return this.http.post(baseUrl, urlParam, httpOptions).pipe(
      map((response: any) => {
        return response;
      })
    );
  }

  firePostRequest(
    url: any,
    parameters: any,
    contentType: any
  ): Observable<any> {
    const httpOptions = {
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': contentType,
        'X-Requested-By': this.jSession.toString(),
      }),
    };
    let s = this.formRestUrl(url, null, false);
    return this.http.post(
      this.formRestUrl(url, null, false),
      parameters,
      httpOptions
    );
  }

  assetsRendition(files: any, importJobID: any) {
    const promises: any = [];
    for (const file of files) {
      promises.push(this.assetRendition(file, importJobID));
    }
    return new Observable((observer) => {
      forkJoin(promises).subscribe({
        next: (response: any) => {
          observer.next(response);
          observer.complete();
        },
        error: (error) => {
          observer.error(error);
        },
      });
    });
  }

  assetRendition(file: any, importJobID: any) {
    const url = env.otmmHost + env.version + '/renditions';
    const param = new FormData();
    param.append('import_job_id', importJobID);
    param.append('file', file);
    param.append('file_name', file.name);
    Cookie.set('jsession', this.jSession);
    const httpOptions = {
      withCredentials: true,
      headers: new HttpHeaders({
        'X-Requested-By': this.jSession.toString(),
      }),
    };
    return this.http.post(url, param, httpOptions).pipe(
      map((response: any) => {
        return response;
      })
    );
  }

  importOTMMJob(
    files: string | any[] | any,
    metadataFields: any,
    metaDataFieldValues: any,
    parentForlderID: string | Blob | any,
    importTemplateID: any,
    metadataModel: any,
    securityPolicy: any,
    importJobId: string | Blob,
    isRevision?: any
  ): any {
    const url = env.otmmHost + env.version + '/jobs/imports/' + importJobId;
    const param = new FormData();
    let blob = null;
    let content = null;
    if (
      files &&
      Array.isArray(files) &&
      metaDataFieldValues &&
      parentForlderID &&
      importTemplateID
    ) {
      for (let i = 0; i < files.length; i++) {
        content = JSON.stringify(
          this.formAssetRepresentation(
            metaDataFieldValues,
            metadataModel,
            securityPolicy
          )
        );
        blob = new Blob([content], {
          type: otmmServicesConstants.CONTENT_TYPE.JSONDATA,
        });
        if (!isRevision) {
          param.append('asset_representation', blob);
          param.append('parent_folder_id', parentForlderID);
        }
        param.append('import_job_id', importJobId);
        param.append('file_name', files[i].name);

        if (isRevision) {
          content = JSON.stringify(this.createUploadManifestForRevision(files));
        } else {
          content = JSON.stringify(this.createUploadManifest(files));
        }
        blob = new Blob([content], {
          type: otmmServicesConstants.CONTENT_TYPE.JSONDATA,
        });
        param.append('manifest', blob);
      }
      const httpOptions = {
        withCredentials: true,
        headers: new HttpHeaders({
          //   'Content-Type': 'application/x-www-form-urlencoded',
          'X-Requested-By': this.jSession.toString(),
        }),
      };
      return this.http.put(url, param, httpOptions).pipe(
        map((response: any) => {
          return response;
        })
      );
    } else {
      console.error(
        'Unable to upload assets:\nbecause the parameters are either incorrect or undefined.'
      );
      return null;
    }
  }

  createUploadManifestForRevision(files: any) {
    let uploadManifest = null;
    if (files) {
      uploadManifest = {
        upload_manifest: {
          master_files: [
            {
              file: {
                file_name: files[0].name,
              },
              uoi_id: files[0].assetId,
            },
          ],
        },
      };
    } else {
      const masterFiles = new Array();
      for (let i = 0; i < files.length; i++) {
        masterFiles.push({
          file: {
            file_name: files[i].name,
          },
          uoi_id: files[i].assetId,
        });
      }
      uploadManifest = {
        upload_manifest: {
          master_files: masterFiles,
        },
      };
    }
    return uploadManifest;
  }

  createUploadManifest(files: any) {
    let uploadManifest = null;
    if (files) {
      if (files.length === 1) {
        uploadManifest = {
          upload_manifest: {
            master_files: [
              {
                file: {
                  file_name: files[0].name,
                  file_type: files[0].type,
                },
              },
            ],
          },
        };
      } else {
        const masterFiles = new Array();
        for (let i = 0; i < files.length; i++) {
          masterFiles.push({
            file: {
              file_name: files[i].name,
              file_type: files[i].type,
            },
          });
        }
        uploadManifest = {
          upload_manifest: {
            master_files: masterFiles,
          },
        };
      }
    } else {
      throw 'Unable to create upload manifest:\n parameter files is either undefined or invalid.';
    }
    return uploadManifest;
  }

  formRestUrl(otmmapiurl: any, urlparam: any, isRendition?: any) {
    //const baseUrl = isDevMode() ? './' : this.sharingService.getMediaManagerConfig().url;
    //   const baseUrl = config.config.otmmHost;
    let baseUrl = 'https://micromm.acheron-tech.com:11090';

    if (isRendition === true) {
      return baseUrl + otmmapiurl;
    } else {
      return (
        baseUrl +
        config.config?.version +
        '/' +
        otmmapiurl +
        (urlparam != null ? this.formRestParams(urlparam) : '')
      );
    }
  }

  formRestParams(parameters: any) {
    let param = '?';
    for (let i = 0; i < parameters.length; i++) {
      if (parameters[i]['key'] === 'json') {
        param +=
          i === 0
            ? parameters[i].value.key +
              '=' +
              encodeURIComponent(JSON.stringify(parameters[i].value.value))
            : '&' +
              parameters[i].value.key +
              '=' +
              encodeURIComponent(JSON.stringify(parameters[i].value.value));
      } else {
        param +=
          i === 0
            ? parameters[i].key + '=' + parameters[i].value
            : '&' + parameters[i].key + '=' + parameters[i].value;
      }
    }
    return param;
  }

  formAssetRepresentation(
    metadata: any,
    metaDataModel: any,
    securityPolicyID: any
  ) {
    let assetRepresentation = null;
    const metadataElementList = new Array();
    const securityPolicies = [];

    if (
      metadata &&
      metaDataModel &&
      metaDataModel != null &&
      metaDataModel !== ''
    ) {
      const defaultSecPolicyId = 1;
      if (
        securityPolicyID &&
        securityPolicyID != null &&
        securityPolicyID.length > 0
      ) {
        const ids = securityPolicyID;
        for (const id of ids) {
          const tempObj: any = {};
          tempObj['id'] = Number(id);
          securityPolicies.push(tempObj);
        }
      } else {
        let policy: any = {
          id: defaultSecPolicyId,
        };
        securityPolicies.push(policy);
        policy = null;
      }

      let metadataModelId = '';
      if (
        metaDataModel &&
        metaDataModel != null &&
        metaDataModel.trim() !== ''
      ) {
        metadataModelId = metaDataModel;
      } else {
        throw new Error('Metadata ModelID is missing.');
      }

      //   if (metadata && metadata.metadata_element_list && Array.isArray(metadata.metadata_element_list)) {
      //       for (const metadataGroup of metadata.metadata_element_list) {
      //           for (const metadataElement of metadataGroup.metadata_element_list) {
      //               if (metadataElement && metadataElement.id === 'MPM.UTILS.DATA_TYPE' && metadataElement.value && metadataElement.value.value) {
      //                   metadataElement.value.value.value = 'ASSET';
      //               }
      //               metadataElementList.push(metadataElement);
      //           }
      //       }
      //   }

      if (
        metadata &&
        metadata.metadata_element_list &&
        Array.isArray(metadata.metadata_element_list)
      ) {
        for (const metadataGroup of metadata.metadata_element_list) {
          metadataElementList.push(metadataGroup);
        }
      }

      assetRepresentation = {
        asset_resource: {
          asset: {
            metadata: {
              metadata_element_list: metadataElementList,
            },
            metadata_model_id: metadataModelId,
            security_policy_list: securityPolicies,
          },
        },
      };
    } else {
      console.error(`Unable to create asset representation:
      the parameters metadataFields & metaDataFieldValues are not correct or undefined.`);
    }
    return assetRepresentation;
  }

  assetCheckout(assetIds: Array<string>) {
    return new Observable((observer) => {
      if (!assetIds || !assetIds.length || assetIds.length === 0) {
        this.notificationService.error(
          'No asset id(s) are available to check out.'
        );
        observer.next(false);
        observer.complete();
      }

      const urlParam = [
        {
          key: 'action',
          value: 'check_out',
        },
        {
          key: 'json',
          value: {
            key: 'selection_context',
            value: {
              selection_context_param: {
                selection_context: {
                  asset_ids: assetIds,
                  type: 'com.artesia.asset.selection.AssetIdsSelectionContext',
                  include_descendants: 'IMMEDIATE',
                },
              },
            },
          },
        },
      ];

      const url = this.formRestUrl('assets/state', null, false);
      const httpOptions = {
        withCredentials: true,
        // headers: new HttpHeaders({
        //     //'X-Requested-By': otmmServicesConstants.OTMM_SERVICE_VARIABLES.userSessionId.toString(),
        //     'X-Requested-By': this.authenticationService.jSession.toString(),
        //     'Content-Type': otmmServicesConstants.CONTENT_TYPE.URLENCODED
        // }),
        transformRequest: 'xWwwFormUrlencoded',
      };
      this.doPutRequest(
        url,
        this.formRestParams(urlParam).substring(1),
        httpOptions,
        false
      ).subscribe((response: any) => {
        if (
          response &&
          response['bulk_asset_result_representation'] &&
          response['bulk_asset_result_representation'].bulk_asset_result &&
          response['bulk_asset_result_representation'].bulk_asset_result
        ) {
          if (
            response['bulk_asset_result_representation'].bulk_asset_result
              .failed_object_list &&
            response['bulk_asset_result_representation'].bulk_asset_result
              .failed_object_list.length &&
            response['bulk_asset_result_representation'].bulk_asset_result
              .failed_object_list.length > 0
          ) {
            observer.error(
              new Error(
                'Unable to check out ' +
                  response['bulk_asset_result_representation'].bulk_asset_result
                    .failed_object_list.length +
                  ' assets.'
              )
            );
          } else if (
            response['bulk_asset_result_representation'].bulk_asset_result
              .successful_object_list &&
            response['bulk_asset_result_representation'].bulk_asset_result
              .successful_object_list.length &&
            response['bulk_asset_result_representation'].bulk_asset_result
              .successful_object_list.length > 0 &&
            // tslint:disable-next-line: max-line-length
            assetIds.length ===
              response['bulk_asset_result_representation'].bulk_asset_result
                .successful_object_list.length
          ) {
            observer.next(response);
            observer.complete();
          } else {
            observer.error(
              new Error('Unable to check out ' + assetIds.length + ' assets.')
            );
          }
        } else {
          observer.error(new Error('Invalid Asset Ids.'));
        }
      });
    });
  }

  assetCheckIn(assetDetail: any, importJobID: any) {
    const url = this.formRestUrl(
      otmmServicesConstants.importJobUrl + '/' + importJobID,
      null,
      false
    );
    const param = new FormData();
    let blob = null;
    let content = null;
    param.append('import_job_id', importJobID);
    let uploadManifest = null;
    uploadManifest = {
      upload_manifest: {
        master_files: [
          {
            file: {
              file_name: assetDetail.name,
            },
            uoi_id: assetDetail.assetId,
          },
        ],
      },
    };
    param.append('file_name', assetDetail.name);
    content = uploadManifest;
    blob = new Blob([JSON.stringify(content)], {
      type: otmmServicesConstants.CONTENT_TYPE.JSONDATA,
    });
    param.append('manifest', blob);

    return this.doPutRequest(url, param, false, false);
  }

  doPutRequest(
    url: any,
    parameters: any,
    headerObject: any,
    isOnlyResponse: any
  ) {
    return new Observable((observer) => {
      if (headerObject) {
        this.http.put(url, parameters, headerObject).subscribe(
          (response) => {
            observer.next(response);
            observer.complete();
          },
          (error) => {
            observer.error(new Error(error));
          }
        );
      } else {
        const httpOptions = {
          withCredentials: true,
          // headers: new HttpHeaders({
          //     'X-Requested-By': this.authenticationService.jSession.toString(),
          // })
        };
        this.http.put(url, parameters, httpOptions).subscribe(
          (response) => {
            observer.next(response);
            observer.complete();
          },
          (error) => {
            observer.error(new Error(error));
          }
        );
      }
    });
  }

  uploadVersionViaHTTP(files: any): Observable<any> {
    return new Observable((observer) => {
      this.assetImport(files[0].name).subscribe(
        (assetImportResponse: any) => {
          const jobId = assetImportResponse['job_handle'].job_id;
          this.assetsRendition(files, jobId).subscribe(
            (assetsRenditionResponse) => {
              observer.next(jobId);
            },
            (assetsRenditionError) => {
              observer.error(assetsRenditionError);
            }
          );
        },
        (assetImportError: any) => {
          observer.error(assetImportError);
        }
      );
    });
  }

  assetImport(fileName: any) {
    const url = this.formRestUrl(
      otmmServicesConstants.importJobUrl,
      null,
      false
    );
    let param;
    if (fileName) {
      param = this.formRestParams([
        {
          key: 'file_name',
          value: fileName,
        },
      ]);
    }
    return this.doPostRequest(url, param, false, false);
  }

  lockAssets(assetIds: Array<string>) {
    const urlParam = [
      {
        key: 'action',
        value: 'lock',
      },
      {
        key: 'json',
        value: {
          key: 'selection_context',
          value: {
            selection_context_param: {
              selection_context: {
                asset_ids: assetIds,
                type: 'com.artesia.asset.selection.AssetIdsSelectionContext',
                include_descendants: 'NONE',
              },
            },
          },
        },
      },
    ];
    const url = this.formRestUrl('assets/state', null, false);
    return this.doPutRequest(
      url,
      this.formRestParams(urlParam).substring(1),
      {
        withCredentials: true,
        // headers: new HttpHeaders({
        //     //'X-Requested-By': otmmServicesConstants.OTMM_SERVICE_VARIABLES.userSessionId.toString(),
        //     'X-Requested-By': this.authenticationService.jSession.toString(),
        //     'Content-Type': otmmServicesConstants.CONTENT_TYPE.URLENCODED
        // })
      },
      false
    );
  }

  unlockAssets(assetIds: Array<string>) {
    const urlParam = [
      {
        key: 'action',
        value: 'unlock',
      },
      {
        key: 'json',
        value: {
          key: 'selection_context',
          value: {
            selection_context_param: {
              selection_context: {
                asset_ids: assetIds,
                type: 'com.artesia.asset.selection.AssetIdsSelectionContext',
                include_descendants: 'NONE',
              },
            },
          },
        },
      },
    ];
    const url = this.formRestUrl('assets/state', null, false);
    return this.doPutRequest(
      url,
      this.formRestParams(urlParam).substring(1),
      {
        withCredentials: true,
        //   headers: new HttpHeaders({
        //       //'X-Requested-By': otmmServicesConstants.OTMM_SERVICE_VARIABLES.userSessionId.toString(),
        //       'X-Requested-By': this.authenticationService.jSession.toString(),
        //       'Content-Type': otmmServicesConstants.CONTENT_TYPE.URLENCODED
        //   })
      },
      false
    );
  }

  checkOutAsset(filesToCheckOut: any): Observable<any> {
    return new Observable((observer) => {
      this.assetCheckout(filesToCheckOut).subscribe(
        (assetCheckoutResponse) => {
          this.lockAssets(filesToCheckOut).subscribe(
            (lockAssetResponse: any) => {
              observer.next(lockAssetResponse);
            },
            (lockAssetError: any) => {
              observer.error(lockAssetError);
            }
          );
        },
        (assetCheckoutError) => {
          observer.error(assetCheckoutError);
        }
      );
    });
  }

  checkInAsset(
    filesToCheckIn: any,
    JobId: any,
    folderId: any
  ): Observable<any> {
    return new Observable((observer) => {
      this.assetCheckIn(filesToCheckIn, JobId).subscribe(
        (response: any) => {
          // const uploadDetails = {
          //     files: filesToCheckIn,
          //     folderId: folderId,
          //     processId: response && this.utilService.isValid(response['job_handle'].process_instance_id) ?
          //         response['job_handle'].process_instance_id : response['job_handle'].job_id
          // };
          // observer.next(uploadDetails);
        },
        (error) => {
          observer.error(error);
        }
      );
    });
  }

  doPostRequest(
    url: any,
    parameters: any,
    headerObject: any,
    isOnlyResponse: any
  ) {
    return new Observable((observer) => {
      if (headerObject && headerObject != null) {
        this.http.post(url, parameters, headerObject).subscribe({
          next: (response: any) => {
            observer.next(response);
            observer.complete();
          },
          error: (error: any) => {
            observer.error(new Error(error));
          },
        });
      } else {
        const headerOptions = {
          withCredentials: true,
          headers: new HttpHeaders({
            'X-Requested-By': this.jSession.toString(),
          }),
        };

        this.http.post(url, parameters, headerOptions).subscribe({
          next: (response) => {
            observer.next(response);
            observer.complete();
          },
          error: (error) => {
            observer.error(new Error(error));
          },
        });
      }
    });
  }
  otmmMetadataSearch(
    searchConfigId: any,
    startIndex: any,
    endIndex: any,
    BucketName: any
  ) {
    const baseUrl =
      env.otmmHost + env.version + otmmServicesConstants.textAssetSearchUrl;
    let searchConditions = [];
    let keywordScopeId = env.keyword_scope_id;

    searchConditions.push({
      metadata_field_id: 'MCU_DETAILS_BUCKET_NAME',
      type: 'com.artesia.search.SearchFulltextCondition',
      keyword: BucketName,
      scope_id: keywordScopeId,
      relational_operator_name: 'Metadata and File Content',
    });

    let search_condition_list = {
      search_condition_list: {
        search_condition: searchConditions,
      },
    };
    console.log(searchConditions);

    let urlParam = '';
    urlParam +=
      'load_type=metadata&' +
      'metadata_to_return=' +
      otmmServicesConstants.copyAssetToFolder.originalParentMetadataField +
      '&' +
      'after=' +
      startIndex +
      '&' +
      'limit=' +
      endIndex +
      '&' +
      'load_multilingual_values=true&' +
      'level_of_detail=slim&' +
      'multilingual_language_code=en_US&' +
      'preference_id=' +
      otmmServicesConstants.preferenceID.GALLERY_VIEW +
      '&' +
      'search_config_id=' +
      searchConfigId +
      '&' +
      'folder_filter_type=direct' +
      '&' +
      'folder_filter=' +
      env.folder_id +
      '&' +
      'search_condition_list=' +
      encodeURIComponent(JSON.stringify(search_condition_list));
    console.log('urlParam --> ', urlParam);
    console.log(`Base Url: `, baseUrl);
    const httpOptions = {
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded',
        'X-Requested-By': this.jSession.toString(),
      }),
    };
    if (this.jSession.toString() === null || this.jSession.toString() == '') {
      console.log('jsession error');
    }
    console.log(`JSEssion: `, this.jSession);

    return this.http.post(baseUrl, urlParam, httpOptions).pipe(
      map((response: any) => {
        return response;
      })
    );
  }
}
