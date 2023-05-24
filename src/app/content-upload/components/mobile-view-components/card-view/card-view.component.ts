import { Input, OnInit } from '@angular/core';
import { Component } from '@angular/core';

@Component({
  selector: 'mcu-card-view',
  templateUrl: './card-view.component.html',
  styleUrls: ['./card-view.component.scss'],
})
export class CardViewComponent implements OnInit {
  @Input() job: any;

  ngOnInit(): void {
    console.log(`jobis`, this.job);
  }
}
