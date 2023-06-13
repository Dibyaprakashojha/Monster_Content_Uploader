import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'mcu-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModalComponent implements OnInit  {
  message:any
  confirmationComment = '';
  confirmationHeading = 'Confirmation';
  constructor(
      public dialogRef: MatDialogRef<ConfirmationModalComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }


  closeDialog() {
      const result = {
          isTrue: false
      };
      this.dialogRef.close();
  }


  ngOnInit() {
      this.message = this.data.message;
      if (this.data.confirmationHeading) {
          this.confirmationHeading = this.data.confirmationHeading;
      }
  }


  onYesClick() {
      const result = {
          isTrue: true,
          confirmationComment: ''
      };
      if (this.data.hasConfirmationComment) {
          if (this.data.isCommentRequired && this.confirmationComment.length === 0) {
              return;
          }
          result.confirmationComment = this.confirmationComment;
      }
      this.dialogRef.close(result);
  }


}

