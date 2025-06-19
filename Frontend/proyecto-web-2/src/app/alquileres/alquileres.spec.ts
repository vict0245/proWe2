import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Alquileres } from './alquileres';

describe('Alquileres', () => {
  let component: Alquileres;
  let fixture: ComponentFixture<Alquileres>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Alquileres]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Alquileres);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
