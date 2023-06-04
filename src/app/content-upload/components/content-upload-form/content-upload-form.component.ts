import { map, of, startWith } from 'rxjs';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import {
  Form,
  FormArray,
  FormBuilder,
  FormGroup,
  NgForm,
} from '@angular/forms';

@Component({
  selector: 'mcu-content-upload-form',
  templateUrl: './content-upload-form.component.html',
  styleUrls: ['./content-upload-form.component.scss'],
})
export class ContentUploadFormComponent {
  onSubmit() {}

  showCreative!: boolean;
  showSubForms(data: any) {
    // console.log(data);
    this.showCreative = data;
  }
}
