import { TestBed, inject } from '@angular/core/testing';

import { VehiclesCanvasService } from './vehicles-canvas.service';

describe('VehiclesCanvasServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VehiclesCanvasService]
    });
  });

  it('should be created', inject([VehiclesCanvasService], (service: VehiclesCanvasService) => {
    expect(service).toBeTruthy();
  }));
});
