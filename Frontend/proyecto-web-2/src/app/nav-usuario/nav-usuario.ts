import { Component, EventEmitter, Output } from '@angular/core';
import { VehiculosServicio } from './../servicio/vehiculos';
import { ComunicacionService } from '../entidades/comunicacion-service';



@Component({
  standalone: true,
  selector: 'app-nav-usuario',
  imports: [],
  templateUrl: './nav-usuario.html',
  styleUrl: './nav-usuario.css'
})
export class NavUsuarioComponent {
  constructor(private comunicacion: ComunicacionService) {}

  Vehiculos: VehiculosServicio;


  @Output() reloandAfterInit = new EventEmitter<void>();
  @Output() abrirModalAlquilados = new EventEmitter<void>();

  onReloandAfterinit(){
    this.reloandAfterInit.emit();
  }

  filtrar(tipo: string) {
    this.comunicacion.emitirTipo(tipo);
  }

  mostrar(){
    this.comunicacion.mostrarTodo();
  }

  onAbrirModalAlquiladosClick(){
    this.abrirModalAlquilados.emit();
  }

}
