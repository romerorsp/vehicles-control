import { CreateNewFieldDialogComponent } from './create-new-field-dialog/create-new-field-dialog.component';
import { VehiclesWSService } from './vehicles-w-s.service';
import { Component, OnInit } from '@angular/core';
import { MdDialog } from '@angular/material';
import { Field } from './field';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  selectedField: Field;
  fields: Array<Field> = new Array<Field>();
  title = 'Vehicles Control';

  constructor(private vehiclesWS: VehiclesWSService,
              private dialog: MdDialog) {}

  ngOnInit(): void {
    this.getFields();
  }

  fieldsIsEmpty(): boolean {
    return this.fields.length < 1;
  }

  getFields(): void {
    this.vehiclesWS.getFields().then(fields => this.fields = fields);
  }

  setCurrentField(field: Field): void {
    this.selectedField = field;
  }

  createNewField(): void {
    this.dialog.open(CreateNewFieldDialogComponent);
  }
}
