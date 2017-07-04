import { CommandsMappingService } from './commands-mapping.service';
import { Field } from './field';
import { ApplicationSocketService } from './application-socket.service';
import { ApplicationSocket } from './application-socket';
import { VehicleSocket } from './vehicle-socket';
import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';

@Injectable()
export class SocketService {

  constructor(private appSocketService: ApplicationSocketService,
              private commandsMappingService: CommandsMappingService) { }

  createVehicleSocket(): VehicleSocket {
    return null;
  }

  getApplicationSocket(): ApplicationSocket {
    return new ApplicationSocket('supervisor/' + UUID.UUID(), this.appSocketService, this.commandsMappingService);
  }

}
