import { Injectable } from '@angular/core';
import { VehicleDrawer } from "app/vehicle-drawer";
import { CommandsMappingService } from "app/services/commands-mapping.service";
import { DrawVehicleCommand } from "app/commands/draw-vehicle-command";
import { RemoveVehicleCommand } from "app/commands/remove-vehicle-command";

@Injectable()
export class DrawVehicleCommandService {

  private drawers: Map<string, VehicleDrawer> = new Map<string, VehicleDrawer>();

  constructor(private commandsMappingService: CommandsMappingService) {
    this.commandsMappingService.addCommand(new DrawVehicleCommand('DRAW_VEHICLE', this.drawers));
    this.commandsMappingService.addCommand(new RemoveVehicleCommand('REMOVE_VEHICLE', this.drawers));
  }

  addVehicleDrawer(fieldId: string, drawer: VehicleDrawer): void {
    this.drawers.set(fieldId, drawer);
  }

  clearField(fieldId: string) {
    this.drawers.get(fieldId).clearField();
  }
}
