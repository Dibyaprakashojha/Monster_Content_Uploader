import { Component, Inject } from '@angular/core';
import {
  MatSnackBarRef,
  MAT_SNACK_BAR_DATA,
} from '@angular/material/snack-bar';

@Component({
  selector: 'mcu-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
})
export class NotificationComponent {
  isWarn = false;
  constructor(
    public snackBarRef: MatSnackBarRef<NotificationComponent>,
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) {}

  getIcon(): any {
    switch (this.data.type) {
      case 'success':
        return 'check_circle';
      case 'error':
        return 'block';
      case 'warn':
        this.isWarn = true;
        return 'warning';
      case 'info':
        return 'info';
    }
  }
}
