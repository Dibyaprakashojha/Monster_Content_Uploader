import {
  Component,
  Input,
  OnChanges,
  SimpleChanges,
  OnInit,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { map, startWith } from 'rxjs';
import { FormService } from '../../services/form.service';

@Component({
  selector: 'mcu-creative-form',
  templateUrl: './creative-form.component.html',
  styleUrls: ['./creative-form.component.scss'],
})
export class CreativeFormComponent implements OnInit, OnChanges {
  @Input() jobId: any;

  ngOnChanges(changes: SimpleChanges): void {
    console.log(`jonbId in cretaive`, this.jobId);
    // this.formService.getJobdetailsByJobId(this.jobId).subscribe((data: any) => {
    //   console.log(data);
    //   this.ngOnInit();
    //   this.setTheFormVlaues(data);
    // });
  }

  public getData(id: any) {
    this.formService.getJobdetailsByJobId(id).subscribe((data: any) => {
      console.log(data);
      // this.ngOnInit();
      this.setTheFormVlaues(data);
    });
  }

  setTheFormVlaues(jobData: any) {
    this.jobDetails.controls['brand'].setValue(jobData.brand.brandId);
    this.jobDetails.controls['productLine'].setValue(
      jobData.productLine.prdLineName
    );
    this.jobDetails.controls['country'].setValue(jobData.country.countryName);
    this.jobDetails.controls['albumName'].setValue(jobData.albumName);
    this.jobDetails.controls['department'].setValue(jobData.department.dptId);

    this.formService
      .getByBrandId(jobData.brand.brandId)
      .subscribe((eachBrand) => {
        console.log(eachBrand);
        this.producs = eachBrand.productLines;
        this.countries = eachBrand.countries;
        console.log(`brands `, this.brands);
      });
  }

  jobDetails!: FormGroup;
  constructor(private fb: FormBuilder, private formService: FormService) {
    this.jobDetails = this.fb.group({
      brand: [''],
      productLine: [''],
      country: [''],
      albumName: [''],
      department: [''],
      assetType: [''],
      assetSubType: [''],
      year: [''],
      sapNumber: [''],
      useCase: [''],
      comments: [''],
    });
  }

  brands = [];
  producs = [];
  countries = [];
  assets = [];
  assetsSubType: any = [];
  useCase = [];
  filteredBrands: any = [];
  filteredProductLine: any = [];
  filterCountries: any = [];
  filterAssetTypes: any = [];
  filterAssetSubTypes: any = [];
  useCaseTypes: any = [];
  selectedBrand: any;

  ngOnInit() {
    console.log(`jonbId in onint`, this.jobId);
    this.formService.getAllBrands().subscribe(
      (eachBrand) => {
        this.brands = eachBrand;
        console.log(`brands `, this.brands);
      },
      (err) => {}
    );

    this.getAllAssets();
    this.getAllUseCase();
    this.filteredBrands = this.jobDetails.controls['brand'].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filterBrand(name as string) : this.brands.slice();
      })
    );
    this.filteredProductLine = this.jobDetails.controls[
      'productLine'
    ].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name
          ? this._filterProductLine(name as string, this.brandId)
          : this.producs.slice();
      })
    );
    this.filterCountries = this.jobDetails.controls[
      'country'
    ].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name
          ? this._filterCountries(name as string)
          : this.countries.slice();
      })
    );
    this.filterAssetTypes = this.jobDetails.controls[
      'assetType'
    ].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filterAssets(name as string) : this.assets.slice();
      })
    );
    this.filterAssetSubTypes = this.jobDetails.controls[
      'assetSubType'
    ].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name
          ? this._filterSubAssets(name as string)
          : this.assetsSubType.slice();
      })
    );
    this.useCaseTypes = this.jobDetails.controls['useCase'].valueChanges.pipe(
      startWith(''),
      map((value) => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._useCase(name as string) : this.useCase.slice();
      })
    );
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
      return element['brandName'];
    }
  }

  getProductLine(prdLineId: any): any {
    let element = this.producs.find((e: any) => e.prdLineId == prdLineId);
    if (element) {
      return element['prdLineName'];
    }
  }

  getCountry(countryId: any): any {
    let element = this.countries.find((e: any) => e.countryId == countryId);
    if (element) {
      return element['countryName'];
    }
  }

  getUseCase(useCaseId: any): any {
    let element = this.useCase.find((e: any) => e.useCaseId == useCaseId);
    if (element) {
      return element['useCaseName'];
    }
  }

  getAssetType(assetId: any): any {
    let element = this.assets.find((e: any) => e.assetTypeId == assetId);
    if (element) {
      return element['assetTypeName'];
    }
  }

  getAssetSubType(assetSubType: any): any {
    let element = this.assetsSubType.find(
      (e: any) => e.assetSubtypeId == assetSubType
    );
    if (element) {
      return element['assetSubtypeName'];
    }
  }

  getAllUseCase(): any {
    this.formService.getAllUseCase().subscribe((data: any) => {
      this.useCase = data;
    });
    console.log(`thisuse case`, this.useCase);
  }

  selectedAsset: any;
  assetName: any;
  updateSelectedAsset(selectedValue: any) {
    this.selectedAsset = selectedValue.source.value;
    console.log(this.selectedAsset);
    this.formService.getAllSubAssetTypes().subscribe((assetSubTypes: any) => {
      console.log(`assestsubTypes`, assetSubTypes);
      assetSubTypes.map((each: any) => {
        if (each.assetType.assetTypeName === this.selectedAsset) {
          this.assetsSubType.push(each);
        }
      });
    });
    console.log(this.assetsSubType);
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
      console.log(`assets`, this.assets);
    });
  }

  showCreative!: boolean;
  submitForm() {
    console.log(`form`, this.jobDetails);
    this.showCreative = true;
  }
}
