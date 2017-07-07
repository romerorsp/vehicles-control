import { Injectable, OnInit } from '@angular/core';
import { Vehicle } from "app/vehicle";
import { VehicleRemover } from "app/vehicle-remover";
import { DrawVehicleCommandService } from "app/services/draw-vehicle-command.service";
import { SocketService } from "app/services/socket.service";
import { CommandsMappingService } from "app/services/commands-mapping.service";
import { ChangeVehicleStateCommand } from "app/commands/change-vehicle-state-command";

@Injectable()
export class VehiclesService implements VehicleRemover, OnInit {

  private vehicles: Array<Vehicle> = new Array<Vehicle>();

  private current: Vehicle;

  constructor(private drawVehicleCommandService: DrawVehicleCommandService,
              private socketService: SocketService,
              private commandsMappingService: CommandsMappingService) { }

  ngOnInit(): void {
    this.commandsMappingService.addCommand(new ChangeVehicleStateCommand('CHANGE_VEHICLE_STATE', this.vehicles));
  }

  addVehicle(vehicle: Vehicle): boolean {
    this.vehicles.push(vehicle);
    this.current = vehicle;
    return true;
  }

  removeVehicle(vehicle: Vehicle): void {
    const index = this.vehicles.indexOf(vehicle);
    this.vehicles = this.vehicles.splice(index, 1);
    this.drawVehicleCommandService.clearField(vehicle.fieldId);
  }

  handle(event: KeyboardEvent): void {
    const key = event.code;
    const appSocket = this.socketService.getApplicationSocket();
    switch(key) {
      case "ArrowUp": {
        appSocket.changeState(this.current, "MOVE_UP");
        break;
      }

      case "ArrowDown": {
        appSocket.changeState(this.current, "MOVE_DOWN");
        break;
      }

      case "ArrowLeft": {
        appSocket.changeState(this.current, "MOVE_LEFT");
        break;
      }

      case "ArrowRight": {
        appSocket.changeState(this.current, "MOVE_RIGHT");
        break;
      }

      case "Space": {
        appSocket.changeState(this.current, "PAUSE");
        break;
      }

      case "Backspace": {
        appSocket.changeState(this.current, "FINISH");
        break;
      }
    }
  }
}