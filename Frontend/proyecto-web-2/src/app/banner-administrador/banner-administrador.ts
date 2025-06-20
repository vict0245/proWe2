import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { NavAdminComponent } from '../nav-admin/nav-admin';
import { TransDatosService } from '../servicio/trans-datos';
import { AlquileresComponent } from "../alquileres/alquileres";
import { OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { VehiculoComponente } from '../vehiculo-componente/vehiculo-componente';

@Component({
  standalone: true,
  selector: 'app-banner-administrador',
  imports: [RouterOutlet, NavAdminComponent, AlquileresComponent, VehiculoComponente],
  templateUrl: './banner-administrador.html',
  styleUrl: './banner-administrador.css'
})
export class BannerAdministrador implements OnInit {
  Usuario: string = '';
  constructor(private router:Router, private transfer:TransDatosService) {}

  @ViewChild(AlquileresComponent) alqui!: AlquileresComponent;
  @ViewChild(VehiculoComponente) vehi!: VehiculoComponente;

    ngOnInit(): void {
    this.info();
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
        console.log('Datos recibidos:', data);
        this.Usuario = data || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }

  mostrarDivAlquiler(){
    console.log("Abriendo div de vehículos alquilados");
    const modal = document.getElementById("modallAlqui");
    if (modal) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      // Añade una comprobación de seguridad, aunque en ngAfterViewInit debería estar siempre definido
      if (this.alqui) {
        this.alqui.verVehiculosAlquilados();
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
    }
  }

  mostrarDivDisponible(){
    console.log("Abriendo div de vehículos disponibles");
    const modal = document.getElementById("modallAlqui"); // Asume que es el mismo modal o asegúrate de que tienes otro ID para el modal de disponibles.
    if (modal) {
      this.cerrarModalvehiculos();
      modal.style.display = "block";
      // Añade una comprobación de seguridad
      if (this.alqui) {
        this.alqui.verVehiculosDisponibles(); // Asegúrate de que AlquileresComponent tenga este método
      } else {
        console.error("Error: 'alqui' (AlquileresComponent) no está definido.");
      }
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
    console.log("Abriendo div de vehículos");
    const modal = document.getElementById("modallVehi");
    if (modal) {
      this.cerrarModalAlquilados();
      modal.style.display = "block";
    }
  }

}