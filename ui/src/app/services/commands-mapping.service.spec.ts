import { TestBed, inject } from '@angular/core/testing';

import { CommandsMappingService } from './commands-mapping.service';

describe('CommandsMappingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommandsMappingService]
    });
  });

  it('should be created', inject([CommandsMappingService], (service: CommandsMappingService) => {
    expect(service).toBeTruthy();
  }));
});
