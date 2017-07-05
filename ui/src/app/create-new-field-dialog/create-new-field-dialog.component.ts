import { Field } from './../field';
import { VehiclesWSService } from './../services/vehicles-w-s.service';
import { Component, OnInit, Inject } from '@angular/core';
import {MD_DIALOG_DATA, MdDialogRef} from '@angular/material';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'create-new-field-dialog',
  templateUrl: './create-new-field-dialog.component.html',
  styleUrls: ['./create-new-field-dialog.component.css']
})
export class CreateNewFieldDialogComponent implements OnInit {

  errorMessage = '';
  public width = 0;
  public height = 0;
  public fName: string;

  constructor(@Inject(MD_DIALOG_DATA) public data: any,
              private vehiclesWS: VehiclesWSService,
              private dialogRef: MdDialogRef<CreateNewFieldDialogComponent>) {}

  ngOnInit() {}

  clearError(): void {
    this.errorMessage = '';
  }

  onWidthInput(event: any) {
    this.clearError();
    this.width = event.value;
  }

  onHeightInput(event: any) {
    this.clearError();
    this.height = event.value;
  }

  createNewField(): void {
    this.clearError();
    if ((!this.fName) || this.fName.trim().length < 1) {
      this.errorMessage = 'The Field Name is required';
    } else if (this.width < 1) {
      this.errorMessage = 'The width requires a number greater than 0';
        } else if (this.height < 1) {
      this.errorMessage = 'The height requires a number greater than 0';
    } else {
      this.vehiclesWS.addField(new Field(this.fName, this.width, this.height))
                     .then(result => {
                       if (!result) {
                         this.errorMessage = 'Something went wrong during the request';
                       } else {
                        this.dialogRef.close();
                       }
                     })
                     .catch(this.handleError);
    }
  }

  private handleError(error: any): Promise<any> {
    this.errorMessage = error.message || error;
    return Promise.reject(error.message || error);
  }
}
