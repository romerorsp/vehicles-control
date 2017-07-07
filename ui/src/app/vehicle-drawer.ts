import { VehicleState } from "app/vehicle-state";

export interface VehicleDrawer {

    drawVehicle(state: VehicleState): void;
    drawRemoveVehicle(state: VehicleState): void;
    clearField(): void;
}
