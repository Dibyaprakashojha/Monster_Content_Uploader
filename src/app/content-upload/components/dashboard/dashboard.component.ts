import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { EventEmitter, Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Output } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'mcu-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  isMenuOpen = false;
  contentMargin = 10;

  onToolbarMenuToggle() {
    this.isMenuOpen = !this.isMenuOpen;

    console.log('On toolbar toggled', this.isMenuOpen);

    if (this.isMenuOpen === false) {
      this.contentMargin = 5;
    } else {
      this.contentMargin = 15;
    }
  }

  constructor(private router: Router) {}

  menu!: string;
  activeMenuCompare() {
    if (this.router.url.includes('my-jobs')) {
      this.menu = 'my-jobs';
    } else if (this.router.url.includes('all-jobs')) {
      this.menu = 'all-jobs';
    } else if (this.router.url.includes('all-jobs')) {
      this.menu = 'all-jobs';
    } else if (this.router.url.includes('my-workflows')) {
      this.menu = 'my-workFlows';
    } else if (this.router.url.includes('my-tasks')) {
      this.menu = 'my-tasks';
    }
  }

  checkMenu(menuName: string): boolean {
    if (this.menu === menuName) {
      return true;
    }
    return false;
  }

  ngOnInit(): void {
    this.activeMenuCompare();
  }
}
