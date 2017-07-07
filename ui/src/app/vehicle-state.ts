export class VehicleState {
    
    vehicleId: string;
    fieldId: string;
    public transition: string;

    public posY: number;
    public posX: number;

    constructor(posX: number,
                posY: number,
                transition: string,
                fieldId: string,
                vehicleId: string) {
                     this.posX = posX;
                     this.posY = posY;
                     this.transition = transition;
                     this.fieldId = fieldId;
                     this.vehicleId = vehicleId;
    }
}
