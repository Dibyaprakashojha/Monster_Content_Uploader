import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContentUploadRoutingModule } from './content-upload-routing.module';
import { MaterialModule } from '../material/material.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SharedModule } from '../shared/shared.module';
import { JobsTableComponent } from './components/jobs-table/jobs-table.component';
import { ContentUploadFormComponent } from './components/content-upload-form/content-upload-form.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MobileJobsListComponent } from './components/mobile-view-components/mobile-jobs-list/mobile-jobs-list.component';
import { CardViewComponent } from './components/mobile-view-components/card-view/card-view.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    DashboardComponent,
    JobsTableComponent,
    ContentUploadFormComponent,
    MobileJobsListComponent,
    CardViewComponent,
  ],
  imports: [
    CommonModule,
    LayoutModule,
    ContentUploadRoutingModule,
    SharedModule,
    HttpClientModule,
  ],
})
export class ContentUploadModule {}
