import {
  Component,
  Input,
  OnChanges,
  SimpleChanges,
  OnInit,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { FormService } from '../../services/form.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { NotificationServiceService } from 'src/app/shared/services/notification-service.service';
import { GlobalConfig as config } from 'src/Utils/config/config'
import { environment  } from 'src/environments/environment';
import { UploadComponent } from 'src/app/shared/components/upload/upload.component';
import { OtmmService } from 'src/app/shared/services/otmm.service';
import { PreviewImageComponent } from 'src/app/shared/components/preview-image/preview-image.component';

@Component({
  selector: 'mcu-creative-form',
  templateUrl: './creative-form.component.html',
  styleUrls: ['./creative-form.component.scss'],
})
export class CreativeFormComponent implements OnInit, OnChanges {
  
  
  @Input() jobId: any;

  brands: any[] = [];
  producs = [];
  countries = [];
  departments=[]
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
  finalImageList: any;
  ngOnChanges(changes: SimpleChanges): void {
    console.log(`jonbId in cretaive`, this.jobId);
  }

  lastModifiedBy: any;
  lastModifiedDate: any;

  public getData(id: any) {
    this.formService.getJobdetailsByJobId(id).subscribe((data: any) => {
      console.log(data);

      this.lastModifiedBy = data.createdBy.psDetailsId;
      // this.lastModifiedDate = data.
      this.formService
        .getByBrandId(data.brand.brandId)
        .subscribe((eachBrand: any) => {
          console.log(eachBrand);
          this.producs = eachBrand.productLines;
          this.filteredProductLine = this.producs;
          this.countries = eachBrand.countries;
          this.filterCountries = this.countries;
          console.log(`brands `, this.brands);
          this.setTheFormVlaues(data);
        });
    });
  }

  setTheFormVlaues(jobData: any) {
    this.jobDetails.controls['brand'].setValue(jobData.brand.brandId);
    this.jobDetails.controls['productLine'].setValue(
      jobData.productLine.prdLineId
    );
    this.jobDetails.controls['country'].setValue(jobData.country.countryId);
    this.jobDetails.controls['albumName'].setValue(jobData.albumName);
    this.jobDetails.controls['department'].setValue(jobData.department.dptId);

    this.getAllAssets();
    this.getAllUseCase();
    this.getAllSubAssetTypes();
    this.disableFormFields();
  }

  disableRadio!: boolean;
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

  jobDetails!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private router: Router,
    public dialog: MatDialog,
    private notificationService: NotificationServiceService,
    private otmmService:OtmmService
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

  ngOnInit() {
    console.log(`jonbId in onint`, this.jobId);

    this.formService.getAllBrands().subscribe(
      (eachBrand) => {
        this.brands = eachBrand;
        console.log(`brands `, this.brands);
      },
      (err) => {}
    );
    this.formService.getAllDepartmentName().subscribe((data:any)=>{
      this.departments=data
    })
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
    this.selectedBrand = selectedValue.source.value;
  }

  private _filterBrand(name: string): any[] {
    const filterValue = name.toLowerCase();
    return this.brands.filter((option: any) =>
      option.name.toLowerCase().includes(filterValue)
    );
  }

  private _filterProductLine(name: string, brandId: any): any[] {
    const filterValue = name.toLowerCase();
    let a = this.producs.filter(
      (option: any) => this.brandId == option.brandId
    );
    let b = a.filter(
      (option: any) =>
        option.name.toLowerCase().includes(filterValue) &&
        this.brandId == option.brandId
    );
    console.log(b);
    return b;
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
      return element['brandDsName']
    }
  }

  getProductLine(prdLineId: any): any {
    let element = this.producs.find((e: any) => e.prdLineId == prdLineId);
    if (element) {
      return element['prdLineDsName'];
    }
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

  /*validators*/
  
  SaveForm() {
    this.notificationService.success('UpLoad Job has beedn saved');
    // if(config.getOtmmSession().session_resource.session.session==null){
      this.otmmService.postSession().subscribe((data:any)=>{
        this.otmmService.jSession=data.session_resource.session.id;
        console.log(data)
      })
    // }


  }

  showCreative!: boolean;
  submitForm() {
    this.enableFormFields();
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
          this.router.navigateByUrl('apps/dashboard');
          this.notificationService.error(
            'UpLoad Job has not submitted please reinitiate Job'
          );
        },
      });
  }

  deleteJob(jobId: any) {
   
    this.notificationService.success('Job has been deleted sucessfully ');
    this.formService.deleteJob(jobId).subscribe((data:any)=>{
      console.log(data)
    this.router.navigateByUrl('apps/dashboard');

    })
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


  getDepartment(departMentId:any){
    const dept:any =this.departments.find((each:any)=>each.dptId==departMentId)

    console.log('deperkpoere',dept)
    return dept['dptDsName']
    
  }

  
  maxFileSize=null
  uploadAsset(BucketName:string){

    if(this.jobDetails.valid){


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
            value: `${this.getBrand(this.jobDetails.get('brand')?.value)}^${this.getProductLine(this.jobDetails.get('productLine')?.value)}^${this.getCountry(this.jobDetails.get('country')?.value)}`
          },
        },
      },
      {
        id: 'MCU_DETAILS_DEPARTMENT',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: this.getDepartment(this.jobDetails.controls['department']?.value),
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
            type: "decimal",
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
            value:this.getUseCase(this.jobDetails.controls['useCase'].value)
          },
        },
      },
      {
        id: 'MCU_ASSET_TYPE',
        type: 'com.artesia.metadata.MetadataField',
        value: {
          value: {
            type: 'string',
            value: `${this.getAssetType(this.jobDetails.controls['assetType'].value)}^${this.getAssetSubType(this.jobDetails.controls['assetSubType'].value)}`
          },
        },
      },
      
      
    ];
    console.log(this.assetData.metadata.metadata_element_list)
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
  }else{
    this.notificationService.error('please fill the form details')
  }
}


