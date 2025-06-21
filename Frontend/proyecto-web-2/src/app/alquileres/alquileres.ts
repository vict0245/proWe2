import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AlquileresServicio } from '../servicio/alquileres';
import { FormsModule } from '@angular/forms';
import { VehiculosServicio } from '../servicio/vehiculos';
import { ComunicacionService } from '../comunicacion';


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
  
    this.verAlquiler();
    this.verAlquilado();
    
      
  }
    constructor(private alquilerservicio: AlquileresServicio,private vehiculosServicio: VehiculosServicio,  private comunicacion: ComunicacionService,){

  }
  verAlquiler(){
  this.alquilerservicio.ObtenerListaVehiculos().subscribe((dato: any[]) => {
    this.vehiculos = dato.map(item => ({
    id: item[0],                    
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
  if (modal!=null) modal.style.display = 'block';
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
  const v = this.vehiculoSeleccionado.id;
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
  const id = this.vehiculoSeleccionado.id;

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

}