import { Component, OnInit, Inject } from '@angular/core';
import {MD_DIALOG_DATA} from '@angular/material';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'create-new-field-dialog',
  templateUrl: './create-new-field-dialog.component.html',
  styleUrls: ['./create-new-field-dialog.component.css']
})
export class CreateNewFieldDialogComponent implements OnInit {

  width = 30;
  height = 0;
  close = false;
  name: string;

  constructor(@Inject(MD_DIALOG_DATA) public data: any) {}

  ngOnInit() {}

  onWidthInput(event: any) {
    this.width = event.value;
  }

  onHeightInput(event: any) {
    this.height = event.value;
  }
  createNewField(): void {
    console.log('SENDING');
    this.close = true;
  }
}
