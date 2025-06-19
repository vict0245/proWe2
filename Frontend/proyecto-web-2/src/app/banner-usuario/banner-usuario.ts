import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-banner-usuario',
  imports: [RouterOutlet],
  templateUrl: './banner-usuario.html',
  styleUrl: './banner-usuario.css'
})
export class BannerUsuarioComponent {
  constructor(private router: Router) {}

  cerrarSesion() {
    this.router.navigate(['/login']);
  }
}
