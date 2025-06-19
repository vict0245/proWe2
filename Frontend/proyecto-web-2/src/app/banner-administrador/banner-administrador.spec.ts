import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerAdministrador } from './banner-administrador';

describe('BannerAdministrador', () => {
  let component: BannerAdministrador;
  let fixture: ComponentFixture<BannerAdministrador>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BannerAdministrador]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BannerAdministrador);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
