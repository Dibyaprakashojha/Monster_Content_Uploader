import { NotificationServiceService } from './../../../shared/services/notification-service.service';
import { FormService } from './../../services/form.service';
import { ViewChild } from '@angular/core';
import { Component } from '@angular/core';

import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
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

  brands: Array<any> = [];
  products = [];
  countries = [];
  filteredBrands: any = [];
  filteredProductLine: Array<any> = [];
  filterCountries: Array<any> = [];
  selectedBrand: any;

  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private notificationService: NotificationServiceService
  ) {}

  public onSelectionChange(event: any, filterCondition: string) {
    let value = event.target.value;
    console.log(value, filterCondition);
    if (filterCondition == 'brand') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filteredBrands = name
        ? this._filterBrand(name as string)
        : this.brands;
      // this.onControlChanges();
    } else if ((filterCondition = 'productLine')) {
      const name = typeof value === 'string' ? value : value?.name;
      console.log(this._filterProductLine(name as string));
      this.filteredProductLine = name
        ? this._filterProductLine(name as string)
        : this.products.slice();
    } else if (filterCondition === 'country') {
      const name = typeof value === 'string' ? value : value?.name;
      this.filterCountries = name
        ? this._filterCountries(name as string)
        : this.countries.slice();
    }
  }

  brandId: any;
 

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

  getBrand(event: any): any {
    let element = this.brands.find((e: any) => e.brandId == event.brandId);
    if (element) {
      return element['brandName'];
    }
  }

  getBrandbyName(name: any) {
    let element = this.brands.find((e: any) => e.brandName == name);
    console.log(`brnadIs`);

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

  onControlChanges() {
    // this.jobDetails.controls['brand'].valueChanges.subscribe((data) => {
    //   this.jobDetails.controls['productLine'].setValue('');
    //   // this.jobDetails.controls['productLine'].markAsUntouched;
    //   // this.jobDetails.controls['productLine'].valid;
    //   this.jobDetails.controls['country'].setValue('');
    //   // this.jobDetails.controls['country'].markAsUntouched;
    //   // this.jobDetails.controls['country'].valid;
    // });
    // this.updateSelectedBrand(this.selectedBrand);
  }

  ngOnInit() {
    this.createForm();
    this.getAllbrands();
  }

  public createForm() {
    this.jobDetails = this.fb.group({
      brand: new FormControl(null),
      productLine: new FormControl(null),
      country: new FormControl(null),
      albumName: new FormControl(null),
      department: new FormControl(null),
    });
  }
  getAllbrands() {
    this.formService.getAllBrands().subscribe((eachBrand) => {
      this.brands = eachBrand;
      this.filteredBrands = this.brands;
    });
  }

  public onBrandChange() {
    setTimeout(() => {
      this.jobDetails.controls['productLine'].setValue(null);
      this.jobDetails.controls['country'].setValue(null);

      let value = this.jobDetails.value.brand;
      console.log(value);
      this.products = value.productLines;
      this.countries = value.countries;
      this.filterCountries = this.countries;
      this.filteredProductLine = this.products;
    }, 0);
  }
  public submitForm() {
    console.log(`initialForm details`, this.jobDetails.value);
    this.showCreative = true;
    // this.jobDetails.controls['brand'].setValue(
    //   this.getBrandbyName(this.jobDetails.controls['brand'].getRawValue())
    // );

    this.formService.createJob(this.jobDetails.value).subscribe(
      (data) => {
        this.showCreative = true;
        let details = JSON.parse(data);
        this.jobId = details.jobId;
        console.log(`jobId`, details.jobId);
        this.mcucomponent?.getData(details.jobId);
        this.notificationService.success('Upload Job Initiated');
      },
      (error) => {
        this.notificationService.error('Something went wrong .');
      }
    );
  }
}
