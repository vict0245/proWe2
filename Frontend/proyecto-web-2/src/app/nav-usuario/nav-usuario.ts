import { VehiculosServicio } from './../servicio/vehiculos';
import { Component, EventEmitter, Output } from '@angular/core';
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
  @Output() reloandAfterInit = new EventEmitter<void>();
  @Output() abrirModalAlquilados = new EventEmitter<void>();

    Vehiculos: VehiculosServicio;

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

 

  

