import { Component, OnInit } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { VehiculosServicio } from '../servicio/vehiculos';
import { FormsModule } from '@angular/forms';
import { Alquileres } from '../entidades/alquileres';
import { Gestionalquiler } from '../entidades/gestionalquiler';
import { Output, EventEmitter } from '@angular/core';

@Component({
  standalone: true,
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

  
  @Output() recargarAfterInitRequest = new EventEmitter<void>();

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
      if (dato != null) {
        alert("Vehiculo Registrado");
        this.cerrarRegistro();
        this.onrecargarAfterInitRequest();
        this.ngOnInit(); 
      } else {
        alert("Registro no guardado");
      }
    });
  }
gestionentrega(){

}
guardargestiondevolucion(){
  this.servicio.registrarGestionDevuelto(this.idAlquiler).subscribe(dato => {
    if(dato!= null){
      alert("vehiculo devuelto correctamente");
      this.cerrarRegistro();
      this.ngOnInit();
    }else{
      alert("Error al devolver el vehiculo")
    }
  })

}
abrirgestionentregado(){
  const modal = document.getElementById("gestionformulario");
    if (modal != null)
       modal.style.display = 'block';
}
abrirgestiondevuelto(){
  const modal = document.getElementById("gestionformulario2");
    if (modal != null)
       modal.style.display = 'block';
}
cerrarRegistrogestiondevuelto() {
    const modal = document.getElementById("gestionformulario2");
    if (modal != null) 
      modal.style.display = 'none';
  }

 cerrarRegistrogestionentregado() {
    const modal = document.getElementById("gestionformulario");
    if (modal != null) 
      modal.style.display = 'none';
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

onrecargarAfterInitRequest(){
    this.recargarAfterInitRequest.emit();
  }
}
