import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavUsuario } from './nav-usuario';

describe('NavUsuario', () => {
  let component: NavUsuario;
  let fixture: ComponentFixture<NavUsuario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavUsuario]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavUsuario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
