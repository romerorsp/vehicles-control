import { VehicleSocket } from "app/vehicle-socket";
import { globalVehiclesSettings } from "app/global-vehicles-setting";
import { UUID } from "angular2-uuid";
import { VehicleState } from "app/vehicle-state";
import { VehicleRemover } from "app/vehicle-remover";

export class Vehicle {

    public colorType: string = "selected";
    public uuid: UUID;
    public y: number;
    public x: number;
    public fieldId: string;
    private timerToken: any;
    public state: string = "CREATE";

    private static COORDINATES: Map<string, Map<string, any>> = new Map(
        [
            [
                globalVehiclesSettings.unselectedColorType,
                new Map([
                    ["CREATE", globalVehiclesSettings.unselected.downCarCoordinates],
                    ["PAUSE", globalVehiclesSettings.unselected.downCarCoordinates],
                    ["FINISH", globalVehiclesSettings.unselected.downCarCoordinates],
                    ["MOVE_DOWN", globalVehiclesSettings.unselected.downCarCoordinates],
                    ["MOVE_UP", globalVehiclesSettings.unselected.upCarCoordinates],
                    ["MOVE_LEFT", globalVehiclesSettings.unselected.leftCarCoordinates],
                    ["MOVE_RIGHT", globalVehiclesSettings.unselected.rightCarCoordinates],
                ])

            ],
            [
                globalVehiclesSettings.selectedColorType,
                new Map([
                    ["CREATE", globalVehiclesSettings.selected.downCarCoordinates],
                    ["PAUSE", globalVehiclesSettings.selected.downCarCoordinates],
                    ["FINISH", globalVehiclesSettings.selected.downCarCoordinates],
                    ["MOVE_DOWN", globalVehiclesSettings.selected.downCarCoordinates],
                    ["MOVE_UP", globalVehiclesSettings.selected.upCarCoordinates],
                    ["MOVE_LEFT", globalVehiclesSettings.selected.leftCarCoordinates],
                    ["MOVE_RIGHT", globalVehiclesSettings.selected.rightCarCoordinates],
                ])
            ]
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
            
            if (this.state == null) {
                socket.send(new VehicleState(this.x, this.y, "CREATE", this.fieldId, this.uuid as string));
            } else if (this.state === "END" || this.state === "FINISH") {
                this.socket.close();
                this.remover.removeVehicle(this);
                clearInterval(this.timerToken);
            } else if (this.state === "MOVE_UP") {
                socket.send(new VehicleState(this.x, --this.y, "MOVE_UP", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_DOWN") {
                socket.send(new VehicleState(this.x, ++this.y, "MOVE_DOWN", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_LEFT") {
                socket.send(new VehicleState(--this.x, this.y, "MOVE_LEFT", this.fieldId, this.uuid as string));
            } else if (this.state === "MOVE_RIGHT") {
                socket.send(new VehicleState(++this.x, this.y, "MOVE_RIGHT", this.fieldId, this.uuid as string));
            }
        };
        this.timerToken = setInterval(activity, globalVehiclesSettings.defaultVelocity);
    }

    static toCoordinates(state: VehicleState, colorType: string): any {
        const result = Object.assign({ posX: state.posX, posY: state.posY });
        const coordinate = Vehicle.COORDINATES.get(colorType).get(state.transition);
        result.left = coordinate.left;
        result.top = coordinate.top;
        result.width = coordinate.width;
        result.height = coordinate.height;
        return result;
    }
}
