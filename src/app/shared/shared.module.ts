import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PaginatorDirective } from './directives/paginator.directive';
import { UTCToLocalPipePipe } from './pipes/utcto-local-pipe.pipe';
import { NotificationComponent } from './components/notification/notification.component';
import { NotificationServiceService } from './services/notification-service.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FilterByTextPipe } from './pipes/filter-by-text.pipe';
import { UploadComponent } from './components/upload/upload.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { ConfirmationModalComponent } from './components/confirmation-modal/confirmation-modal.component';
import { OtmmService } from './services/otmm.service';
import { PreviewImageComponent } from './components/preview-image/preview-image.component';

@NgModule({
  declarations: [
    PaginatorDirective,
    UTCToLocalPipePipe,
    NotificationComponent,
    FilterByTextPipe,
    UploadComponent,
    FileUploadComponent,
    ConfirmationModalComponent,
    PreviewImageComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    MatSnackBarModule,
  ],
  exports: [
    MaterialModule,
    FormsModule,
    NotificationComponent,
    ReactiveFormsModule,
    PaginatorDirective,
    UTCToLocalPipePipe,
    FilterByTextPipe,
  ],
  providers: [NotificationServiceService, MatSnackBarModule,OtmmService],
})
export class SharedModule {}
