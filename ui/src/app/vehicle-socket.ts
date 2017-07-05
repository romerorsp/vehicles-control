import { SocketInterface } from './socket-interface';
import { environment } from 'environments/environment';

export class VehicleSocket {
  socket: WebSocket;

  constructor (private uri: string, private template: any = null) {
    this.socket = new WebSocket(environment.webSocketAddress + uri);
    this.socket.onclose(template.onclose || this.onClose);
    this.socket.onopen(template.onopen || this.onOpen);
    this.socket.onmessage(template.onmessage || this.onMessage);
    this.socket.onerror(template.onerror || this.onError);
  }

  onMessage(message: any): any {
    console.log('message' + message);
  }

  onOpen(event: any): any {
    console.log('open: ' + event);
  }

  onClose(event: any): any {
    console.log('close' + event);
  }

  onError(message: any): any {
    console.log('error' + message);
  }
}
