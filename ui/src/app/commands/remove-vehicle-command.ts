import { Field } from 'app/field';
import { Command } from './command';
import { CanvasTabComponent } from "app/canvas-tab/canvas-tab.component";
import { VehicleState } from "app/vehicle-state";
import { VehicleDrawer } from "app/vehicle-drawer";

export class RemoveVehicleCommand implements Command<VehicleState> {

  constructor (private name: string, private drawers: Map<string, VehicleDrawer>) {}
 
  execute(state: VehicleState) {
    this.drawers.get(state.fieldId).drawRemoveVehicle(state);
  }

  getName(): string {
    return this.name;
  }
}
