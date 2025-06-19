import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerUsuario } from './banner-usuario';

describe('BannerUsuario', () => {
  let component: BannerUsuario;
  let fixture: ComponentFixture<BannerUsuario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BannerUsuario]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BannerUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
