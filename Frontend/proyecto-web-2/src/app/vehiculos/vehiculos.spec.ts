import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Vehiculos } from './vehiculos';

describe('Vehiculos', () => {
  let component: Vehiculos;
  let fixture: ComponentFixture<Vehiculos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Vehiculos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Vehiculos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
