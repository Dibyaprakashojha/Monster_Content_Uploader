import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { EventEmitter, Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Output } from '@angular/core';

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
      this.contentMargin = 10;
    }
  }

  constructor() {}

  ngOnInit(): void {}
}
