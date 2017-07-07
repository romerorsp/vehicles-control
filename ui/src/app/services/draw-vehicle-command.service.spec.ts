import { TestBed, inject } from '@angular/core/testing';

import { DrawVehicleCommandService } from './draw-vehicle-command.service';

describe('DrawVehicleCommandService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DrawVehicleCommandService]
    });
  });

  it('should be created', inject([DrawVehicleCommandService], (service: DrawVehicleCommandService) => {
    expect(service).toBeTruthy();
  }));
});
