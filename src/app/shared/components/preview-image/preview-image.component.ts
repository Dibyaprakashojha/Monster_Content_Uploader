import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'mcu-preview-image',
  templateUrl: './preview-image.component.html',
  styleUrls: ['./preview-image.component.scss'],
})
export class PreviewImageComponent implements OnInit {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
  imageList: any;
  ngOnInit(): void {
    console.log('data:', this.data.imageList);
    this.imageList = this.data.imageList;
  }
}
