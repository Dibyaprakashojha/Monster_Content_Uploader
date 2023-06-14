import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterByText',
  pure:true
})
export class FilterByTextPipe implements PipeTransform {
  transform(items: any[], searchText: string): any[] {
    console.log(items);
    console.log(searchText);
    if (!items) {
      return [];
    }
    if (!searchText) {
      return items;
    }
    searchText = searchText.toLocaleLowerCase();
    return items.filter((each: any): any => {
      if (each.job_id.toString().includes(searchText)) {
        return true;
      } else if (each.job_name.includes(searchText)) {
        return true;
      } else if (each.brand.includes(searchText)) {
        return true;
      } else if (each.country.includes(searchText)) {
        return true;
      } else if (each.department_name.includes(searchText)) {
        return true;
      } else if (each.job_status.includes(searchText)) {
        return true;
      }else{
        return true
      }
    });
  }
}
