import { TestBed } from '@angular/core/testing';

import { TransDatos } from './trans-datos';

describe('TransDatos', () => {
  let service: TransDatos;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransDatos);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
