import { SearchService } from './../../../services/search.service';
import { Router } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { InfiniteScrollDirective } from 'ngx-infinite-scroll';

@Component({
  selector: 'mcu-mobile-jobs-list',
  templateUrl: './mobile-jobs-list.component.html',
  styleUrls: ['./mobile-jobs-list.component.scss'],
})
export class MobileJobsListComponent implements OnInit {
  isMobile!: boolean;
  jobList: any[] = [];

  @ViewChild(InfiniteScrollDirective) infiniteScroll!: InfiniteScrollDirective;

  constructor(
    private breakPointObserver: BreakpointObserver,
    private router: Router,
    private searchService: SearchService
  ) {}

  menuName!: string;
  searchText!: string;
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

  documentIndexAllJobs = 0;
  documentIndexMyJobs = 0;
  loadRecord=5;
  ngOnInit(): void {
    this.jobList = [];
    if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getInitialAllJobsFormobile(this.documentIndexAllJobs)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
          this.documentIndexAllJobs+=this.loadRecord
        });
        
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getInitialMyJobsFormobile(this.documentIndexMyJobs)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
          this.documentIndexMyJobs+=this.loadRecord
        });
      console.log(`joblist`, this.jobList);
    }

    this.menuNameIdentifier();
  }

  onScroll(event: any): void {
    console.log('on Scroll method', event);
    if (this.router.url.includes('all-jobs')) {
      // this.documentIndexAllJobs += 3;
      console.log(this.documentIndexAllJobs);
      // this.infiniteScroll.ngOnDestroy();
      // this.infiniteScroll.setup();

      this.searchService
        .getAllJobsForMobile(this.documentIndexAllJobs, 'ASC')
        .subscribe((solrData: any) => {
      this.documentIndexAllJobs+=this.loadRecord
          
          // this.jobList.push.apply(solrData.data)
          this.jobList = [...this.jobList, ...solrData.data];


          console.log(this.jobList);
        });
    } else if (this.router.url.includes('my-jobs')) {
      
      // this.documentIndexMyJobs += 3;
      


      this.searchService
        .getMyJobsForMobile(this.documentIndexMyJobs, 1)
        .subscribe((solrData: any) => {
      this.documentIndexMyJobs+=this.loadRecord

          console.log(solrData);
          this.jobList = [...this.jobList, ...solrData.data];
        });
    }
    // this.infiniteScroll.ngOnDestroy();
    // this.infiniteScroll.setup();
  }

  // scrollUpDistance: number = 2;
}
