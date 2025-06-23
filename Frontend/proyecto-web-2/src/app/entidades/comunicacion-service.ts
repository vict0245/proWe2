import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComunicacionService {
  private tipoFiltro = new Subject<string>();
  tipoFiltro$ = this.tipoFiltro.asObservable();

  private mostrar = new Subject<void>();
  Mostrar$ = this.mostrar.asObservable();

  emitirTipo(tipo: string) {
    this.tipoFiltro.next(tipo);
  }

  mostrarTodo(){
    this.mostrar.next();
  }
}
