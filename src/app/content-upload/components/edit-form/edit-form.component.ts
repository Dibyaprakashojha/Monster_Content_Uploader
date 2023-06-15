import { map } from 'rxjs';
import { Component, Input, SimpleChanges, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationServiceService } from 'src/app/shared/services/notification-service.service';
import { FormService } from '../../services/form.service';
import { formatDate } from '@angular/common';
import { OtmmService } from 'src/app/shared/services/otmm.service';
import { environment as env, environment } from 'src/environments/environment';
import { PreviewImageComponent } from 'src/app/shared/components/preview-image/preview-image.component';
import { UploadComponent } from 'src/app/shared/components/upload/upload.component';
@Component({
  selector: 'mcu-edit-form',
  templateUrl: './edit-form.component.html',
  styleUrls: ['./edit-form.component.scss'],
})
export class EditFormComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private router: Router,
    public dialog: MatDialog,
    private notificationService: NotificationServiceService,
    private activatedRoute: ActivatedRoute,
    private otmmService: OtmmService
  ) {
    this.jobDetails = this.fb.group({
      brand: ['', [Validators.required]],
      productLine: ['', Validators.required],
      country: ['', Validators.required],
      albumName: [''],
      department: [''],
      assetType: [''],
      assetSubType: [''],
      eventDate: [''],
      sapNumber: [''],
      useCase: [''],
      comments: [''],
    });
  }

  jobDetails!: FormGroup;
  jobId: any;
  brands: any[] = [];
  producs = [];
  countries = [];
  assets = [];
  assetsSubType: any = [];
  useCase = [];
  filteredBrands: any = [];
  filteredProductLine: Array<any> = [];
  filterCountries: Array<any> = [];
  filterAssetTypes: Array<any> = [];
  filterAssetSubTypes: Array<any> = [];
  useCaseTypes: Array<any> = [];
  selectedBrand: any;
  lastModifiedBy: any;
  lastModifiedDate: any;
  finalImageList: any;
  proofImageList: any;
  resourcesImageList: any;
  workingImageList: any;
  finalHitCount: number = 0;
  proofHitCount: number = 0;
  resourcesHitCount: number = 0;
  workingHitCount: number = 0;
  departments: any;

  ngOnInit() {
    this.getImagesFromOtmm();
    this.activatedRoute.queryParams.subscribe((data: any) => {
      if (data.jobId) {
        this.getAllbrands();
        this.getAllProductLines();
        this.getAllCountries();
        this.getAllAssets();
        this.getAllUseCase();
        this.getAllSubAssetTypes();
        this.getData(data.jobId);
      } else {
        this.getAllbrands();
        this.getAllProductLines();
        this.getAllCountries();
        this.getAllAssets();
        this.getAllUseCase();
        this.getAllSubAssetTypes();
      }
      this.jobId = data.jobId;
    });
    this.formService.getAllDepartmentName().subscribe((data: any) => {
      this.departments = data;
    });
  }

  getAllbrands() {
    this.formService.getAllBrands().subscribe((eachBrand) => {
      this.brands = eachBrand;
      this.filteredBrands = this.brands;
    });
  }

  dummyProducts: any;
  getAllProductLines() {
    this.formService.getAllProductLine().subscribe((eachProductLine: any) => {
      this.producs = eachProductLine;
      // this.dummyProducts = this.producs;
      this.filteredProductLine = this.producs;
      console.log(`productLine`, eachProductLine);
    });
  }

  getAllCountries() {
    this.formService.getAllCountries().subscribe((eachCountry: any) => {
      this.countries = eachCountry;
      this.filterCountries = eachCountry;
    });
  }

  onSelectionChanged(event: any, filterCondition: string) {
    let value = event.target.value;
    if (filterCondition == 'brand') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filteredBrands = name
        ? this._filterBrand(name as string)
        : this.brands;
    } else if (filterCondition == 'productLine') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filteredProductLine = name
        ? this._filterProductLine(name as string, this.brandId)
        : this.producs.slice();
    } else if (filterCondition === 'country') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filterCountries = name
        ? this._filterCountries(name as string)
        : this.countries.slice();
    } else if (filterCondition === 'assetType') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filterAssetTypes = name
        ? this._filterAssets(name as string)
        : this.assets;
    } else if (filterCondition === 'assetSubType') {
      console.log(value);
      const name = typeof value === 'string' ? value : value?.name;
      this.filterAssetSubTypes = name
        ? this._filterSubAssets(name as string)
        : this.assetsSubType;
    } else if (filterCondition === 'useCase') {
      const name = typeof value === 'string' ? value : value?.name;
      this.useCaseTypes = name
        ? this._useCase(name as string)
        : this.useCase.slice();
    }
  }

  brandId: any;
  updateSelectedBrand(selectedValue: any) {
    if (selectedValue?.source.value) {
      this.resetTheFormVlaues();
      this.selectedBrand = selectedValue?.source.value;
      console.log(`sected brand`, this.selectedBrand);
      this.brands.map((brand: any) => {
        if (brand.brandId === this.selectedBrand) {
          this.brandId = selectedValue?.source.value;
          this.formService
            .getByBrandId(this.selectedBrand)
            .subscribe((eachBrand) => {
              console.log(eachBrand);
              this.filterCountries = eachBrand.countries;
              this.countries = eachBrand.countries;
              this.filteredProductLine = eachBrand.productLines;
              this.producs = eachBrand.productLines;
              console.log(`brands `, this.brands);
            });
        }
      });
    }
  }

  private _filterBrand(name: string): any[] {
    const filterValue = name.toLowerCase();

    return this.brands.filter((option: any) =>
      option.brandName.toLowerCase().includes(filterValue)
    );
  }

  private _filterProductLine(name: string, brandId: any): any[] {
    const filterValue = name.toLowerCase();
    return this.producs.filter((option: any) =>
      option.prdLineName.toLowerCase().includes(filterValue)
    );
  }

  private _filterCountries(name: string): any[] {
    const countryName = name.toLowerCase();
    return this.countries.filter((option: any) =>
      option.name.toLowerCase().includes(countryName)
    );
  }

  private _filterAssets(name: string): any[] {
    console.log(name);
    const filterAsset = name.toLowerCase();
    console.log(this.assets);

    return this.assets.filter((option: any) =>
      option.assetTypeName.toLowerCase().includes(filterAsset)
    );
  }

  getBrand(brandId: any): any {
    let element = this.brands.find((e: any) => e.brandId == brandId);
    if (element) {
      return element['brandDsName'];
    }
  }

  getProductLine(prdLineId: any): any {
    let element = this.filteredProductLine.find(
      (e: any) => e.prdLineId == prdLineId
    );
    return element?.['prdLineDsName'];
  }

  getCountry(countryId: any): any {
    let element = this.countries.find((e: any) => e.countryId == countryId);
    if (element) {
      return element['countryDsName'];
    }
  }

  getUseCase(useCaseId: any): any {
    let element = this.useCaseTypes.find((e: any) => e.useCaseId == useCaseId);
    if (element) {
      return element['useCaseDsName'];
    }
  }

  getAssetType(event: any): any {
    let element = this.assets.find(
      (e: any) => e.assetTypeId == event.assetTypeId
    );
    if (element) {
      return element['assetTypeDsName'];
    }
  }

  getAssetSubType(assetSubTypeId: any): any {
    let element = this.allAssetSubTypes.find(
      (e: any) => e.assetSubtypeId == assetSubTypeId
    );
    if (element) {
      return element['assetSubtypeDsName'];
    }
  }

  getAllUseCase(): any {
    this.formService.getAllUseCase().subscribe((data: any) => {
      this.useCase = data;
      this.useCaseTypes = data;
    });
    console.log(`thisuse case`, this.useCase);
  }

  selectedAsset: any;
  assetName: any;
  updateSelectedAsset() {
    setTimeout(() => {
      this.jobDetails.get('assetSubType')?.setValue(null);
      let value = this.jobDetails.value.assetType;
      console.log(value);
      this.filterAssetSubTypes = [];
      this.filterAssetSubTypes = this.allAssetSubTypes.filter(
        (x) => x.assetType.assetTypeId == value.assetTypeId
      );
      this.assetsSubType = this.filterAssetSubTypes;
      console.log(this.filterAssetSubTypes);
    });
  }

  public allAssetSubTypes: Array<any> = [];
  public getAllSubAssetTypes() {
    this.formService.getAllSubAssetTypes().subscribe((assetSubTypes: any) => {
      console.log(`assestsubTypes`, assetSubTypes);
      this.allAssetSubTypes = assetSubTypes;
    });
  }

  private _filterSubAssets(name: string): any[] {
    const filterAssetSubAsset = name.toLowerCase();
    return this.assetsSubType.filter((option: any) =>
      option.assetSubtypeName.toLowerCase().includes(filterAssetSubAsset)
    );
  }

  private _useCase(name: string): any[] {
    const useCaseValue = name.toLowerCase();
    return this.useCase.filter((option: any) =>
      option.useCaseName.toLowerCase().includes(useCaseValue)
    );
  }

  getAllAssets() {
    this.formService.getAllAssetTypes().subscribe((data: any) => {
      this.assets = data;
      this.filterAssetTypes = data;
      console.log(`assets`, this.assets);
    });
  }

  public getData(id: any) {
    this.formService.getJobdetailsByJobId(id).subscribe((data: any) => {
      this.formService
        .getByBrandId(data.brand.brandId)
        .subscribe((eachBrand: any) => {
          this.setTheFormVlaues(data);
        });
      console.log(data);
    });
  }

  resetTheFormVlaues() {
    this.jobDetails.get('brand')?.valueChanges.subscribe((data: any) => {
      console.log(`data`, data);
      this.jobDetails.controls['country'].reset('');
      this.jobDetails.controls['productLine'].reset('');
    });
    this.getAllbrands();
    this.getAllProductLines();
    this.getAllCountries();
    this.getAllAssets();
    this.getAllUseCase();
    this.getAllSubAssetTypes();
  }

  setTheFormVlaues(jobData: any) {
    this.jobDetails.controls['brand'].setValue(jobData.brand.brandId);
    this.jobDetails.controls['productLine'].setValue(
      jobData.productLine.prdLineId
    );
    this.jobDetails.controls['country'].setValue(jobData.country.countryId);
    this.jobDetails.controls['albumName'].setValue(jobData.albumName);
    this.jobDetails.controls['assetType'].setValue(jobData.assetType);
    this.jobDetails.controls['assetSubType'].setValue(
      jobData.assetSubType.assetSubtypeId
    );
    this.jobDetails.controls['department'].setValue(jobData.department.dptId);
    this.jobDetails.controls['useCase'].setValue(jobData.useCase.useCaseId);
    this.jobDetails.controls['sapNumber'].setValue(jobData.sapMaterialNumber);
    this.jobDetails.controls['eventDate'].setValue(
      formatDate(jobData.eventDateTime, 'yyyy-MM-dd', 'en')
    );

    this.jobDetails.controls['comments'].setValue(jobData.comments);
  }

  disableFormFields() {
    if (
      this.jobDetails.controls['brand'].getRawValue !== null ||
      this.jobDetails.controls['brand'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['brand'].disable();
    }
    if (
      this.jobDetails.controls['productLine'].getRawValue !== null ||
      this.jobDetails.controls['productLine'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['productLine'].disable();
    }
    if (
      this.jobDetails.controls['country'].getRawValue !== null ||
      this.jobDetails.controls['country'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['country'].disable();
    }
    if (
      this.jobDetails.controls['albumName'].getRawValue !== null ||
      this.jobDetails.controls['albumName'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['albumName'].disable();
    }
    // if (
    //   this.jobDetails.controls['department'].get('department') !== null ||
    //   this.jobDetails.controls['department'].get('department') !== undefined
    // ) {
    //   this.jobDetails.controls['albumName'].disable();
    // }
  }

  enableFormFields() {
    this.jobDetails.controls['albumName'].enable();
    this.jobDetails.controls['country'].enable();
    this.jobDetails.controls['productLine'].enable();
    this.jobDetails.controls['brand'].enable();
  }
  /*validators*/

  showCreative!: boolean;
  submitForm() {
    // this.enableFormFields();
    console.log(`form`, this.jobDetails);
    this.showCreative = true;
    this.formService
      .createJobDetails(this.jobDetails.value, this.jobId)
      .subscribe({
        next: (data) => {
          this.router.navigateByUrl('apps/dashboard');
          this.notificationService.success('UpLoad Job has beedn submitted');
        },
        error: (err) => {
          // this.router.navigateByUrl('apps/dashboard');
          this.notificationService.error(
            'UpLoad Job has not submitted please reinitiate Job'
          );
        },
      });
  }

  assetData: any = {
    metadata: {
      metadata_element_list: [],
    },
    metadata_model_id: '',
    security_policy_list: [1],
    template_id: environment.folder_template_id,
    folderId: environment.folder_id,
  };

  getDepartment(departMentId: any) {
    const dept: any = this.departments.find(
      (each: any) => each.dptId == departMentId
    );

    console.log('deperkpoere', dept);
    return dept['dptDsName'];
  }

  maxFileSize = null;
  uploadAsset(BucketName: string) {
    if (this.jobDetails.valid) {
      this.otmmService.getSessioons().subscribe({
        next: (data) => {
          console.log(data);
        },
        error: (error) => {
          this.otmmService.postSession().subscribe({
            next: (data) => {
              this.otmmService.jSession = '';
              this.otmmService.jSession = data.session_resource.session.id;
            },
          });
        },
      });

      this.assetData.metadata_model_id = environment.MetadataModel;
      this.assetData.template_id = environment.folder_template_id;
      this.assetData.folderId = environment.folder_id;
      this.assetData.metadata.metadata_element_list = [
        {
          id: 'MCU_DETAILS_BRAND',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: `${this.getBrand(
                this.jobDetails.get('brand')?.value
              )}^${this.getProductLine(
                this.jobDetails.get('productLine')?.value
              )}^${this.getCountry(this.jobDetails.get('country')?.value)}`,
            },
          },
        },
        {
          id: 'MCU_DETAILS_DEPARTMENT',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: this.getDepartment(
                this.jobDetails.controls['department']?.value
              ),
            },
          },
        },
        {
          id: 'MCU_DETAIL_ALBUM_NAME',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: this.jobDetails.controls['albumName']?.value,
            },
          },
        },
        {
          id: 'MCU_DETAILS_SAP_NUMBER',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'decimal',
              value: this.jobDetails.controls['sapNumber']?.value,
            },
          },
        },
        {
          id: 'MCU_DETAILS_DATE',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'dateTime',
              value: this.jobDetails.controls['eventDate']?.value,
            },
          },
        },
        {
          id: 'MCU_DETAILS_COMMENTS',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: this.jobDetails.controls['comments']?.value,
            },
          },
        },
        {
          id: 'MCU_DETAILS_BUCKET_NAME',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: BucketName,
            },
          },
        },
        {
          id: 'MCU_DETAILSJOB_ID',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: this.jobId,
            },
          },
        },
        {
          id: 'MCU_DETAILS_USECASE',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: this.getUseCase(this.jobDetails.controls['useCase'].value),
            },
          },
        },
        {
          id: 'MCU_ASSET_TYPE',
          type: 'com.artesia.metadata.MetadataField',
          value: {
            value: {
              type: 'string',
              value: `${this.getAssetType(
                this.jobDetails.controls['assetType'].value
              )}^${this.getAssetSubType(
                this.jobDetails.controls['assetSubType'].value
              )}`,
            },
          },
        },
      ];
      console.log(this.assetData.metadata.metadata_element_list);
      let fileToRevision;
      let isRevision = false;

      let maxFiles = null;
      const allowDuplicateDeliverable = false;
      isRevision = false;

      const dialogRef = this.dialog.open(UploadComponent, {
        width: '60%',
        disableClose: true,
        data: {
          assetData: this.assetData,
          isRevisionUpload: isRevision,
          fileToRevision,
          maxFiles,
          maxFileSize: this.maxFileSize,
        },
      });
      dialogRef.afterClosed().subscribe((result) => {});
    } else {
      this.notificationService.error('please fill the form details');
    }
  }

  getMetaDataAsset() {
    let asstetMetaData = {
      brand: this.getBrand(this.jobDetails.get('brand')?.value),
      productLine: this.getProductLine(
        this.jobDetails.get('productLine')?.value
      ),
      country: this.getCountry(this.jobDetails.get('country')?.value),
      albumName: this.jobDetails.get('albumName')?.value,
      department: this.getDepartment(
        this.jobDetails.controls['department']?.value
      ),
      assetType: this.getAssetType(this.jobDetails.controls['assetType'].value),
      assetSubType: this.getAssetSubType(
        this.jobDetails.controls['assetSubType'].value
      ),
      useCase: this.getUseCase(this.jobDetails.controls['useCase'].value),
      comments: this.jobDetails.controls['comments'].value,
      sapNo: this.jobDetails.controls['sapNumber'].value,
      eventDate: this.jobDetails.controls['eventDate'].value,
    };
    return asstetMetaData;
  }
  downloadUrl!: any;
  JOB_ID!: string;
  /* Preview Image List from OTMM */
  getImagesFromOtmm = () => {
    this.activatedRoute.queryParams.subscribe((data: any) => {
      this.JOB_ID = data.jobId;
    });
    this.otmmService.getSessioons().subscribe({
      next: (data) => {
        console.log(data);
        this.otmmService.jSession = data.session_resource.session.id;
      },
      error: (error) => {
        this.otmmService.postSession().subscribe({
          next: (data) => {
            this.otmmService.jSession = '';
            this.otmmService.jSession = data.session_resource.session.id;
          },
        });
      },
    });
    /**FINAL BUCKET */
    this.otmmService
      .otmmMetadataSearch(env.searchConfigId, 0, 5, 'FINAL', this.JOB_ID)
      .subscribe({
        next: (res: any) => {
          this.finalHitCount =
            res.search_result_resource.search_result.total_hit_count;
          this.downloadUrl = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.master_content_info.url;
            }
          );
          this.finalImageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Download Url:  `, this.downloadUrl);
          console.log(`Metadata: `, res);
        },
      });

    /**PROOF BUCKET */
    this.otmmService
      .otmmMetadataSearch(env.searchConfigId, 0, 5, 'PROOF', this.JOB_ID)
      .subscribe({
        next: (res: any) => {
          this.proofHitCount =
            res.search_result_resource.search_result.total_hit_count;
          this.proofImageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Metadata: `, res);
        },
      });

    /**RESOURCES BUCKET */
    this.otmmService
      .otmmMetadataSearch(env.searchConfigId, 0, 5, 'RESOURCES', this.JOB_ID)
      .subscribe({
        next: (res: any) => {
          this.resourcesHitCount =
            res.search_result_resource.search_result.total_hit_count;
          this.resourcesImageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Metadata: `, res);
        },
      });

    /**WORKING BUCKET */
    this.otmmService
      .otmmMetadataSearch(env.searchConfigId, 0, 5, 'WORKING', this.JOB_ID)
      .subscribe({
        next: (res: any) => {
          this.workingHitCount =
            res.search_result_resource.search_result.total_hit_count;
          this.workingImageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Metadata: `, res);
        },
      });
  };

  previewImage(imageList: any, bucketName: any) {
    let assetMetadataVlaues = this.getMetaDataAsset();
    this.dialog.open(PreviewImageComponent, {
      width: '900px',
      height: '700px',
      data: {
        imageList: imageList,
        finalHitCount: this.finalHitCount,
        validStatus: this.jobDetails.valid,
        bucketName: bucketName,
        assetMetadata: assetMetadataVlaues,
        JobId: this.jobId,
      },
    });
  }

  deleteJob(jobId: any) {
    this.router.navigateByUrl('apps/dashboard');
    this.notificationService.success('Job has been deleted sucessfully ');
    this.formService.deleteJob(jobId).subscribe((data: any) => {
      console.log(data);
    });
  }
}
