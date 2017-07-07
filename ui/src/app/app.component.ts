import { NewFieldCommand } from './commands/new-field-command';
import { Command } from 'app/commands/command';
import { CommandsMappingService } from 'app/services/commands-mapping.service';
import { ApplicationSocket } from 'app/application-socket';
import { SocketService } from 'app/services/socket.service';
import { CreateNewFieldDialogComponent } from 'app/create-new-field-dialog/create-new-field-dialog.component';
import { VehiclesWSService } from 'app/services/vehicles-w-s.service';
import { VehiclesService } from 'app/services/vehicles.service'
import { Component, OnInit, HostListener } from '@angular/core';
import { MdDialog } from '@angular/material';
import { Field } from 'app/field';

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
              private vehiclesService: VehiclesService,
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
    }).catch(reason => this.createAppSocket());
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

  @HostListener('document:keyup', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 
    this.vehiclesService.handle(event);
  }
}
