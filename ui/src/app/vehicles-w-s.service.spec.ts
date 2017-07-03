import { TestBed, inject } from '@angular/core/testing';

import { VehiclesWSService } from './vehicles-w-s.service';

describe('VehiclesWSService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VehiclesWSService]
    });
  });

  it('should be created', inject([VehiclesWSService], (service: VehiclesWSService) => {
    expect(service).toBeTruthy();
  }));
});
