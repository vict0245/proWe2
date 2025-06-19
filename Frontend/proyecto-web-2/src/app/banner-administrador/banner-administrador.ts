import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-banner-administrador',
  imports: [RouterOutlet],
  templateUrl: './banner-administrador.html',
  styleUrl: './banner-administrador.css'
})
export class BannerAdministrador {
  constructor(private router:Router) {}

  cerrarSesion() {
    this.router.navigate(['/login']);
  }

}
