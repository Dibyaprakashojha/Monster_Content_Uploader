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
  // search(event: any) {
  //   this.jobList.filter((each) => {
  //     if (each.job_id == event.target.value) {
  //       // this.jobList = [...this.filteredJobList];
  //       // this.filteredJobList.push(each);
  //     }
  //   });

  //   if (event.target.value == '') {
  //     if (this.router.url.includes('all-jobs')) {
  //       this.searchService
  //         .getAllJobsForMobile(this.documentIndex)
  //         .subscribe((solrData) => {
  //           this.jobList = solrData.data;
  //           console.log(`jobList`, this.jobList);
  //         });
  //     } else if (this.router.url.includes('my-jobs')) {
  //       this.searchService
  //         .getMyJobsForMobile(this.documentIndex, 1)
  //         .subscribe((solrData) => {
  //           this.jobList = solrData.data;
  //         });
  //     }
  //   }
  // }

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
  ngOnInit(): void {
    this.jobList=[]
    if (this.router.url.includes('all-jobs')) {
      this.searchService
        .getAllJobDeatils(this.documentIndexAllJobs,3,'ASC')
        .subscribe((solrData) => {
          this.jobList = solrData.data;
        });
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('my-jobs')) {
      this.searchService
        .getMyJobDetails(this.documentIndexMyJobs,3,'ASC',1)
        .subscribe((solrData) => {
          this.jobList = solrData.data;
        });
      console.log(`joblist`, this.jobList);
    }

    this.menuNameIdentifier();
  }

  onScroll(event: any): void {
    console.log('on Scroll method', event);
    if (this.router.url.includes('all-jobs')) {
      this.documentIndexAllJobs+=3
      console.log(this.documentIndexAllJobs)
      this.searchService
        .getAllJobsForMobile(this.documentIndexAllJobs , 'ASC')
        .subscribe((solrData: any) => {
          console;
          // solrData.data.map((each) => {
          //   this.jobList.push(each);
          // });
          this.jobList = [...this.jobList, ...solrData.data];

          console.log(this.jobList);
        });
      console.log(`joblist`, this.jobList);
    } else if (this.router.url.includes('my-jobs')) {
      this.documentIndexMyJobs+=3
      this.searchService
        .getMyJobsForMobile(this.documentIndexMyJobs + 3, 1)
        .subscribe((solrData: any) => {
          console.log(solrData);
          // this.jobList = [...this.jobList, ...solrData.data];
          this.jobList.push(...solrData.data)
          // solrData.data.map((each) => {
          //   this.jobList.push(each);
          //   console.log(this.jobList);
          // });
        });
    }
  }

  scrollUpDistance: number = 2;
}
