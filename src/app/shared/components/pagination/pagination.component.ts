import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { PageObjectInterface } from '../../models/PageObjectInterface';

@Component({
  selector: 'mcu-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss'],
})
export class PaginationComponent implements OnInit, OnChanges {
  @Input() length: any;
  @Input() pageSize: any;
  @Input() pageIndex: any;
  @Output() paginator = new EventEmitter<any>();

  @ViewChild(MatPaginator, { static: true }) matPaginator!: MatPaginator;

  pageSizeOptions: number[] = [5, 10, 25, 50, 100];
  totalPageNumber = 1;
  manualPageChangeControl!: FormControl;

  constructor() {}

  calculateTotalPageSize() {
    this.pageIndex = this.pageIndex
      ? Number(this.pageIndex)
      : this.matPaginator.pageIndex + 1;
    const pageSize = this.pageSize
      ? Number(this.pageSize)
      : this.matPaginator.pageSize;
    this.matPaginator.pageIndex = this.pageIndex - 1;
    this.matPaginator.pageSize = pageSize;

    this.totalPageNumber = Math.ceil(this.length / pageSize);
  }

  changePaginator() {
    if (this.pageSize !== this.matPaginator.pageSize) {
      this.pageIndex = 1;
      this.matPaginator.pageIndex = 0;
    } else {
      this.pageIndex = this.matPaginator.pageIndex + 1;
    }
    this.pageSize = this.matPaginator.pageSize;

    this.initializeForm();
    const pageObject: PageObjectInterface = {
      length: this.matPaginator.length,
      pageIndex: this.matPaginator.pageIndex,
      pageSize: this.matPaginator.pageSize,
      totalPages: this.totalPageNumber,
    };
    this.paginator.next(pageObject);
  }

  onpageValueChange(event: any) {
    if (
      event.target &&
      event.target.value &&
      (event.relatedTarget == null || event.relatedTarget == undefined)
    ) {
      if (Number(event.target.value) <= this.totalPageNumber) {
        this.matPaginator.pageIndex = Number(event.target.value) - 1;
        this.changePaginator();
      } else {
        console.log('');
      }
    }
  }

  initializeForm() {
    this.calculateTotalPageSize();
    this.manualPageChangeControl = new FormControl(this.pageIndex, [
      Validators.max(this.totalPageNumber),
      Validators.min(1),
      Validators.required,
    ]);
  }

  ngOnChanges() {
    this.initializeForm();
  }

  numberOnly(event: any): boolean {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  ngOnInit() {
    this.initializeForm();
    this.matPaginator.page.subscribe(() => {
      this.changePaginator();
    });
  }
}
