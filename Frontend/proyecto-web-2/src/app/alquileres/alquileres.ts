import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AlquileresServicio } from '../servicio/alquileres';
import { FormsModule } from '@angular/forms';
import { VehiculosServicio } from '../servicio/vehiculos';
import { ComunicacionService } from '../comunicacion';
import { Alquileres } from '../entidades/alquileres';
import Swal from 'sweetalert2';


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
  vehiculosF: any[]=[];


    ngOnInit(): void {
      this.comunicacion.tipoFiltro$.subscribe((tipo) => {
      this.tipo(tipo);
    });

      this.comunicacion.Mostrar$.subscribe(() => {
      this.verAlquiler();
    });

      this.comunicacion.mostrarA$.subscribe(() =>{
        this.verAlquilado();
      });
  
    this.verAlquiler();
    this.verAlquilado();
    
      
  }
    constructor(private alquilerservicio: AlquileresServicio,private vehiculosServicio: VehiculosServicio,  private comunicacion: ComunicacionService,){

  }
  verAlquiler(){
  this.alquilerservicio.ObtenerListaVehiculos().subscribe((dato: any[]) => {
    this.vehiculos = dato.map(item => ({
    idVehiculo: item[0],                    
    modelo: item[1],
    tipo: item[2],
    valorAlquilerDia: item[3]
    }));
     this.vehiculosF = this.vehiculos;
  });

}

verAlquilado(){
  this.alquilerservicio.obtenerListaAlquilados().subscribe(dato => {
this.alquiler = dato.map((item: any[]) => ({
  idAlquiler: item[0],
  placa: item[1],
  marca: item[2],
  modelo: item[3],
  id_cliente: item[4],
  fecha_inicio: item[5],
  fecha_fin: item[6],
  estado: item[7]
}));

  });
}


abrir(item: any) {
  this.vehiculoSeleccionado = item; 
  document.getElementById('actualizar')!.style.display = 'block';
  this.valorCalculado = '';
  this.mostrarResumen = false;
}


cerrar() {
  const modal = document.getElementById("actualizar");
  if (modal) modal.style.display = 'none';

  this.valorCalculado = '';
  this.respuestaDisponibilidad = '';
  this.disponible = null;
  this.alquileres = {};
  this.vehiculoSeleccionado = null;
}


verificar() {
  const v = this.vehiculoSeleccionado.idVehiculo;
  const inicio = this.alquileres.fechaInicio;
  const fin = this.alquileres.fechaFin;
  this.valorCalculado = '';

  if (!inicio || !fin) {
    alert("Debes ingresar ambas fechas.");
    return;
  }
  this.alquilerservicio.verificarDisponibilidad(v,inicio, fin).subscribe(dato =>{
    if(dato!=false){
      this.respuestaDisponibilidad = "✅ El vehículo está disponible.";
      this.disponible = true;
    }else{
      this.respuestaDisponibilidad = "❌ El vehículo no está disponible.";
      this.disponible = false;
    }
  })
}
mostrarResumen: boolean = false;

calcularTotal() {
  const inicio = this.alquileres.fechaInicio;
  const fin = this.alquileres.fechaFin;
  const id = this.vehiculoSeleccionado.idVehiculo;

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
  const vehiculoId = this.vehiculoSeleccionado?.idVehiculo; 

  if (!inicio || !fin || !vehiculoId) {
    alert("Debes completar todos los datos.");
    return;
  }

  const reserva = {
    fechaInicio: inicio,
    fechaFin: fin,
    estado: "Pendiente",
    vehiculo: { idVehiculo: vehiculoId },
    usuario: { idUsuario: 1 } // ⚠️ Simulado, reemplazar por el ID del usuario logueado
  };

  this.alquilerservicio.guardarReserva(reserva).subscribe({
    next: (res) => {
      alert("✅ " + res);
      this.cerrar();
    },
    error: (err) => {
      console.error("❌ Error al guardar reserva:", err);
      alert("No se pudo registrar la solicitud.");
    }
  });
}


  verVehiculosAlquilados() {
    const divAlquilados = document.getElementById("VehículosAlquilados");
    const divDisponibles= document.getElementById("VehículosDisponibles");
    if (divAlquilados && divDisponibles) {
      divDisponibles.style.display = "none";
      divAlquilados.style.display = "block";
    } else {
      console.error("Modal no encontrado");
    }
  }

  verVehiculosDisponibles(){
    const divAlquilados = document.getElementById("VehículosAlquilados");
    const divDisponibles= document.getElementById("VehículosDisponibles");
    if (divAlquilados && divDisponibles) {
      divAlquilados.style.display = "none";
      divDisponibles.style.display = "block";
    } else {
      console.error("Modal no encontrado");
    }
  }



   tipo(tipo: string) {
  console.log("Tipo seleccionado:", tipo); 
  this.vehiculosServicio.tipoVehiculo(tipo).subscribe(dato => {
    console.log("Respuesta del backend:", dato);
    this.vehiculosF = dato;
  });
}
cancelar(id: number) {
  Swal.fire({
    title: '¿Estás seguro?',
    text: `¿Deseas cancelar el alquiler con ID ${id}?`,
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, cancelar',
    cancelButtonText: 'No'
  }).then((result) => {
    if (result.isConfirmed) {
      console.log("✅ ID recibido para cancelar:", id);

      this.alquilerservicio.cancelarAlquiler(id).subscribe(
        res => {
          // ✅ Como el backend devuelve solo un string, usamos res directamente
          Swal.fire('Cancelado', res, 'success');
          this.verAlquilado(); // 🔄 Refresca la tabla
        },
        err => {
          console.error("❌ Error al cancelar:", err.error || err.message);
          Swal.fire('Error', err.error || 'Error al cancelar', 'error');
        }
      );
    } else {
      console.log("❎ Cancelación abortada por el usuario.");
    }
  });
}


}