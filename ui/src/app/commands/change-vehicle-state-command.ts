import { Field } from 'app/field';
import { Command } from './command';
import { CanvasTabComponent } from "app/canvas-tab/canvas-tab.component";
import { VehicleState } from "app/vehicle-state";
import { VehicleDrawer } from "app/vehicle-drawer";
import { Vehicle } from "app/vehicle";

export class ChangeVehicleStateCommand implements Command<VehicleState> {

  constructor (private name: string, private vehicles: Array<Vehicle>) {}
 
  execute(state: VehicleState) {
    const vehicle = this.vehicles.find(vehicle => vehicle.field.id === state.fieldId && vehicle.uuid === state.vehicleId);
    if(vehicle) vehicle.state = state.transition;
  }

  getName(): string {
    return this.name;
  }
}
