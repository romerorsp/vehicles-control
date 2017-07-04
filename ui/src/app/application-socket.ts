import { CommandsMappingService } from './commands-mapping.service';
import { Field } from './field';
import { ApplicationSocketService } from './application-socket.service';
import { environment } from 'environments/environment';

export class ApplicationSocket {

  socket: WebSocket;

  constructor (private uri: string,
               private appSocket: ApplicationSocketService,
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

  onMessage(message: MessageEvent): any {
    const command = JSON.parse(message.data);
    const cmd = this.commandsMappingService.fromName(command.getName());
    if (cmd) {
      cmd.execute(command.payload);
    }
  }

  onOpen(event: Event): any {
    console.log('open: ' + event);
    console.log(event);
  }

  onClose(event: Event): any {
    console.log('close' + event);
    console.log(event);
  }

  onError(event: Event): any {
    console.log('error' + event);
    console.log(event);
  }
}
