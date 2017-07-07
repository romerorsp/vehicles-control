import { Field } from 'app/field';
import { Command } from './command';
import { CanvasTabComponent } from "app/canvas-tab/canvas-tab.component";
import { VehicleState } from "app/vehicle-state";
import { VehicleDrawer } from "app/vehicle-drawer";
import { Vehicle } from "app/vehicle";

export class ChangeVehicleStateCommand implements Command<VehicleState> {

  constructor (private name: string, private vehicles: Array<Vehicle>) {}
 
  execute(state: VehicleState) {
    this.vehicles.find(vehicle => vehicle.fieldId === state.fieldId && vehicle.uuid === state.vehicleId).state = state.transition;
  }

  getName(): string {
    return this.name;
  }
}
