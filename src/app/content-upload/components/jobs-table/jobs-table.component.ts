import { Router } from '@angular/router';
import { SearchService } from './../../services/search.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { OnInit } from '@angular/core';
import { Component, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { JobDetails, SorlResponse } from 'src/app/shared/models/jobDetails';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { FormService } from '../../services/form.service';

@Component({
  selector: 'mcu-jobs-table',
  templateUrl: './jobs-table.component.html',
  styleUrls: ['./jobs-table.component.scss'],
  providers: [
    {
      provide: MAT_RADIO_DEFAULT_OPTIONS,
      useValue: { color: 'warn' },
    },
  ],
})
export class JobsTableComponent implements OnInit {
  pageSizeOptions = [5, 10];
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
    private searchService: SearchService,
    private router: Router,
    private formService: FormService
  ) {}

  applyFilter(event: Event) {
    let filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  active!: string;
  direction!: string;

  isDesktop!: boolean;
  isTablet!: boolean;

  ngOnInit(): void {
    if (this.router.url.includes('my-jobs')) {
      console.log(`sdfghj`, this.totalPageNumber);
      this.searchService
        .getMyJobDetails(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection,
          1
        )
        .subscribe((solrData) => {
          this.totalPageNumber = 0;
          this.dataSource.data = solrData.data;
          console.log(`myjobs`, solrData.cursor.totalRecords);
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    } else if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobDeatils(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection
        )
        .subscribe((solrData) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    }

    this.breakpointObserver
      .observe([Breakpoints.XSmall])
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
      .observe([Breakpoints.Tablet, Breakpoints.Small])
      .subscribe((result) => {
        console.log(`tablet`, result);

        if (result.matches) {
          this.isTablet = true;

          console.log(`tablet`, result);
        }
      });
  }

  startIndex = 0;
  endIndex = 5;
  page = {
    length: 0,
    pageSize: 5,
    pageIndex: 0,
  };
  pageIndexValue = 1;
  totalPageNumber = 0;
  sortDirection: string = 'asc';

  sortData(event: any) {
    this.sortDirection = event.direction;
    console.log(`event sort`, this.sortDirection);

    if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobDetails(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection,
          1
        )
        .subscribe((solrData) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    } else if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobDeatils(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection
        )
        .subscribe((solrData: SorlResponse) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
          console.log(`all`, solrData);
        });
    }
  }

  refresh() {
    if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobDetails(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection,
          1
        )
        .subscribe((solrData) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    } else if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobDeatils(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection
        )
        .subscribe((solrData: SorlResponse) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
          console.log(`all`, solrData);
        });
    }
  }

  onPageChange(pageEvent: PageEvent) {
    console.log(`pageIndex`, pageEvent.pageIndex);
    this.startIndex = pageEvent.pageIndex * pageEvent.pageSize;
    console.log(`pageSizeOptions`, this.pageEvent);
    this.endIndex = pageEvent.pageSize;
    this.totalPageNumber = Math.ceil(pageEvent.length / pageEvent.pageSize);

    console.log(this.startIndex, this.endIndex, this.totalPageNumber);

    if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobDetails(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection,
          1
        )
        .subscribe((solrData) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    } else if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobDeatils(
          this.startIndex,
          this.endIndex,
          this.totalPageNumber,
          this.sortDirection
        )
        .subscribe((solrData: SorlResponse) => {
          this.dataSource.data = solrData.data;
          this.totalPageNumber = solrData.cursor.totalRecords;
        });
    }
  }

  editTheJob(job: any) {
    // this.router.navigate(['./edit-from'], {
    //   queryParams: { jobId: job.job_id },
    // });
    this.router.navigate(['/apps/edit-form'], {
      queryParams: { jobId: job.job_id },
    });
    console.log(job.job_id);
  }
  viewTheJob(job: any) {
    // this.formService.getJobdetailsByJobId(job.job_id).subscribe((data: any) => {
    this.router.navigate(['/apps/view-form'], {
      queryParams: { jobId: job.job_id },
    });
    // });
    console.log(job.job_id);
  }
}