getMetaDataAsset(){
  let asstetMetaData={
    'brand':this.getBrand(this.jobDetails.get('brand')?.value),
    'productLine':this.getProductLine(this.jobDetails.get('productLine')?.value),
    "country":this.getCountry(this.jobDetails.get('country')?.value),
    "albumName":this.jobDetails.get('albumName')?.value,
    "department":this.getDepartment(this.jobDetails.controls['department']?.value),
    "assetType":this.getAssetType(this.jobDetails.controls['assetType'].value),
    "assetSubType":this.getAssetSubType(this.jobDetails.controls['assetSubType'].value),
    "useCase":this.getUseCase(this.jobDetails.controls['useCase'].value),
    "comments": this.jobDetails.controls['comments'].value,
    "sapNo":this.jobDetails.controls['sapNumber'].value,
    "eventDate":this.jobDetails.controls['eventDate'].value
  }
  return asstetMetaData
  
  
}

finalHitCount!: number;
/* Preview Image List from OTMM */
getImagesFromOtmm = () => {
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
  this.otmmService
    .otmmMetadataSearch(environment.searchConfigId, 0, 5, 'FINAL',this.jobId)
    .subscribe({
      next: (res: any) => {
        this.finalHitCount =
          res.search_result_resource.search_result.total_hit_count;
        this.finalImageList = res.search_result_resource.asset_list.map(
          (asset: any) => {
            return asset.delivery_service_url;
          }
        );
        console.log(`Metadata: `, res);
      },
    });
};

previewImage(imageList: any,bucketName:any) {
 let assetMetadataVlaues= this.getMetaDataAsset();
  this.dialog.open(PreviewImageComponent, {
    width: '900px',
    height: '700px',
    data: {
      imageList: imageList,
      finalHitCount: this.finalHitCount,
      validStatus:this.jobDetails.valid,
      bucketName:bucketName,
      assetMetadata:assetMetadataVlaues,
      JobId:this.jobId

    },
  });
}
  


  
}
