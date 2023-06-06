// from ng oninit in basic form
// this.jobDetails.controls['brand'].valueChanges.pipe(
//   startWith(''),
//   debounceTime(200),
//   distinctUntilChanged(),
//   map((value) => {
//     const name = typeof value === 'string' ? value : value?.name;
//     this.filteredBrands = name
//       ? this._filterBrand(name as string)
//       : this.brands.slice();
//   })
// );
// this.jobDetails.controls['productLine'].valueChanges.pipe(
//   startWith(''),
//   map((value) => {
//     console.log(value);
//     const name = typeof value === 'string' ? value : value?.name;
//     console.log(this._filterProductLine(name as string));
//     this.filteredProductLine = name
//       ? this._filterProductLine(name as string)
//       : this.products.slice();
//   })
// );
// this.jobDetails.controls['country'].valueChanges.pipe(
//   startWith(''),
//   map((value) => {
//     const name = typeof value === 'string' ? value : value?.name;
//     this.filterCountries = name
//       ? this._filterCountries(name as string)
//       : this.countries.slice();
//   })
// );
