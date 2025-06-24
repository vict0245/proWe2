import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavAdminComponent } from '../nav-admin/nav-admin';
import { TransDatosService } from '../servicio/trans-datos';
import { AlquileresComponent } from "../alquileres/alquileres";
import { OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { VehiculoComponente } from '../vehiculo-componente/vehiculo-componente';
<<<<<<< HEAD
import { AlquileresServicio } from '../servicio/alquileres';
=======
import { ComunicacionService } from '../entidades/comunicacion-service';
>>>>>>> fccd9131ffed2e3465032b8e89f818d483c18417

@Component({
  standalone: true,
  selector: 'app-banner-administrador',
  imports: [RouterOutlet, NavAdminComponent, AlquileresComponent, VehiculoComponente],
  templateUrl: './banner-administrador.html',
  styleUrl: './banner-administrador.css'
})
export class BannerAdministrador implements OnInit {
  Usuario: string = '';
<<<<<<< HEAD
  vehiculosPendientes: any[] = [];
  constructor(private alquilerservicio: AlquileresServicio, private router:Router, private transfer:TransDatosService) {}
=======
  constructor(private router:Router, private transfer:TransDatosService,private comuni:ComunicacionService) {}
>>>>>>> fccd9131ffed2e3465032b8e89f818d483c18417

  @ViewChild(AlquileresComponent) alqui!: AlquileresComponent;
  @ViewChild(VehiculoComponente) vehi!: VehiculoComponente;

    ngOnInit(): void {
    this.info();

  }
  abrirModalAlquiladosYListar() {
  this.mostrarDivAlquiler();

}

  ngAfterViewInit() {
    this.mostrarDivAlquiler();
  }

  cerrarSesion() {
    this.router.navigate(['/login']);
  }

  info(){
    this.transfer.datos$.subscribe(data => {
      if(data!=null){
        this.Usuario = data || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }

  mostrarDivAlquiler(){
    const modal = document.getElementById("modallAlqui");
    const divAd = document.getElementById('bannerAd');
    if (modal && divAd) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      divAd.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verAlquilado();
        this.alqui.verVehiculosAlquilados();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

  mostrarDivDisponible(){
    const divAd = document.getElementById('bannerAd');
    const modal = document.getElementById("modallAlqui"); // Asume que es el mismo modal o asegúrate de que tienes otro ID para el modal de disponibles.
    if (modal && divAd) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      divAd.style.display = "none";
      // Añade una comprobación de seguridad
      if (this.alqui) {
        this.alqui.verAlquiler();
        this.alqui.verVehiculosDisponibles(); // Asegúrate de que AlquileresComponent tenga este método
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

  mostrarDivPendientes(){
    const modal = document.getElementById("modallAlqui");
    if (modal) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verListaP();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

  cerrarModalPendientes() {
    const modal = document.getElementById("modallPendientes");
    if (modal) {
      modal.style.display = "none";
    }
  }

  cerrarModalAlquilados() {
    const modal = document.getElementById("modallAlqui");
    if (modal) {
      modal.style.display = "none";
    }
  }

  cerrarModalvehiculos() {
    const modal = document.getElementById("modallVehi");
    if (modal) {
      modal.style.display = "none";
    }
  }

  mostrarDivVehiculo(){
    const modal = document.getElementById("modallVehi");
    if (modal) {
      this.cerrarModalAlquilados();
      modal.style.display = "block";
    }
  }

    mostrarDivPendientes(){
    const modal = document.getElementById("modallPendientes");
    if (modal) {
      this.cerrarModalPendientes();
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verListaP();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

  cerrarModalPendientes() {
    const modal = document.getElementById("modallPendientes");
    if (modal) {
      modal.style.display = "none";
    }
  }



}