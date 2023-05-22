import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConstants } from '../RoutingConstants';

@Component({
  selector: 'mcu-core',
  templateUrl: './core.component.html',
  styleUrls: ['./core.component.scss'],
})
export class CoreComponent {
  constructor(public router: Router) {}

  ngOnInit() {
    this.router.navigate([RoutingConstants.mcuBaseRoute]);
  }
}
