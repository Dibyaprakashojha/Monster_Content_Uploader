import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { publish, refCount } from 'rxjs/operators';
import {
  MatSnackBar,
  MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';
import { NotificationComponent } from '../components/notification/notification.component';
@Injectable({
  providedIn: 'root',
})
export class NotificationServiceService {
  enableAutoHide = true;
  autoHideDuration = 5000;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  defaultClass = 'sfdc-notification';

  constructor(private snackBar: MatSnackBar) {}
  private _notification: BehaviorSubject<string> = new BehaviorSubject('');
  readonly notification$: Observable<string> = this._notification
    .asObservable()
    .pipe(publish(), refCount());

  notify(message: any) {
    this._notification.next(message);
    if (message) {
      this.snackBar.open(message, 'Close', {
        duration: 2000,
        verticalPosition: 'top',
      });
    }
  }

  notifyWithDuration(message: any, duration: any) {
    this._notification.next(message);
    if (message) {
      this.snackBar.open(message, 'Close', {
        duration: duration,
        verticalPosition: 'top',
      });
    }
  }

  info = (message: string) => {
    if (!message) {
      return;
    }
    const config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.panelClass = ['info-notification', this.defaultClass];
    config.data = {
      message,
      type: 'info',
    };
    config.duration = 8000;
    if (this.enableAutoHide) {
      config.duration = this.autoHideDuration;
    }

    this.snackBar.openFromComponent(NotificationComponent, config);
  };

  success = (message: string) => {
    if (!message) {
      return;
    }
    const config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.panelClass = ['success-notification', this.defaultClass];
    config.data = {
      message,
      type: 'success',
    };
    config.duration = 3000;
    if (this.enableAutoHide) {
      config.duration = this.autoHideDuration;
    }

    this.snackBar.openFromComponent(NotificationComponent, config);
  };

  warn = (message: string) => {
    if (!message) {
      return;
    }
    const config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.panelClass = ['warn-notification', this.defaultClass];
    config.duration = 10000;
    config.data = {
      message,
      type: 'warn',
    };
    if (this.enableAutoHide) {
      config.duration = this.autoHideDuration;
    }

    this.snackBar.openFromComponent(NotificationComponent, config);
  };

  error = (message: string) => {
    if (!message) {
      return;
    }
    const config = new MatSnackBarConfig();
    config.verticalPosition = this.verticalPosition;
    config.horizontalPosition = this.horizontalPosition;
    config.panelClass = ['error-notification', this.defaultClass];
    config.duration = 10000;
    config.data = {
      message,
      type: 'error',
    };
    if (this.enableAutoHide) {
      config.duration = this.autoHideDuration;
    }

    this.snackBar.openFromComponent(NotificationComponent, config);
  };
}
