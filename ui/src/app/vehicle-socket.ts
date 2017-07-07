import { environment } from 'environments/environment';
import {CommandsMappingService } from 'app/services/commands-mapping.service';
import { VehicleState } from "app/vehicle-state";

export class VehicleSocket {

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

  send(state: VehicleState): void {
    this.socket.send(state);
  }

  close(): void {
    this.socket.close();
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
    console.log('open: ' + event);
    console.log(event);
  }

  onClose(event: Event): any {
    //TODO: Take the opportunity to advice the user that the connection was lost...
    console.log('close' + event);
    console.log(event);
  }

  onError(event: Event): any {
    // I'm assuming it never fails... This should be reviewed in a real application.
    console.log('error' + event);
    console.log(event);
  }
}
