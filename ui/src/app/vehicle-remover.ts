import { Vehicle } from "app/vehicle";

export interface VehicleRemover {
    removeVehicle(vehicle: Vehicle): void;
}
