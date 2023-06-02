import { map, of, startWith } from 'rxjs';
import { Component } from '@angular/core';
import { Form, FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'mcu-content-upload-form',
  templateUrl: './content-upload-form.component.html',
  styleUrls: ['./content-upload-form.component.scss'],
})
export class ContentUploadFormComponent {
  jobDetails!: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.jobDetails = this.formBuilder.group({
      brand: [''],
      productLine: [''],
      country: [''],
      albumName: [''],
      department: [''],
      departMentFormArray: this.formBuilder.array([]),
    });
  }

  jobDetailsArray(): FormArray {
    return this.jobDetails.get('departMentFormArray') as FormArray;
  }

  creativeDepartMent() {
    return this.formBuilder.group({
      name: [''],
    });
  }

  departMent!: string;
  departMentChange() {
    this.jobDetailsArray().push(this.creativeDepartMent());
  }

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
  filteredBrands: any = [];
  filteredProductLine: any = [];
  filterCountries: any = [];
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
  }

  brandId: any;
  updateSelectedBrand(selectedValue: any) {
    this.selectedBrand = selectedValue.source.value;

    // let c = this.producs.map((brand) => {
    //   if (brand.name === this.selectedBrand) {
    //     this.brandId = brand.brandId;
    //   }
    //   console.log(`sbfb`, c);
    // });

    console.log(selectedValue.source.value, 'baba');
    console.log(this.selectedBrand, 'baba');
    console.log(this.brandId);
  }

  private _filterBrand(name: string): any[] {
    const filterValue = name.toLowerCase();
    return this.brands.filter((option: any) =>
      option.name.toLowerCase().includes(filterValue)
    );
  }

  private _filterProductLine(name: string, brandId: any): any[] {
    console.log(`dsdsd`, brandId);
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

  hasChange: boolean = false;
  onCreateGroupFormValueChange() {}

  submitForm() {
    console.log(`form`, this.jobDetails);
  }
}
