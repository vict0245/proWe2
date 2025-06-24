import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavUsuarioComponent } from '../nav-usuario/nav-usuario';
import { TransDatosService } from '../servicio/trans-datos';
import { AlquileresComponent } from '../alquileres/alquileres';
import { ViewChild } from '@angular/core';
import { VehiculoComponente } from '../vehiculo-componente/vehiculo-componente';
import { NavAdminComponent } from '../nav-admin/nav-admin'; 

@Component({
  standalone: true,
  selector: 'app-banner-usuario',
  imports: [RouterOutlet,NavUsuarioComponent,AlquileresComponent, VehiculoComponente,NavAdminComponent],
  templateUrl: './banner-usuario.html',
  styleUrl: './banner-usuario.css'
})
export class BannerUsuarioComponent {
  identificacion: string = '';
  constructor(private router: Router, private transfer:TransDatosService) {}
  
  @ViewChild(AlquileresComponent) alqui!: AlquileresComponent;
  @ViewChild(VehiculoComponente) vehi!: VehiculoComponente;

  ngOnInit(): void {
    this.info();
  }

  ngAfterViewInit() {
    this.mostrarDivDisponible();
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

    mostrarDivAlquilers(){
    const modal = document.getElementById("modallAlquiler");
    if (modal) {
      this.cerrarModalDisponibles();
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verVehiculosAlquilados();
        this.alqui.verAlquilado();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

    cerrarModalDisponibles() {
    const modal = document.getElementById("modallVehiculo");
    if (modal) {
      modal.style.display = "none";
    }
  }

    mostrarDivDisponible(){
    const modal = document.getElementById("modallVehiculo"); // Asume que es el mismo modal o asegúrate de que tienes otro ID para el modal de disponibles.
    if (modal) {
      this.cerrarModalDisponibles();
      modal.style.display = "block";
      // Añade una comprobación de seguridad
      if (this.alqui) {
        this.alqui.verAlquiler();
        this.alqui.verVehiculosDisponibles(); // Asegúrate de que AlquileresComponent tenga este método
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }
  
      cerrarModalAlquilados() {
    const modal = document.getElementById("modallAlquiler");
    if (modal) {
      modal.style.display = "none";
    }
  }
}
