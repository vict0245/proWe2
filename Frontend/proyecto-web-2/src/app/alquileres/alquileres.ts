import { Vehiculos } from './../entidades/vehiculos';
import { LocalDate } from '@js-joda/core';
import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AlquileresServicio } from '../servicio/alquileres';
import { FormsModule } from '@angular/forms';
import { VehiculosServicio } from '../servicio/vehiculos';
import { ComunicacionService } from '../entidades/comunicacion-service';
import { Alquiler } from '../lib/pdf';
import Swal from 'sweetalert2';
import {generateAlquilerPDF} from '../lib/pdf';
import { TransDatosService } from '../servicio/trans-datos';
import { Alquileres } from '../entidades/alquileres';

type gdrReserva = {
  vehi: Vehiculos;
  fechaInicio: Date;
  fechaFin: Date;
  valorTotal: number;
  identificacion: String;
};

@Component({
  standalone: true,
  selector: 'app-alquileres',
  imports: [CommonModule, FormsModule],
  templateUrl: './alquileres.html',
  styleUrl: './alquileres.css',
})

export class AlquileresComponent implements OnInit {
  valorT: number = 0;
  objecto: Alquiler = new Alquiler();
  urlImagen: string | null = null;
  vehiculoSeleccionado: any = null;
  respuestaDisponibilidad: string = '';
  disponible: boolean | null = null;
  valorCalculado: String = '';
  alquileres: any = {};
  alquiler: any[] = [];
  vehiculos: any[] = [];
  vehiculosF: any[] = [];
  mostrarDisponibles: boolean = false;
  mostrarAlquilados: boolean = false;
  tipoV: String = 'todos';
  tabla: String = 'Alquilados';
  identificacion = '';
  Inicio: Date;
  Fin: Date;
  idAlqui:any;
  actuaModal=false;

  ngOnInit(): void {
    this.info();

    this.comunicacion.tipoFiltro$.subscribe((tipo) => {
      this.tipo(tipo);
    });

    this.comunicacion.Mostrar$.subscribe(() => {
      this.verAlquiler();
    });

    this.comunicacion.mostrarA$.subscribe(() => {
      this.verAlquilado();
    });
  }

  constructor(
    private alquilerservicio: AlquileresServicio,
    private vehiculosServicio: VehiculosServicio,
    private comunicacion: ComunicacionService,
    private cdRef: ChangeDetectorRef,
    private transfer: TransDatosService
  ) {}

  info() {
    this.transfer.datos$.subscribe((data) => {
      if (data != null) {
        this.identificacion = data || '';
        console.log(data);
      } else {
        console.warn('no se recibieron datos');
      }
    });
  }

  verAlquiler() {
    this.tipoV = 'todos';
    this.alquilerservicio.ObtenerListaVehiculos().subscribe((dato: any[]) => {
      this.vehiculos = dato.map((item) => ({
        idVehiculo: item[0],
        marca: item[5],
        modelo: item[1],
        tipo: item[2],
        valorAlquilerDia: item[3],
        img: item[4],
      }));
      this.mostrarDisponibles = true;
      this.mostrarAlquilados = false;
      this.vehiculosF = this.vehiculos;
    });
  }

  verAlquilado() {
    this.tabla = 'Alquilados';
    this.alquilerservicio.obtenerListaAlquilados().subscribe((dato) => {
      this.alquiler = dato.map((item: any[]) => ({
        idAlquiler: item[0],
        placa: item[1],
        marca: item[2],
        modelo: item[3],
        id_cliente: item[4],
        fecha_inicio: item[5],
        fecha_fin: item[6],
        estado: item[7],
        img: item[8],
      }));
      this.mostrarDisponibles = false;
      this.mostrarAlquilados = true;
    });
  }

  verAlquiladoU(identificacion: number) {
    this.alquilerservicio
      .obtenerListaAlquiladosU(identificacion)
      .subscribe((dato) => {
        this.alquiler = dato.map((item: any[]) => ({
          idAlquiler: item[0],
          placa: item[1],
          marca: item[2],
          modelo: item[3],
          id_cliente: item[4],
          fecha_inicio: item[5],
          fecha_fin: item[6],
          estado: item[7],
          img: item[8],
        }));
        this.mostrarDisponibles = false;
        this.mostrarAlquilados = true;
      });

  }

  abrir(item: any) {
    this.vehiculoSeleccionado = item;
    document.getElementById('actualizar')!.style.display = 'block';
    this.valorCalculado = '';
    this.mostrarResumen = false;
  }

