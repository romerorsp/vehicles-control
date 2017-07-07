import { VehicleSocket } from "app/vehicle-socket";
import { globalVehiclesSettings } from "app/global-vehicles-setting";
import { UUID } from "angular2-uuid";
import { VehicleState } from "app/vehicle-state";
import { VehicleRemover } from "app/vehicle-remover";

export class Vehicle {

    public uuid: UUID;
    public y: number;
    public x: number;
    public fieldId: string;
    private timerToken: any;
    public state: string = "CREATE";

    private static COORDINATES: Map<string, any> = new Map(
        [
            ["CREATE", globalVehiclesSettings.downCarCoordinates],
            ["FINISH", globalVehiclesSettings.downCarCoordinates],
            ["MOVE_DOWN", globalVehiclesSettings.downCarCoordinates],
            ["MOVE_UP", globalVehiclesSettings.upCarCoordinates],
            ["MOVE_LEFT", globalVehiclesSettings.leftCarCoordinates],
            ["MOVE_RIGHT", globalVehiclesSettings.rightCarCoordinates],
        ]
    );

    constructor(x: number,
        y: number,
        fieldId: string,
        uuid: UUID,
        private addCoordinates: Function,
        private socket: VehicleSocket,
        private remover: VehicleRemover) {
        this.x = x;
        this.y = y;
        this.fieldId = fieldId;
        this.uuid = uuid;
        const activity = () => {
            if (this.state === "CREATE") {
                socket.send(new VehicleState(x, y, "PAUSE", this.fieldId, this.uuid as string));
            } else if (this.state === "END" || this.state === "FINISH") {
                this.socket.close();
                this.remover.removeVehicle(this);
                clearInterval(this.timerToken);
            } else if (this.state === "MOVE_UP") {
                socket.send(new VehicleState(x, ++y, "MOVE_UP", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_DOWN") {
                socket.send(new VehicleState(x, --y, "MOVE_DOWN", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_LEFT") {
                socket.send(new VehicleState(--x, y, "MOVE_LEFT", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_RIGHT") {
                socket.send(new VehicleState(++x, y, "MOVE_RIGHT", this.fieldId, this.uuid as string));
            }
        };
        this.timerToken = setInterval(activity, globalVehiclesSettings.defaultVelocity);
    }

    static toCoordinates(state: VehicleState): any {
        return Object.assign({ posX: state.posX, posY: state.posY }, Vehicle.COORDINATES.get(state.transition));
    }
}
