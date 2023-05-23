import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { JobsTableComponent } from './components/jobs-table/jobs-table.component';
import { ContentUploadFormComponent } from './components/content-upload-form/content-upload-form.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: 'my-jobs', component: JobsTableComponent },
      { path: 'create-job', component: ContentUploadFormComponent },
      { path: '', redirectTo: 'my-jobs', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContentUploadRoutingModule {}
