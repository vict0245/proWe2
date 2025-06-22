import { VehiculosServicio } from './../servicio/vehiculos';
import { Component, EventEmitter, Output } from '@angular/core';
import { ComunicacionService } from '../comunicacion';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-nav-usuario',
  imports: [],
  templateUrl: './nav-usuario.html',
  styleUrl: './nav-usuario.css'
})
export class NavUsuarioComponent {
  constructor(private comunicacion: ComunicacionService, private router: Router) {}
  @Output() reloandAfterInit = new EventEmitter<void>();
  @Output() abrirModalAlquiladosRequest = new EventEmitter<void>();



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
    this.abrirModalAlquiladosRequest.emit();
  }
}

 

  

