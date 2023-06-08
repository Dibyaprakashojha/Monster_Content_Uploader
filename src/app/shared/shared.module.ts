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

@NgModule({
  declarations: [
    PaginatorDirective,
    UTCToLocalPipePipe,
    NotificationComponent,
    FilterByTextPipe,
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
  providers: [NotificationServiceService, MatSnackBarModule],
})
export class SharedModule {}
