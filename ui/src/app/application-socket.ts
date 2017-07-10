import { CommandsMappingService } from 'app/services/commands-mapping.service';
import { Field } from 'app/field';
import { Command } from 'app/commands/command';
import { environment } from 'environments/environment';
import { Vehicle } from "app/vehicle";
import { VehicleState } from "app/vehicle-state";

export class ApplicationSocket {

  socket: WebSocket;

  constructor (private uri: string,
               private commandsMappingService: CommandsMappingService,
               private template: any = null) {
    this.socket = new WebSocket(environment.webSocketAddress + uri);
    if (!template) {
      template = {};
    }
    this.socket.onclose = template.onclose ? event => template.onclose(event) : event => this.onClose(event);
    this.socket.onopen = template.onopen ? event => template.onopen(event) : event => this.onOpen(event);
    this.socket.onmessage = template.onmessage ? message => template.onmessage(message) : message => this.onMessage(message);
    this.socket.onerror = template.onerror ? event => template.onerror(event) : event => this.onError(event);
  }

  changeState(vehicle: Vehicle, transition: string) {
    if(vehicle != null && vehicle.state != transition) {
      this.socket.send(JSON.stringify(new VehicleState(vehicle.x, vehicle.y, transition, vehicle.fieldId, vehicle.uuid as string)));
    }
  }

  onMessage(message: MessageEvent): any {
    const command = JSON.parse(message.data);
    const cmd = this.commandsMappingService.fromName(command.name);
    if (cmd) {
      cmd.execute(command.payload);
    }
  }

  onOpen(event: Event): any {
    // Nothing to do here for now...
    console.log('App Socket open');
  }

  onClose(event: Event): any {
    //TODO: Take the opportunity to advice the user that the connection was lost...
    console.log('App Socket close');
  }

  onError(event: Event): any {
    // I'm assuming it never fails... This should be reviewed in a real application.
    console.log('App Socket error');
  }
}
