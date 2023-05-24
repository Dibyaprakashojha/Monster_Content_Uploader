import { Component, Input } from '@angular/core';

@Component({
  selector: 'mcu-mcu-pagination',
  templateUrl: './mcu-pagination.component.html',
  styleUrls: ['./mcu-pagination.component.scss'],
})
export class McuPaginationComponent {
  @Input() pageSize: any;
  @Input() pageIndex: any;
}
