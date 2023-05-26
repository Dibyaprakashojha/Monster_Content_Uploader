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
  jobList: any;

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
    this.searchService.searchByKeyWord(event.target.value).subscribe((data) => {
      console.log((event.target as HTMLInputElement).value);
      this.jobList = data;
    });
  }

  sortType = true;
  sortTheData() {
    this.searchService.SortByJobId(this.sortType).subscribe((data) => {
      this.jobList = data;
      this.sortType = !this.sortType;
    });
  }

  ngOnInit(): void {
    this.searchService.getAllJobDeatils().subscribe((data) => {
      this.jobList = data;
    });
    this.menuNameIdentifier();
  }
}