  cerrar() {
    this.mostrarResumen=false;
    document.getElementById('actualizar')!.style.display = 'none';
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
      alert('Debes ingresar ambas fechas.');
      return;
    }
    this.alquilerservicio
      .verificarDisponibilidad(v, inicio, fin)
      .subscribe((dato) => {
        if (dato != false) {
          this.respuestaDisponibilidad = '✅ El vehículo está disponible.';
          this.disponible = true;
        } else {
          this.respuestaDisponibilidad = '❌ El vehículo no está disponible.';
          this.disponible = false;
        }
      });
  }
  mostrarResumen: boolean = false;

  calcularTotal() {
    const inicio = this.alquileres.fechaInicio;
    const fin = this.alquileres.fechaFin;
    const id = this.vehiculoSeleccionado.idVehiculo;

    this.Inicio = inicio;
    this.Fin = fin;

    if (!inicio || !fin || !id) {
      this.valorCalculado = 'Debes seleccionar el vehículo y las fechas.';
      this.mostrarResumen = false;
      return;
    }

    this.alquilerservicio.calcularValorTotal(id, inicio, fin).subscribe({
      next: (res) => {
        this.valorCalculado = res;
        this.mostrarResumen = true;
        this.valorT = res;
      },
      error: (err) => {
        console.error('Error:', err);
        this.valorCalculado = 'Error al calcular el valor total.';
        this.mostrarResumen = false;
      },
    });
  }

  confirmarReserva() {
    // Asegúrate que las fechas son objetos Date válidos
    const inicio = new Date(this.Inicio);
    const fin = new Date(this.Fin);
    
    // Validación de fechas
    if (isNaN(inicio.getTime())) {
        alert('Fecha de inicio no válida');
        return;
    }
    if (isNaN(fin.getTime())) {
        alert('Fecha fin no válida');
        return;
    }

    const reservaData:gdrReserva = {
        vehi: this.vehiculoSeleccionado,
        fechaInicio: inicio,
        fechaFin: fin,
        valorTotal: this.valorT,
        identificacion: this.identificacion
    };

    console.log('Datos a enviar:', reservaData);
    
    this.alquilerservicio.guardarReserva(reservaData).subscribe({
        next: (dato) => {
            if (dato) {
                alert('Reserva Guardada');
            } else {
                alert('No se pudo guardar');
            }
        },
        error: (err) => {
            console.error('Error al guardar reserva:', err);
            alert('Error al procesar la reserva');
        }
    });
}

  async traerAlqui() {
    try {
        const reserva = [
            this.Inicio,
            this.Fin,
            'Pendiente',
            this.vehiculoSeleccionado.idVehiculo,
            this.identificacion
        ];

        // Convertir a Promise para poder usar await
        const dato = await this.alquilerservicio.obtenerAlquiler(reserva).toPromise();
        
        if (!dato) {
            throw new Error('No se recibieron datos del servidor');
        }
        const fechaEmision = new Date().toISOString().split('T')[0];
        console.log('Datos para PDF:', dato, fechaEmision);
        dato[0].push(this.valorT);
        // Generar PDF solo si tenemos datos válidos
        if (dato) {
            generateAlquilerPDF(dato, fechaEmision);
        } else {
            console.error('Datos incompletos para generar PDF');
        }
    } catch (error) {
        console.error('Error en traerAlqui:', error);
        alert('Error al obtener datos para el PDF');
    }
}

  verVehiculosAlquilados() {
    this.mostrarDisponibles = false;
    this.mostrarAlquilados = true;
    this.cdRef.detectChanges();
  }

  verVehiculosDisponibles() {
    this.mostrarDisponibles = true;
    this.mostrarAlquilados = false;
    this.cdRef.detectChanges();
  }

  tipo(tipo: string) {
    this.tipoV = tipo;
    this.vehiculosServicio.tipoVehiculo(tipo).subscribe((dato) => {
      console.log('Respuesta del backend:', dato);
      this.vehiculosF = dato;
    });
    this.verVehiculosDisponibles();
  }

  tablaM(tabla: string) {
    this.tabla = tabla;
  }

  async cancelar(id: number) {
    const confirm = await Swal.fire({
        title: '¿Estás seguro?',
        text: `¿Deseas cancelar el alquiler con ID ${id}?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, cancelar',
        cancelButtonText: 'No'
    });

    if (!confirm.isConfirmed) {
        console.log('Cancelación abortada');
        return;
    }

    try {
        const res = await this.alquilerservicio.cancelarAlquiler(id).toPromise();
        await Swal.fire('¡Cancelado!', res.message || 'Operación exitosa', 'success');
        this.verAlquilado();
    } catch (error) {
        console.error('Error:', error);
        Swal.fire('Error', error + 'Error al cancelar', 'error');
    }
}

  verListaP() {
    this.tipoV = 'Pendientes';
    this.alquilerservicio.obtenerListaPendientes().subscribe((dato: any[]) => {
      this.vehiculos = dato.map((item) => {
        return {
          idVehiculo: item[0],
          marca: item[5],
          modelo: item[1],
          tipo: item[2],
          valorAlquilerDia: item[3],
          img: item[4],
        };
      });

      this.vehiculosF = this.vehiculos;
      this.mostrarDisponibles = true;
      this.mostrarAlquilados = false;
    });
  }

  convertirBlobADataURL(blob: Blob): Promise<string> {
    return new Promise((resolve) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result as string);
      reader.readAsDataURL(blob);
    });
  }
}
