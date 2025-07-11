import { Component, OnInit } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { VehiculosServicio } from '../servicio/vehiculos';
import { FormsModule } from '@angular/forms';
import { Alquileres } from '../entidades/alquileres';
import { Gestionalquiler } from '../entidades/gestionalquiler';
import { Output, EventEmitter } from '@angular/core';
import { TransDatosService } from '../servicio/trans-datos';

@Component({
  standalone: true,
  selector: 'app-vehiculo-componente',
  imports: [FormsModule],
  templateUrl: './vehiculo-componente.html',
  styleUrl: './vehiculo-componente.css',
})
export class VehiculoComponente implements OnInit {
  archivo: File;
  idAlquiler: number = 0;
  idAdministrador: number = 0;
  showAdditionalButtons: boolean = false;
  alquilersIns: Alquileres = new Alquileres();
  gestionIns: Gestionalquiler = new Gestionalquiler();
  vehiculoV: Vehiculos = new Vehiculos();
  placaVehiculo: string = '';

  @Output() recargarAfterInitRequest = new EventEmitter<void>();

  constructor(private servicio: VehiculosServicio, private transfer:TransDatosService) {}

  ngOnInit(): void {
  }

  abrirRegistroVehiculo() {
    const modal = document.getElementById('registro');
    if (modal != null) modal.style.display = 'block';
  }

  cerrarRegistro() {
    const modal = document.getElementById('registro');
    if (modal != null) modal.style.display = 'none';
  }

  guardarVehiculo() {
    const formData = new FormData();
    this.vehiculoV.img=this.archivo;
    formData.append('placa', this.vehiculoV.placa);
    formData.append('marca', this.vehiculoV.marca);
    formData.append('modelo', this.vehiculoV.modelo);
    formData.append('color', this.vehiculoV.color);
    formData.append('estado', this.vehiculoV.estado);
    formData.append(
      'valorAlquilerDia',
      this.vehiculoV.valorAlquilerDia.toString()
    );
    formData.append('tipo', this.vehiculoV.tipo);
    formData.append('img', this.vehiculoV.img);
    this.servicio.registrarVehiculo(formData).subscribe((dato) => {
      if (dato != null) {
        alert('Vehiculo Registrado');
        this.cerrarRegistro();
        this.onrecargarAfterInitRequest();
        this.ngOnInit();
      } else {
        alert('Registro no guardado');
      }
    });
  }
  gestionentrega() {}

  guardargestiondevolucion() {
    if (!this.placaVehiculo) {
      alert('Por favor ingrese la placa del vehículo');
      return;
    }
    console.log(this.placaVehiculo)

    this.servicio
      .registrarGestionDevuelto(this.placaVehiculo.toString())
      .subscribe({
        next: (response: any) => {
          alert(response.mensaje || 'Vehículo devuelto correctamente');
          this.cerrarRegistro();
          this.ngOnInit();
        },
        error: (error) => {
          console.error('Error:', error);
          alert(error.error?.error || 'Error al procesar la devolución');
        },
      });
  }

  abrirgestionentregado() {
    const modal = document.getElementById('gestionformulario');
    if (modal != null) modal.style.display = 'block';
  }
  abrirgestiondevuelto() {
    const modal = document.getElementById('gestionformulario2');
    if (modal != null) modal.style.display = 'block';
  }
  cerrarRegistrogestiondevuelto() {
    const modal = document.getElementById('gestionformulario2');
    if (modal != null) modal.style.display = 'none';
  }

  cerrarRegistrogestionentregado() {
    const modal = document.getElementById('gestionformulario');
    if (modal != null) modal.style.display = 'none';
  }

  guardarGestion() {
    if (!this.placaVehiculo) {
      alert('Por favor ingrese la placa del vehículo');
      return;
    }

    this.servicio.registrarGestion(this.placaVehiculo).subscribe({
      next: (response: any) => {
        alert(response.mensaje || 'Vehículo entregado correctamente');
        this.cerrarRegistro();
        this.ngOnInit();
      },
      error: (error) => {
        console.error('Error:', error);
        alert(error.error?.error || 'Error al procesar la entrega');
      },
    });
  }

  onrecargarAfterInitRequest() {
    this.recargarAfterInitRequest.emit();
  }

  cargarImagen(event: any) {
    this.archivo = event.target.files[0];
    console.log('Archivo cargado:', this.archivo); // <-- MUY IMPORTANTE
  }
}
