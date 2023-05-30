import { SearchService } from './../../../services/search.service';
import { Route, Router } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'mcu-mobile-jobs-list',
  templateUrl: './mobile-jobs-list.component.html',
  styleUrls: ['./mobile-jobs-list.component.scss'],
})
export class MobileJobsListComponent implements OnInit {
  isMobile!: boolean;
  jobList: any[] = [];

  constructor(
    private breakPointObserver: BreakpointObserver,
    private router: Router,
    private searchService: SearchService
  ) {}

  menuName!: string;

  menuNameIdentifier() {
    if (this.router.url.includes('my-jobs')) {
      this.menuName = 'MY JOBS';
    } else if (this.router.url.includes('all-jobs')) {
      this.menuName = 'ALL JOBS';
    } else if (this.router.url.includes('my-workflows')) {
      this.menuName = 'MY WORKFLOWS';
    } else if (this.router.url.includes('my-tasks')) {
      this.menuName = 'MY TASKS';
    } else if (this.router.url.includes('content-manager')) {
      this.menuName = 'CONTENT MANAGER';
    } else if (this.router.url.includes('assets-team')) {
      this.menuName = 'ASSETS TEAM';
    }
  }

  search(event: any) {
    // this.searchService.searchByKeyWord(event.target.value).subscribe((data) => {
    //   console.log((event.target as HTMLInputElement).value);
    //   this.jobList = data;
    // });
    // this.jobList.
  }

  sortType = true;
  sortTheData() {
    this.sortType = !this.sortType;
    if (this.sortType === true) {
      this.jobList.sort((a, b) => (a.JOB_ID < b.JOB_ID ? 1 : -1));
    } else {
      this.jobList.sort((a, b) => (a.JOB_ID > b.JOB_ID ? 1 : -1));
    }
  }

  showSearchBar: boolean = false;
  toggleSearch() {
    this.showSearchBar = !this.showSearchBar;
  }

  documentIndex = 0;
  ngOnInit(): void {
    if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobsForMobile(this.documentIndex)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
        });
    } else if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobsForMobile(this.documentIndex, 1)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
        });
    }

    this.menuNameIdentifier();
  }

  onScroll(): void {
    if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobsForMobile(this.documentIndex + 3)
        .subscribe((solrData) => {
          solrData.data.map((each) => {
            var index = this.jobList.findIndex(
              (job) => each.job_id == job.job_id
            );
            if (index === -1) {
              this.jobList.push(each);
            }
          });
        });
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobsForMobile(this.documentIndex + 3, 1)
        .subscribe((solrData) => {
          solrData.data.map((each) => {
            var index = this.jobList.findIndex(
              (job) => each.job_id == job.job_id
            );
            if (index === -1) {
              this.jobList.push(each);
            }
          });
        });
    }
  }

  scrollUpDistance: number = 2;
}
