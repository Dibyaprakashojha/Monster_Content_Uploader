import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'mcu-content-upload-form',
  templateUrl: './content-upload-form.component.html',
  styleUrls: ['./content-upload-form.component.scss'],
})
export class ContentUploadFormComponent {
  myForm!: FormGroup;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.myForm = this.formBuilder.group({
      brand: [''],
      productLine: ['', []],
      country: [''],
      albumName: [''],
      department: [''],
    });
  }

  submitForm() {
    if (!this.myForm?.invalid) {
      console.log(`nj`, this.myForm);
    }
    this.myForm?.reset();
  }
}
