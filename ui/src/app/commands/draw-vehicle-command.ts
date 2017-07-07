import { Field } from 'app/field';
import { Command } from './command';
import { CanvasTabComponent } from "app/canvas-tab/canvas-tab.component";
import { VehicleState } from "app/vehicle-state";
import { VehicleDrawer } from "app/vehicle-drawer";

export class DrawVehicleCommand implements Command<VehicleState> {

  constructor (private name: string, private drawers: Map<string, VehicleDrawer>) {}
 
  execute(state: VehicleState) {
    if(this.drawers.get(state.fieldId) != null) {
      this.drawers.get(state.fieldId).drawVehicle(state);
    }
  }

  getName(): string {
    return this.name;
  }
}
