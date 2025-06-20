import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavAdminComponent } from '../nav-admin/nav-admin';
import { TransDatosService } from '../servicio/trans-datos';

@Component({
  standalone: true,
  selector: 'app-banner-administrador',
  imports: [RouterOutlet,NavAdminComponent],
  templateUrl: './banner-administrador.html',
  styleUrl: './banner-administrador.css'
})
export class BannerAdministrador {
  email: string = '';
  constructor(private router:Router, private transfer:TransDatosService) {}

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
