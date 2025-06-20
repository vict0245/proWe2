import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavUsuarioComponent } from '../nav-usuario/nav-usuario';
import { TransDatosService } from '../servicio/trans-datos';

@Component({
  standalone: true,
  selector: 'app-banner-usuario',
  imports: [RouterOutlet,NavUsuarioComponent],
  templateUrl: './banner-usuario.html',
  styleUrl: './banner-usuario.css'
})
export class BannerUsuarioComponent {
  identificacion: string = '';
  constructor(private router: Router, private transfer:TransDatosService) {}

  ngOnInit(): void {
    this.info();
  }

  cerrarSesion() {
    this.router.navigate(['/login']);
  }

  info(){
    this.transfer.datos$.subscribe(data => {
      if(data!=null){
        console.log('Datos recibidos:', data);
        this.identificacion = data || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }
}
