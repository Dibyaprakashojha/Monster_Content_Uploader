import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { EventEmitter, Input, ViewChild, AfterViewInit } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Output } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'mcu-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  isMenuOpen = false;
  contentMargin = 10;

  @ViewChild('mobileSideNav') mobileSideNav!: MatSidenav;

  onToolbarMenuToggle() {
    this.isMenuOpen = !this.isMenuOpen;

    console.log('On toolbar toggled', this.isMenuOpen);

    if (this.isMenuOpen === false) {
      this.contentMargin = 5;
    } else {
      this.contentMargin = 15;
    }
  }

  mobileMenuOpen = false;
  onMobileToolBarToggle() {
    this.mobileMenuOpen = !this.mobileMenuOpen;
    if (this.mobileMenuOpen) {
      this.mobileSideNav.close();
    } else {
      this.mobileSideNav.open();
    }
  }

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngAfterViewInit(): void {}

  menu!: string;
  activeMenuCompare() {
    if (this.router.url.includes('my-jobs')) {
      this.menu = 'my-jobs';
    } else if (this.router.url.includes('all-jobs')) {
      this.menu = 'all-jobs';
    } else if (this.router.url.includes('my-workflows')) {
      this.menu = 'my-workflows';
    } else if (this.router.url.includes('my-tasks')) {
      this.menu = 'my-tasks';
    } else if (this.router.url.includes('content-manager')) {
      this.menu = 'content-manager';
    } else if (this.router.url.includes('assets-team')) {
      this.menu = 'assets-team';
    }
  }

  checkMenu(menuName: string): boolean {
    this.activeMenuCompare();
    if (this.menu === menuName) {
      return true;
    }
    return false;
  }

  isMobile!: boolean;
  isDesktop!: boolean;
  isTablet!: boolean;
  ngOnInit(): void {
    this.breakpointObserver
      .observe([Breakpoints.XSmall, Breakpoints.Small])
      .subscribe((result) => {
        console.log(`result`, result);
        if (result.matches) {
          this.isMobile = true;
          this.isTablet = false;
          this.isDesktop = false;
        }
      });
    this.breakpointObserver
      .observe([Breakpoints.Large, Breakpoints.XLarge])
      .subscribe((result) => {
        if (result.matches) {
          this.isMobile = false;
          this.isTablet = false;
          this.isDesktop = true;
        }
      });
    this.breakpointObserver
      .observe([Breakpoints.Medium])
      .subscribe((result) => {
        if (result.matches) {
          this.isMobile = false;
          this.isTablet = true;
          this.isDesktop = false;
        }
      });
  }
}
