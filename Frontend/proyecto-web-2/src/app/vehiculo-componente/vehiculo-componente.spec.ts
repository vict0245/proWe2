import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiculoComponente } from './vehiculo-componente';

describe('VehiculoComponente', () => {
  let component: VehiculoComponente;
  let fixture: ComponentFixture<VehiculoComponente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehiculoComponente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehiculoComponente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
