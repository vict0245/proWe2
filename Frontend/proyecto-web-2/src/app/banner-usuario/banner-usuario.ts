import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavUsuarioComponent } from '../nav-usuario/nav-usuario';
import { TransDatosService } from '../servicio/trans-datos';
import { c } from 'vite/dist/node/moduleRunnerTransport.d-DJ_mE5sf';

@Component({
  standalone: true,
  selector: 'app-banner-usuario',
  imports: [RouterOutlet,NavUsuarioComponent],
  templateUrl: './banner-usuario.html',
  styleUrl: './banner-usuario.css'
})
export class BannerUsuarioComponent {
  email: string = '';
  constructor(private router: Router, private transfer:TransDatosService) {}

  ngOnInit(): void {
    this.transfer.datos$.subscribe(data => {
      if(data!=null){
        console.log('Datos recibidos:', data);
        this.email = data.email || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }

  cerrarSesion() {
    this.router.navigate(['/login']);
  }
}
