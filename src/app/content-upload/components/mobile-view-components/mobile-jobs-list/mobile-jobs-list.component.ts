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

  @Input() jobList: any;

  constructor(
    private breakPointObserver: BreakpointObserver,
    private router: Router
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

  ngOnInit(): void {
    this.menuNameIdentifier();
  }
}
