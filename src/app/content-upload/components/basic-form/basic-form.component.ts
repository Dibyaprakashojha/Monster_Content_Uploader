import { FormService } from './../../services/form.service';
import { SearchService } from './../../services/search.service';
import { Route, Router } from '@angular/router';
import { EventEmitter, Input, NgZone, Output, ViewChild } from '@angular/core';
import { Component } from '@angular/core';

import {
  FormGroup,
  FormBuilder,
  FormArray,
  FormGroupDirective,
} from '@angular/forms';
import {
  tap,
  startWith,
  debounceTime,
  distinctUntilChanged,
  switchMap,
  map,
} from 'rxjs/operators';
import { CreativeFormComponent } from '../creative-form/creative-form.component';
@Component({
  selector: 'mcu-basic-form',
  templateUrl: './basic-form.component.html',
  styleUrls: ['./basic-form.component.scss'],
})
export class BasicFormComponent {
  @ViewChild('mcucomponent', { static: false }) mcucomponent:
    | CreativeFormComponent
    | undefined;
  jobDetails!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private formService: FormService,
    private zone: NgZone
  ) {
    this.jobDetails = this.fb.group({
      brand: [''],
      productLine: [''],
      country: [''],
      albumName: [''],
      department: [''],
    });
  }

  jobDetailsArray(): FormArray {
    return this.jobDetails.get('departMentFormArray') as FormArray;
  }

  creativeDepartMent() {
    return this.fb.group({
      name: [''],
    });
  }

  departMent!: string;
  departMentChange() {
    this.jobDetailsArray().push(this.creativeDepartMent());
  }

  brands: any = [];
  products = [];
  countries = [];
  filteredBrands: any = [];
  filteredProductLine: any = [];
  filterCountries: any = [];
  selectedBrand: any;

  ngOnInit() {
    this.formService.getAllBrands().subscribe(
      (eachBrand) => {
        this.brands = eachBrand;
        console.log(`brands `, this.brands);
      },
      (err) => {}
    );

    this.filteredBrands = this.jobDetails.controls['brand'].valueChanges.pipe(
      startWith(''),
      debounceTime(200),
      distinctUntilChanged(),
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
        console.log(value);
        const name = typeof value === 'string' ? value : value?.name;
        console.log(this._filterProductLine(name as string));
        return name
          ? this._filterProductLine(name as string)
          : this.products.slice();
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
  }

  brandId: any;
  updateSelectedBrand(selectedValue: any) {
    this.selectedBrand = selectedValue.source.value;
    console.log(`sected brand`, this.selectedBrand);
    this.brands.map((brand: any) => {
      if (brand.brandName === this.selectedBrand) {
        this.brandId = brand.brandId;
        this.formService.getByBrandId(this.brandId).subscribe((eachBrand) => {
          console.log(eachBrand);
          this.products = eachBrand.productLines;
          this.countries = eachBrand.countries;
          console.log(`brands `, this.brands);
        });
      }
    });
  }

  private _filterBrand(name: string): any[] {
    const filterValue = name.toLowerCase();
    return this.brands.filter((option: any) =>
      option.brandName.toLowerCase().includes(filterValue)
    );
  }

  filteredArray: any[] = [];
  private _filterProductLine(name: string): any[] {
    const filterValue = name.toLowerCase();
    return this.products.filter((option: any) =>
      option.prdLineName.toLowerCase().includes(filterValue)
    );
  }

  private _filterCountries(name: string): any[] {
    const countryName = name.toLowerCase();
    return this.countries.filter((option: any) =>
      option.countryName.toLowerCase().includes(countryName)
    );
  }

  hasChange: boolean = false;
  onCreateGroupFormValueChange() {}

  getBrand(brandId: any): any {
    let element = this.brands.find((e: any) => e.brandId == brandId);
    if (element) {
      return element['brandName'];
    }
  }

  getBrandbyName(name: any) {
    let element = this.brands.find((e: any) => e.brandName == name);
    return element['brandId'];
  }

  getCountry(countryId: any): any {
    let element = this.countries.find((e: any) => e.countryId == countryId);
    if (element) {
      return element['countryName'];
    }
  }

  getProductLine(prdLineId: any): any {
    let element = this.products.find((e: any) => e.prdLineId == prdLineId);
    if (element) {
      return element['prdLineName'];
    }
  }

  showCreative: boolean = false;
  jobId: any;
  submitForm() {
    console.log(`form`, this.jobDetails.value);
    this.showCreative = true;
    this.jobDetails.controls['brand'].setValue(
      this.getBrandbyName(this.jobDetails.controls['brand'].getRawValue())
    );
    this.formService.createJob(this.jobDetails.value).subscribe((data) => {
      this.showCreative = true;
      let details = JSON.parse(data);
      this.jobId = details.jobId;
      console.log(`jobId`, details.jobId);
      this.mcucomponent?.getData(details.jobId);
    });
  }
}
