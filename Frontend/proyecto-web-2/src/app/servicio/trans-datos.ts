import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransDatosService {

  constructor() { }

  private datos = new BehaviorSubject<any>(null);
  datos$ = this.datos.asObservable();

  enviarDatos(info: any) {
    this.datos.next(info);
  }
}
