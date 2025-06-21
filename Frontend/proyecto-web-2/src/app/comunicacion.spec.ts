import { TestBed } from '@angular/core/testing';

import { Comunicacion } from './comunicacion';

describe('Comunicacion', () => {
  let service: Comunicacion;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Comunicacion);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
