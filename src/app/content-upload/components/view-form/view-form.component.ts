import { formatDate } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { NotificationServiceService } from 'src/app/shared/services/notification-service.service';
import { FormService } from '../../services/form.service';

@Component({
  selector: 'mcu-view-form',
  templateUrl: './view-form.component.html',
  styleUrls: ['./view-form.component.scss'],
})
export class ViewFormComponent {
  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private router: Router,
    public dialog: MatDialog,
    private notificationService: NotificationServiceService,
    private activatedRoute: ActivatedRoute
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

  ngOnInit() {
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
            .subscribe((eachBrand: any) => {
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
    return element['prdLineDsName']
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
    this.disableFormFields();
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
    //   this.jobDetails.controls['department'].getRawValue !== null ||
    //   this.jobDetails.controls['department'].getRawValue !== undefined
    // ) {
    //   this.jobDetails.controls['department'].disable();
    // }
    if (
      this.jobDetails.controls['assetType'].getRawValue !== null ||
      this.jobDetails.controls['assetType'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['assetType'].disable();
    }
    if (
      this.jobDetails.controls['assetSubType'].getRawValue !== null ||
      this.jobDetails.controls['assetSubType'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['assetSubType'].disable();
    }
    if (
      this.jobDetails.controls['useCase'].getRawValue !== null ||
      this.jobDetails.controls['useCase'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['useCase'].disable();
    }
    if (
      this.jobDetails.controls['sapNumber'].getRawValue !== null ||
      this.jobDetails.controls['sapNumber'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['sapNumber'].disable();
    }
    if (
      this.jobDetails.controls['comments'].getRawValue !== null ||
      this.jobDetails.controls['comments'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['comments'].disable();
    }
    if (
      this.jobDetails.controls['eventDate'].getRawValue !== null ||
      this.jobDetails.controls['eventDate'].getRawValue !== undefined
    ) {
      this.jobDetails.controls['eventDate'].disable();
    }
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

  deleteJob(jobId: any) {
    this.formService.deleteJob(jobId).subscribe({
      next: (data) => {
        this.router.navigateByUrl('apps/dashboard');
        this.notificationService.success('Job has been deleted sucessfully ');
      },
      error: (err) => {
        this.notificationService.error(
          'Job is not deleted please verify the form'
        );
      },
    });
  }

  navigateBack(){
    this.router.navigateByUrl('apps/dashboard')
  }
}
