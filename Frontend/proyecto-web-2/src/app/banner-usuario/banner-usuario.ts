import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavUsuarioComponent } from '../nav-usuario/nav-usuario';
import { TransDatosService } from '../servicio/trans-datos';
import { AlquileresComponent } from '../alquileres/alquileres';
import { ViewChild } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-banner-usuario',
  imports: [RouterOutlet,NavUsuarioComponent,AlquileresComponent],
  templateUrl: './banner-usuario.html',
  styleUrl: './banner-usuario.css'
})
export class BannerUsuarioComponent {
  identificacion: string = '';
  constructor(private router: Router, private transfer:TransDatosService) {}
  
  @ViewChild(AlquileresComponent) alqui!: AlquileresComponent;

  ngOnInit(): void {
    this.info();
  }

  ngAfterViewInit() {
    this.mostrarDivAlquiler();
  }

  info(){
    this.transfer.datos$.subscribe(data => {
      if(data!=null){
        this.identificacion = data || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }

  mostrarDivAlquiler(){
    const modal = document.getElementById("modallAlqui");
    if (modal) {
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verVehiculosDisponibles();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }
}
