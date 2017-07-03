import { VehiclesCanvasService } from './../vehicles-canvas.service';
import { Field } from './../field';
import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'canvas-tab',
  templateUrl: './canvas-tab.component.html',
  styleUrls: ['./canvas-tab.component.css']
})
export class CanvasTabComponent implements OnInit, AfterViewInit {

  @Input() field: Field;
  @ViewChild('vehicleCanvas') vehicleCanvas: ElementRef;

  spriteSheet: HTMLImageElement;
  canvas: CanvasRenderingContext2D;
  spriteHeight = 640;
  spriteWidth= 600;

  constructor(private vehicleCanvasService: VehiclesCanvasService) { }

  ngOnInit() {}

  ngAfterViewInit(): void {
    this.canvas = this.vehicleCanvas.nativeElement.getContext('2d');
    this.spriteSheet = new Image();
    this.spriteSheet.src = 'assets/img/vehicles-sprite.png';
    this.canvas.fillStyle = '#000000';
    this.canvas.fillRect(0, 0, 1600, 900);

    this.tick();
  }

  tick(): void {
    requestAnimationFrame(() => {
      this.tick();
    });
    this.canvas.drawImage(this.spriteSheet, 0, 0, 45, 45, 0, 0, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 47, 0, 45, 45, 50, 0, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 94, 0, 45, 45, 100, 0, 50, 50);

    this.canvas.drawImage(this.spriteSheet, 0, 50, 45, 45, 0, 50, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 47, 50, 45, 45, 50, 50, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 94, 50, 45, 45, 100, 50, 50, 50);

    this.canvas.drawImage(this.spriteSheet, 0, 98, 45, 45, 0, 100, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 47, 98, 45, 45, 50, 100, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 94, 98, 45, 45, 100, 100, 50, 50);


    // this.canvas.drawImage(this.spriteSheet, 144, 0, 45, 45, 0, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 191, 0, 45, 45, 150, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 238, 0, 45, 45, 300, 0, 150, 150);

    // this.canvas.drawImage(this.spriteSheet, 288, 0, 45, 45, 0, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 335, 0, 45, 45, 150, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 382, 0, 45, 45, 300, 0, 150, 150);

    // this.canvas.drawImage(this.spriteSheet, 432, 0, 45, 45, 0, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 479, 0, 45, 45, 150, 0, 150, 150);
    // this.canvas.drawImage(this.spriteSheet, 526, 0, 45, 45, 300, 0, 150, 150);


    this.canvas.drawImage(this.spriteSheet, 0, 144, 45, 45, 0, 150, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 47, 144, 45, 45, 50, 150, 50, 50);
    this.canvas.drawImage(this.spriteSheet, 94, 144, 45, 45, 100, 150, 50, 50);


    // this.canvas.drawImage(this.spriteSheet, 46, 0, 45, 45, 46, 0, 45, 45);
    // this.canvas.drawImage(this.spriteSheet, 92, 0, 45, 45, 92, 0, 45, 45);

    // this.canvas.drawImage(this.spriteSheet, 138, 0, 45, 45, 138, 0, 45, 45);
    // this.canvas.drawImage(this.spriteSheet, 185, 0, 45, 45, 185, 0, 45, 45);
    // this.canvas.drawImage(this.spriteSheet, 231, 0, 45, 45, 231, 0, 45, 45);

    // this.canvas.drawImage(this.spriteSheet, 35, 0, 40, 40, 40, 0, 40, 40);
    // this.canvas.drawImage(this.spriteSheet, 70, 0, 40, 40, 80, 0, 40, 40);
  }
}
