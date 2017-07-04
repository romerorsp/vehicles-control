import { NewFieldCommand } from './commands/new-field-command';
import { Command } from './commands/command';
import { CommandsMappingService } from './commands-mapping.service';
import { ApplicationSocket } from './application-socket';
import { SocketService } from './socket.service';
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
  title = 'Vehicles Control';
  private appSocket: ApplicationSocket;
  fields: Array<Field> = new Array<Field>();

  constructor(private vehiclesWS: VehiclesWSService,
              private socketService: SocketService,
              private commandsMappingService: CommandsMappingService,
              private dialog: MdDialog) {}

  ngOnInit(): void {
    this.getFields();
  }

  fieldsIsEmpty(): boolean {
    return this.fields.length < 1;
  }

  getFields(): void {
    this.vehiclesWS.getFields().then(fields => {
      this.fields = fields;
      this.appSocket = this.socketService.getApplicationSocket();
      this.commandsMappingService.addCommand(new NewFieldCommand('NEW_FIELD', this.fields));
    }).catch(this.createAppSocket);
  }

  createAppSocket(): any {
    this.appSocket = this.socketService.getApplicationSocket();
    this.commandsMappingService.addCommand(new NewFieldCommand('NEW_FIELD', this.fields));
  }

  setCurrentField(field: Field): void {
    this.selectedField = field;
  }

  createNewField(): void {
    this.dialog.open(CreateNewFieldDialogComponent);
  }
}
