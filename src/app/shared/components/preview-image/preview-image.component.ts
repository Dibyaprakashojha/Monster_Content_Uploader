import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { OtmmService } from '../../services/otmm.service';
import { environment as env } from 'src/environments/environment';
@Component({
  selector: 'mcu-preview-image',
  templateUrl: './preview-image.component.html',
  styleUrls: ['./preview-image.component.scss'],
})
export class PreviewImageComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private otmmService: OtmmService
  ) {}
  imageList!: any[];
  responsiveOptions!: any[];
  bucketName!: any;
  finalHitCount!: any;

  ngOnInit(): void {
    console.log('data:', this.data);
    this.finalHitCount = this.data.finalHitCount;
    this.bucketName = this.data.bucketName;
    this.otmmService
      .otmmMetadataSearch(
        env.searchConfigId,
        0,
        this.finalHitCount,
        this.bucketName
      )
      .subscribe({
        next: (res: any) => {
          this.imageList = res.search_result_resource.asset_list.map(
            (asset: any) => {
              return asset.delivery_service_url;
            }
          );
          console.log(`Metadata: `, res);
        },
      });
  }
}
