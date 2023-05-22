import { ContentUploadModule } from './../content-upload/content-upload.module';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoreComponent } from './core.component';
import { RoutingConstants } from '../RoutingConstants';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('../content-upload/content-upload.module').then(
        (m) => m.ContentUploadModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoreRoutingModule {}
