import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { JobsTableComponent } from './components/jobs-table/jobs-table.component';
import { CreativeFormComponent } from './components/creative-form/creative-form.component';
import { BasicFormComponent } from './components/basic-form/basic-form.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: 'my-jobs', component: JobsTableComponent },
      { path: 'all-jobs', component: JobsTableComponent },
      { path: 'my-workflows', component: JobsTableComponent },
      { path: 'content-manager', component: JobsTableComponent },
      { path: 'assets-team', component: JobsTableComponent },
      { path: 'basic-form', component: BasicFormComponent },
      { path: '', redirectTo: 'my-jobs', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContentUploadRoutingModule {}
