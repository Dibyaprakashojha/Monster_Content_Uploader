import { SearchService } from './../../services/search.service';
import { ContentUploadService } from './../../services/content-upload.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AfterViewInit, DoCheck, OnInit } from '@angular/core';
import { Component, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { catchError, filter, map, of, startWith, switchMap } from 'rxjs';
import { JobDetails, SorlResponse } from 'src/app/shared/models/jobDetails';

@Component({
  selector: 'mcu-jobs-table',
  templateUrl: './jobs-table.component.html',
  styleUrls: ['./jobs-table.component.scss'],
})
export class JobsTableComponent implements OnInit {
  // length = 50;
  // pageSize = 3;
  // pageIndex = 0;
  pageSizeOptions = [3, 5, 7];
  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent!: PageEvent;

  displayedColumns: string[] = [
    'job_id',
    'job_name',
    'brand',
    'country',
    'department_name',
    'product_line',
    'job_status',
    'created_date',
    'actions',
  ];

  isMobile!: boolean;
  dataSource = new MatTableDataSource<JobDetails>();
  @ViewChild('paginator') paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private searchService: SearchService
  ) {}

  applyFilter(event: Event) {
    let filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  // offset!: number;

  // handlePageEvent(e: PageEvent) {
  //   this.offset = e.pageIndex;
  //   this.pageSize = e.pageSize;
  //   console.log(this.offset + '...' + this.pageSize);
  //   // this.sortwithPagination();
  // }

  active!: string;
  direction!: string;

  isDesktop!: boolean;
  isTablet!: boolean;

  getJobDetails(start: any, end: any) {
    return this.searchService.getAllJobDeatils(start, end, 300);
  }

  ngOnInit(): void {
    // this.searchService
    //   .getAllJobDeatils(this.startIndex, this.endIndex, this.totalPageNumber)
    //   .subscribe((solrData: SorlResponse) => {
    //     console.log(solrData.data);
    //     this.dataSource = new MatTableDataSource<JobDetails>(solrData.data);
    //     this.dataSource.paginator = this.paginator;
    //   });

    this.getJobDetails(this.startIndex, this.endIndex).subscribe((solrData) => {
      this.dataSource.data = solrData.data;
      this.totalPageNumber = solrData.cursor.total_records;
    });

    this.breakpointObserver
      .observe([Breakpoints.Handset])
      .subscribe((result) => {
        console.log(`result`, result);
        if (result.matches) {
          this.isMobile = true;
        }
      });
    this.breakpointObserver.observe([Breakpoints.Web]).subscribe((result) => {
      if (result.matches) {
        this.isDesktop = true;
      }
    });
    this.breakpointObserver
      .observe([Breakpoints.Tablet])
      .subscribe((result) => {
        if (result.matches) {
          this.isTablet = true;
          console.log(`tablet`, result);
        }
      });
  }

  startIndex = 0;
  endIndex = 3;
  page = {
    length: 0,
    pageSize: 3,
    pageIndex: 0,
  };
  pageIndexValue = 1;
  totalPageNumber = 0;

  onPageChange(pageEvent: PageEvent) {
    // this.calculateTotalPageSize();
    // this.startIndex = this.page.pageIndex * this.page.pageSize;
    this.startIndex = pageEvent.pageIndex * pageEvent.pageSize;
    console.log(`pageSizeOptions`, this.pageEvent);
    // this.endIndex = this.page.pageSize;
    this.endIndex = pageEvent.pageSize;
    // this.totalPageNumber = Math.ceil(this.page.length / this.page.pageSize);
    this.totalPageNumber = Math.ceil(pageEvent.length / pageEvent.pageSize);

    this.searchService
      .getAllJobDeatils(this.startIndex, this.endIndex, this.totalPageNumber)
      .subscribe((solrData: SorlResponse) => {
        console.log(solrData.data);
        this.dataSource.data = solrData.data;
      });
  }

  // calculateTotalPageSize() {
  //   if (this.page.length < this.paginator.pageSize * this.paginator.pageIndex) {
  //     this.paginator.pageIndex = 0;
  //   }
  //   if (this.paginator.pageSize >= 0 && this.paginator.pageIndex >= 0) {
  //     this.page = {
  //       length: this.page.length,
  //       pageSize: this.paginator.pageSize,
  //       pageIndex: this.paginator.pageIndex,
  //     };
  //   } else {
  //     this.page = {
  //       length: this.page.length,
  //       pageSize: this.page.pageSize,
  //       pageIndex: this.page.pageIndex,
  //     };
  //   }
  //   this.pageIndexValue = this.page.pageIndex + 1;
  // }

  // jobDetailsArray: JobDetails[] = [];
  // totalRecords: number = 0;

  // ngAfterViewInit(): void {
  //   this.dataSource.paginator = this.paginator;

  //   this.paginator.page
  //     .pipe(
  //       startWith({}),
  //       switchMap(() => {
  //         return this.getJobDetails(
  //           this.paginator.pageIndex + 1,
  //           this.paginator.pageSize
  //         ).pipe(catchError(() => of(null)));
  //       }),
  //       map((jobData) => {
  //         if (jobData == null) return [];
  //         this.totalRecords = jobData.cursor.total_records;
  //         console.log(`jobData`, jobData);
  //         return jobData.data;
  //       })
  //     )
  //     .subscribe((empData) => {
  //       this.jobDetailsArray = empData;
  //       console.log(`jobDetials`, empData);
  //       this.dataSource = new MatTableDataSource(this.jobDetailsArray);
  //       console.log(`oa`, this.paginator);
  //       console.log(`data sote`, this.dataSource);
  //     });
  // }
}
