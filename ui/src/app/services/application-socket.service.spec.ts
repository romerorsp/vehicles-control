import { TestBed, inject } from '@angular/core/testing';

import { ApplicationSocketService } from './application-socket.service';

describe('ApplicationSocketService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApplicationSocketService]
    });
  });

  it('should be created', inject([ApplicationSocketService], (service: ApplicationSocketService) => {
    expect(service).toBeTruthy();
  }));
});
