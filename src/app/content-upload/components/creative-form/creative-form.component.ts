import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { map, startWith } from 'rxjs';

@Component({
  selector: 'mcu-creative-form',
  templateUrl: './creative-form.component.html',
  styleUrls: ['./creative-form.component.scss'],
})
export class CreativeFormComponent {
  jobDetails!: FormGroup;
  constructor(private fb: FormBuilder) {
    this.jobDetails = this.fb.group({
      brand: ['monster'],
      productLine: ['ultra'],
      country: ['India'],
      albumName: ['xyz'],
      department: ['creative'],

      assetType: [''],
      assetSubType: [''],
      year: [''],
      sapNumber: [''],
      useCase: [''],
      comments: [''],
    });
  }

  // jobDetailsArray(): FormArray {
  //   return this.jobDetails.get('departMentFormArray') as FormArray;
  // }

  creativeDepartMent() {
    return this.fb.group({
      name: [''],
    });
  }

  departMent!: string;

  brands = [
    { name: 'monster', brandId: 1 },
    { name: 'core', brandId: 2 },
  ];
  producs = [
    { name: 'ultra', brandId: 1 },
    { name: 'rehab', brandId: 1 },
    { name: 'pink', brandId: 2 },
    { name: 'blue', brandId: 2 },
  ];
  countries = [
    { name: 'India', countryId: 1 },
    { name: 'USA', countryId: 2 },
    { name: 'Australia', countryId: 3 },
  ];

  assets = [
    { name: 'can', assetId: 1 },
    { name: 'bottle', assetId: 2 },
    { name: 'bike', assetId: 3 },
    { name: 'bmx', assetId: 4 },
  ];

  assetsSubType = [
    { name: 'pinkCan', assetId: 1 },
    { name: 'greenbottle', assetId: 1 },
    { name: 'bike', assetId: 3 },
    { name: 'bmx', assetId: 4 },
  ];

  useCase = [
    { name: 'round', useCaseId: 1 },
    { name: 'canSahpe', useCaseId: 2 },
    { name: 'straight', useCaseId: 3 },
  ];

  filteredBrands: any = [];
  filteredProductLine: any = [];
  filterCountries: any = [];
  filterAssetTypes: any = [];
  filterAssetSubTypes: any = [];
  useCaseTypes: any = [];
  selectedBrand: any;

  ngOnInit() {
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
        return name
          ? this._filterSubAssets(name as string)
          : this.useCase.slice();
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
    const filterAsset = name.toLowerCase();
    return this.assets.filter((option: any) =>
      option.name.toLowerCase().includes(filterAsset)
    );
  }

  private _filterSubAssets(name: string): any[] {
    const filterAssetSubAsset = name.toLowerCase();
    return this.assets.filter((option: any) =>
      option.name.toLowerCase().includes(filterAssetSubAsset)
    );
  }

  private _useCase(name: string): any[] {
    const useCaseValue = name.toLowerCase();
    return this.useCase.filter((option: any) =>
      option.name.toLowerCase().includes(useCaseValue)
    );
  }

  hasChange: boolean = false;
  onCreateGroupFormValueChange() {}

  showCreative!: boolean;
  submitForm() {
    console.log(`form`, this.jobDetails);
    // this.showSubForms.emit(true);

    this.showCreative = true;
  }
}
