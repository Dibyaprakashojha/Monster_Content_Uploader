import { Component, AfterViewInit } from '@angular/core';
import { PageService } from './page.service';

@Component({
  selector: 'mcu-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.scss'],
})
export class PageComponent {
  // constructor(public empService: PageService) {}
  // getTableData$(pageNumber: Number, pageSize: Number) {
  //   return this.empService.getEmployees(pageNumber, pageSize);
  // }
  // ngAfterViewInit() {
  //   this.dataSource.paginator = this.paginator;
  //   this.paginator.page
  //     .pipe(
  //       startWith({}),
  //       switchMap(() => {
  //         return this.getTableData$(
  //           this.paginator.pageIndex + 1,
  //           this.paginator.pageSize
  //         ).pipe(catchError(() => observableOf(null)));
  //       }),
  //       map((empData) => {
  //         if (empData == null) return [];
  //         this.totalData = empData.total;
  //         return empData.data;
  //       })
  //     )
  //     .subscribe((empData) => {
  //       this.EmpData = empData;
  //       this.dataSource = new MatTableDataSource(this.EmpData);
  //     });
  // }
}
