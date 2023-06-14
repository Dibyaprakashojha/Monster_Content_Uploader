import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NotificationServiceService } from '../../services/notification-service.service';
import { OtmmService } from '../../services/otmm.service';

@Component({
  selector: 'mcu-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent {
  constructor(
    public dialogRef: MatDialogRef<UploadComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private notificationService: NotificationServiceService,
    private otmmService:OtmmService
  ) {}
  uploadedFiles = [];
  uploadProcessing = false;
  assetsList = [];
  // sortBy;
 


  closeDialog() {
    this.dialogRef.close(3);
  }
  udploadFileEvent(files:any) {
    this.uploadedFiles = files;
  }
  uploadFiles() {
    if (this.uploadedFiles.length > 0) {
      this.uploadProcessing = true;
      this.otmmService
        .startUpload(this.uploadedFiles, this.data, this.data.isRevisionUpload)
        .subscribe(
          // next:(response:any) => {
          //   // this.otmmAssetService.delay().then((any) => {
          //   console.log('wait for some time');
          //   this.notificationService.success('Asset(s) upload is in progress.')
          //   console.log('res ',response);
          //   if(response =='Success'){
          //     console.log("hiiis")
          //   }
          //   this.dialogRef.close(true);
          // },
          // error:() => {
          //   this.uploadProcessing = false;
          //   this.notificationService.error(
          //     'Something went wrong while uploading the file(s).'
          //   );
          // }

         { 
          next:(response:any)=>{
          this.notificationService.success('Asset(s) upload is in progress.')
            console.log('res ',response);
            if(response =='Success'){
              console.log("hiiis")
            }
            this.dialogRef.close(true);
          },
          error:()=>{
            this.uploadProcessing = false;
            this.notificationService.error(
              'Something went wrong while uploading the file(s).'
            );
          }
           });
    } else {
      this.notificationService.info(
        'Please select or drag and drop file(s) to upload.'
      );
    }
  }
  

  ngOnInit() {}

}