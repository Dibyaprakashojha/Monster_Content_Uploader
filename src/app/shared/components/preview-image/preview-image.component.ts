import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { OtmmService } from '../../services/otmm.service';
import { environment as env, environment } from 'src/environments/environment';
import { UploadComponent } from '../upload/upload.component';
import { Dialog } from '@angular/cdk/dialog';
@Component({
  selector: 'mcu-preview-image',
  templateUrl: './preview-image.component.html',
  styleUrls: ['./preview-image.component.scss'],
})
export class PreviewImageComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private otmmService: OtmmService,
    private dialog:MatDialog

  ) {}
  imageList!: any[];
  responsiveOptions!: any[];
  bucketName!: any;
  finalHitCount!: any;

  ngOnInit(): void {
    console.log('data:', this.data);
    this.finalHitCount = this.data.finalHitCount;
    this.bucketName = this.data.bucketName;
    this.otmmService
      .otmmMetadataSearch(
        env.searchConfigId,
        0,
        this.finalHitCount,
        this.bucketName
      )
      .subscribe({
        next: (res: any) => {
          this.imageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Metadata: `, res);
        },
      });
 

    }

  
  assetData :any= {
    "metadata": {
      "metadata_element_list":[]
    },
    "metadata_model_id":"",
    "security_policy_list": [1],
    "template_id": environment.folder_template_id,
    "folderId": environment.folder_id,
  };

  maxFileSize=null
  uploadMore(){

    if(this.data.validStatus){
    this.otmmService.getSessioons().subscribe({
      next:(data)=>{
        console.log(data)
      },
      error:(error)=>{
        this.otmmService.postSession().subscribe({
          next:(data)=>{
            this.otmmService.jSession='';
            this.otmmService.jSession=data.session_resource.session.id;
          }
        })
      }}
    )
   
    this.assetData.metadata_model_id = environment.MetadataModel;
    this.assetData.template_id = environment.folder_template_id;
    this.assetData.folderId  = environment.folder_id
    this.assetData.metadata.metadata_element_list = [
      {
        id: 'MCU_DETAILS_BRAND',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: `${this.data.assetMetadata.brand}^${this.data.assetMetadata.productLine}^${this.data.assetMetadata.country}`
          },
        },
      },
      {
        id: 'MCU_DETAILS_DEPARTMENT',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: this.data.assetMetadata.department,
          },
        },
      },
      {
        id: 'MCU_DETAIL_ALBUM_NAME',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value:  this.data.assetMetadata.albumName,
          },
        },
      },
      {
        id: 'MCU_DETAILS_SAP_NUMBER',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: "decimal",
            value:  this.data.assetMetadata.sapNo,
          },
        },
      },
      {
        id: 'MCU_DETAILS_DATE',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'dateTime',
            value: new Date(this.data.assetMetadata.eventDate)
          },
        },
      },
      {
        id: 'MCU_DETAILS_COMMENTS',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value:this.data.assetMetadata.comments,
          },
        },
      },
      {
        id: 'MCU_DETAILS_BUCKET_NAME',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: this.data.bucketName,
          },
        },
      },
      {
        id: 'MCU_DETAILSJOB_ID',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: this.data.JobId,
          },
        },
      },
      {
        id: 'MCU_DETAILS_USECASE',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value:this.data.assetMetadata.useCase
          },
        },
      },
      {
        id: 'MCU_ASSET_TYPE',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: `${this.data.assetMetadata.assetType}^${this.data.assetMetadata.assetSubType}`
          },
        },
      },
    ];
    // console.log(this.assetData.metadata.metadata_element_list)
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
    dialogRef.afterClosed().subscribe((result:any) => {});
  }else{
  }
  }

}



