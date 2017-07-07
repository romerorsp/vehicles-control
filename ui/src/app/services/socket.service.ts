import { CommandsMappingService } from './commands-mapping.service';
import { Field } from 'app/field';
import { ApplicationSocket } from 'app/application-socket';
import { VehicleSocket } from 'app/vehicle-socket';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';

@Injectable()
export class SocketService {
  
  applicationSocket: any;

  constructor(private commandsMappingService: CommandsMappingService) { }

  createVehicleSocket(field:Field, x: number, y: number, uuid: UUID): VehicleSocket {
    return new VehicleSocket(field.id + '/' + uuid + '/' + x + '/' + y, this.commandsMappingService);
  }

  getApplicationSocket(): ApplicationSocket {
    if(this.applicationSocket == null) {
      return (this.applicationSocket = new ApplicationSocket('supervisor/' + UUID.UUID(), this.commandsMappingService));
    }
    return this.applicationSocket;
  }
}
