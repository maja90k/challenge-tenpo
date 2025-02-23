import { TestBed } from '@angular/core/testing';

import { SymbolService } from './auto.service';

describe('AutoService', () => {
  let service: SymbolService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SymbolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
