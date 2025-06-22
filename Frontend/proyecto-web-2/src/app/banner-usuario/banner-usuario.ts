import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavUsuarioComponent } from '../nav-usuario/nav-usuario';
import { TransDatosService } from '../servicio/trans-datos';
import { AlquileresComponent } from '../alquileres/alquileres';
import { ViewChild } from '@angular/core';
import { VehiculoComponente } from '../vehiculo-componente/vehiculo-componente';

@Component({
  standalone: true,
  selector: 'app-banner-usuario',
  imports: [RouterOutlet,NavUsuarioComponent,AlquileresComponent, VehiculoComponente],
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

    mostrarDivAlquilers(){
    const modal = document.getElementById("modallAlqui");
    if (modal) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verAlquilado();
        this.alqui.verVehiculosAlquilados();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

    cerrarModalvehiculos() {
    const modal = document.getElementById("modallVehi");
    if (modal) {
      modal.style.display = "none";
    }
  }

  
}
