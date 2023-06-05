import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContentUploadRoutingModule } from './content-upload-routing.module';
import { MaterialModule } from '../material/material.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SharedModule } from '../shared/shared.module';
import { JobsTableComponent } from './components/jobs-table/jobs-table.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MobileJobsListComponent } from './components/mobile-view-components/mobile-jobs-list/mobile-jobs-list.component';
import { CardViewComponent } from './components/mobile-view-components/card-view/card-view.component';
import { HttpClientModule } from '@angular/common/http';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BasicFormComponent } from './components/basic-form/basic-form.component';
import { CreativeFormComponent } from './components/creative-form/creative-form.component';
@NgModule({
  declarations: [
    DashboardComponent,
    JobsTableComponent,
    MobileJobsListComponent,
    CardViewComponent,
    BasicFormComponent,
    CreativeFormComponent,
  ],
  imports: [
    CommonModule,
    LayoutModule,
    ContentUploadRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    InfiniteScrollModule,
  ],
  exports: [],
  providers: [],
})
export class ContentUploadModule {}
