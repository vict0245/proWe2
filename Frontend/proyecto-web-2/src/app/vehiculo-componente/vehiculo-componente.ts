import { Component, OnInit } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { VehiculosServicio } from '../servicio/vehiculos';
import { FormsModule } from '@angular/forms';
import { Alquileres } from '../entidades/alquileres';
import { Gestionalquiler } from '../entidades/gestionalquiler';

@Component({
  selector: 'app-vehiculo-componente',
  imports: [FormsModule],
  templateUrl: './vehiculo-componente.html',
  styleUrl: './vehiculo-componente.css'
})
export class VehiculoComponente implements OnInit {
  idAlquiler: number = 0;
  idAdministrador: number = 0;
  showAdditionalButtons: boolean = false;
  alquilersIns: Alquileres = new Alquileres();
  gestionIns: Gestionalquiler = new Gestionalquiler();
  vehiculoV: Vehiculos = new Vehiculos();

  constructor(private servicio: VehiculosServicio) {}

  ngOnInit(): void {
    
  }

  abrirRegistroVehiculo() {
    const modal = document.getElementById("registro");
    if (modal != null)
       modal.style.display = 'block';
  }

  cerrarRegistro() {
    const modal = document.getElementById("registro");
    if (modal != null) 
      modal.style.display = 'none';
  }

  guardarVehiculo() {
    this.servicio.registrarVehiculo(this.vehiculoV).subscribe(dato => {
      console.log(dato);
      if (dato != null) {
        alert("Vehiculo Registrado");
        this.cerrarRegistro();
        this.ngOnInit(); 
      } else {
        alert("Registro no guardado");
      }
    });
  }
gestionentrega(){

}
gestiondevolucion(){

}
abrirgestion(){
  const modal = document.getElementById("gestionformulario");
    if (modal != null)
       modal.style.display = 'block';
}
guardarGestion(){
    this.servicio.registrarGestion(this.idAlquiler, this.idAdministrador).subscribe(dato => {
      if(dato!=null){
        alert("Vehiculo Entregado Correctamente");
        this.cerrarRegistro();
        this.ngOnInit();
      }else{
        alert("Error al entregar el vehiculo")
      }
    })
}
}
