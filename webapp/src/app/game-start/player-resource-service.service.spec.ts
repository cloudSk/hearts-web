import { TestBed } from '@angular/core/testing';

import { PlayerResourceService } from './player-resource.service';

describe('PlayerResourceServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PlayerResourceService = TestBed.get(PlayerResourceService);
    expect(service).toBeTruthy();
  });
});
