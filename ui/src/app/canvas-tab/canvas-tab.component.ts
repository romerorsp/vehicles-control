import { VehiclesCanvasService } from 'app/services/vehicles-canvas.service';
import { VehiclesService } from 'app/services/vehicles.service';
import { SocketService } from 'app/services/socket.service'
import { Field } from 'app/field';
import { Vehicle } from 'app/vehicle'
import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { globalVehiclesSettings } from "app/global-vehicles-setting";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'canvas-tab',
  templateUrl: './canvas-tab.component.html',
  styleUrls: ['./canvas-tab.component.css']
})
export class CanvasTabComponent implements OnInit, AfterViewInit {

  @Input() field: Field;
  @ViewChild('vehicleCanvas') vehicleCanvas: ElementRef;
  private spriteSheet: HTMLImageElement;
  private canvas: CanvasRenderingContext2D;
  private tbDraw: Array<any> = new Array<any>();

  constructor(private vehicleCanvasService: VehiclesCanvasService,
              private vehiclesService: VehiclesService,
              private socket: SocketService) { }

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.canvas = this.vehicleCanvas.nativeElement.getContext('2d');
    this.spriteSheet = new Image();
    this.spriteSheet.src = globalVehiclesSettings.spriteImageSource;
    this.canvas.fillStyle = globalVehiclesSettings.fillRectColor;
    this.canvas.fillRect(0, 0, this.field.width, this.field.height);

    this.tick();
  }

  newVehicle(event: any) {
    console.log(event);
    this.vehiclesService.addVehicle(new Vehicle(event.clientX, event.clientY,
                                                (coordinate) => this.tbDraw.push(coordinate),
                                                this.socket.createVehicleSocket(this.field, event.clientX, event.clientY)))
  }

  tick(): void {
    requestAnimationFrame(() => {
      this.tick();
    });
    let item;
    while((item = this.tbDraw.pop()) != null) {
      this.canvas.drawImage(this.spriteSheet,
                            item.left,
                            item.top,
                            item.width,
                            item.height,
                            item.posX,
                            item.posY,
                            globalVehiclesSettings.vehiclesDefaultWidth,
                            globalVehiclesSettings.vehiclesDefaultHeight);
    }
  }

  // tick(): void {
  //   requestAnimationFrame(() => {
  //     this.tick();
  //   });
  //   this.canvas.drawImage(this.spriteSheet, 0, 0, 45, 45, 0, 0, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 47, 0, 45, 45, 50, 0, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 94, 0, 45, 45, 100, 0, 50, 50);

  //   this.canvas.drawImage(this.spriteSheet, 0, 50, 45, 45, 0, 50, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 47, 50, 45, 45, 50, 50, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 94, 50, 45, 45, 100, 50, 50, 50);

  //   this.canvas.drawImage(this.spriteSheet, 0, 98, 45, 45, 0, 100, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 47, 98, 45, 45, 50, 100, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 94, 98, 45, 45, 100, 100, 50, 50);


  //   // this.canvas.drawImage(this.spriteSheet, 144, 0, 45, 45, 0, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 191, 0, 45, 45, 150, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 238, 0, 45, 45, 300, 0, 150, 150);

  //   // this.canvas.drawImage(this.spriteSheet, 288, 0, 45, 45, 0, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 335, 0, 45, 45, 150, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 382, 0, 45, 45, 300, 0, 150, 150);

  //   // this.canvas.drawImage(this.spriteSheet, 432, 0, 45, 45, 0, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 479, 0, 45, 45, 150, 0, 150, 150);
  //   // this.canvas.drawImage(this.spriteSheet, 526, 0, 45, 45, 300, 0, 150, 150);


  //   this.canvas.drawImage(this.spriteSheet, 0, 144, 45, 45, 0, 150, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 47, 144, 45, 45, 50, 150, 50, 50);
  //   this.canvas.drawImage(this.spriteSheet, 94, 144, 45, 45, 100, 150, 50, 50);


  //   // this.canvas.drawImage(this.spriteSheet, 46, 0, 45, 45, 46, 0, 45, 45);
  //   // this.canvas.drawImage(this.spriteSheet, 92, 0, 45, 45, 92, 0, 45, 45);

  //   // this.canvas.drawImage(this.spriteSheet, 138, 0, 45, 45, 138, 0, 45, 45);
  //   // this.canvas.drawImage(this.spriteSheet, 185, 0, 45, 45, 185, 0, 45, 45);
  //   // this.canvas.drawImage(this.spriteSheet, 231, 0, 45, 45, 231, 0, 45, 45);

  //   // this.canvas.drawImage(this.spriteSheet, 35, 0, 40, 40, 40, 0, 40, 40);
  //   // this.canvas.drawImage(this.spriteSheet, 70, 0, 40, 40, 80, 0, 40, 40);
  // }
}
