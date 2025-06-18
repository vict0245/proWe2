import { TestBed } from '@angular/core/testing';

import { Alquileres } from './alquileres';

describe('Alquileres', () => {
  let service: Alquileres;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Alquileres);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
