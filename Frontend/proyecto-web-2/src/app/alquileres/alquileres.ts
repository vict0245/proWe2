import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Alquileres } from '../entidades/alquileres';
import { Vehiculos } from '../entidades/vehiculos';
import { AlquileresServicio } from '../servicio/alquileres';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-alquileres',
  imports: [CommonModule,FormsModule],
  templateUrl: './alquileres.html',
  styleUrl: './alquileres.css'
})
export class AlquileresComponent implements OnInit {
  vehiculoSeleccionado: any = null;
 respuestaDisponibilidad: string = '';
  disponible: boolean | null = null;
  valorCalculado: String='';
   alquileres: any = {} ;
   alquiler: any[]=[];
   vehiculos: any[] = [];
    ngOnInit(): void {
      this.verAlquiler();
      this.verAlquilado();
      
  }
    constructor(private alquilerservicio: AlquileresServicio){

  }
  private verAlquiler(){
  this.alquilerservicio.ObtenerListaVehiculos().subscribe((dato: any[]) => {
    console.log(dato)
    this.vehiculos = dato.map(item => ({
    id: item[0],                    
    modelo: item[1],
    tipo: item[2],
    valorAlquilerDia: item[3]
}));

});

}

verAlquilado(){
  this.alquilerservicio.obtenerListaAlquilados().subscribe(dato => {
    this.alquiler = dato.map((item: any[]) => ({
      placa: item[0],
      marca: item[1],
      modelo: item[2],
      id_cliente: item[3],
      fecha_inicio: item[4],
      fecha_fin: item[5],
      estado: item[6]
    }));
  });
}


abrir(item: any) {
  this.vehiculoSeleccionado = item;
  const modal = document.getElementById("actualizar");
   console.log("✅ Vehículo seleccionado:", this.vehiculoSeleccionado);
  if (modal) modal.style.display = 'block';
}



cerrar() {
  const modal = document.getElementById("actualizar");
  if (modal) modal.style.display = 'none';

  // Limpia estado visual
  this.valorCalculado = '';
  this.respuestaDisponibilidad = '';
  this.disponible = null;
  this.alquileres = {};
  this.vehiculoSeleccionado = null;
}


verificar() {
  const inicio = this.alquileres.fechaInicio;
  const fin = this.alquileres.fechaFin;
  this.valorCalculado = '';



  if (!inicio || !fin) {
    alert("Debes ingresar ambas fechas.");
    return;
  }

  this.alquilerservicio.verificarDisponibilidad(inicio, fin).subscribe({
    next: (res) => {
      console.log("Respuesta del backend:", res);
      alert(res);
    },
    error: (err) => {
      console.error("Error del backend:", err);
      alert("Ocurrió un error al verificar disponibilidad.");
    }
  });
}
mostrarResumen: boolean = false;

calcularTotal() {
  const inicio = this.alquileres.fechaInicio;
  const fin = this.alquileres.fechaFin;
  const id = this.vehiculoSeleccionado?.id;

  if (!inicio || !fin || !id) {
    this.valorCalculado = "Debes seleccionar el vehículo y las fechas.";
    this.mostrarResumen = false;
    return;
  }

  this.alquilerservicio.calcularValorTotal(id, inicio, fin).subscribe({
    next: (res) => {
      this.valorCalculado = res;
      this.mostrarResumen = true;
    },
    error: (err) => {
      console.error("Error:", err);
      this.valorCalculado = "Error al calcular el valor total.";
      this.mostrarResumen = false;
    }
  });
}
confirmarReserva() {
  const inicio = this.alquileres.fechaInicio;
  const fin = this.alquileres.fechaFin;
  const vehiculoId = this.vehiculoSeleccionado?.id;

  if (!inicio || !fin || !vehiculoId) {
    alert("Debes completar todos los datos.");
    return;
  }

  const reserva = {
    fechaInicio: inicio,
    fechaFin: fin,
    estado: "Pendiente",
    vehiculo: { idVehiculo: vehiculoId },
    usuario: { idUsuario: 1 } // ⚠️ ID simulado. En un sistema real, debe ser el usuario logueado
  };

  this.alquilerservicio.guardarReserva(reserva).subscribe({
    next: (res) => {
      alert("✅ " + res);
      this.cerrar(); // Opcional: Cierra modal
    },
    error: (err) => {
      console.error("❌ Error al guardar reserva:", err);
      alert("No se pudo registrar la solicitud.");
    }
  });
}
}