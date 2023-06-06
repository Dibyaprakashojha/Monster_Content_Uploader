import { SearchService } from './../../../services/search.service';
import { Router } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';

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

  filteredJobList: any[] = [];
  searchValue: any;
  search(event: any) {
    // let value = event.target.value.toString();
    this.jobList.forEach((each) => {
      if (each.job_id == event.target.value) {
        this.filteredJobList.push(each);
      }
    });
    this.jobList = [...this.filteredJobList];
    if (event.target.value == '') {
      if (this.router.url.includes('all-jobs')) {
        this.searchService
          .getAllJobsForMobile(this.documentIndex)
          .subscribe((solrData) => {
            this.jobList = solrData.data;
            console.log(`jobList`, this.jobList);
          });
      } else if (this.router.url.includes('my-jobs')) {
        this.searchService
          .getMyJobsForMobile(this.documentIndex, 1)
          .subscribe((solrData) => {
            this.jobList = solrData.data;
          });
      }
    }
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
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobsForMobile(this.documentIndex, 1)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
        });
      console.log(`joblist`, this.jobList);
    }

    this.menuNameIdentifier();
  }

  onScroll(): void {
    if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobsForMobile(this.documentIndex + 3, 'ASC')
        .subscribe((solrData) => {
          solrData.data.map((each) => {
            this.jobList.push(each);
          });
          console.log(this.jobList);
        });
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getMyJobsForMobile(this.documentIndex + 3, 1)
        .subscribe((solrData) => {
          solrData.data.map((each) => {
            this.jobList.push(each);
            console.log(this.jobList);
          });
        });
    }
  }

  // ngOnDestroy(): void {
  //   throw new Error('Method not implemented.');
  // }

  scrollUpDistance: number = 2;
}
