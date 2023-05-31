import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PaginatorDirective } from './directives/paginator.directive';
import { UTCToLocalPipePipe } from './pipes/utcto-local-pipe.pipe';
// import { CustomPaginatorDirective } from './directives/custom-paginator.directive';

@NgModule({
  declarations: [PaginatorDirective, UTCToLocalPipePipe],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
  ],
  exports: [
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    PaginatorDirective,
    UTCToLocalPipePipe,
  ],
})
export class SharedModule {}
