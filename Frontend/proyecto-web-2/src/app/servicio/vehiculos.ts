import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehiculosServicio {
  dato: boolean;

  constructor(private httpClient: HttpClient) {

   }
   private bdURL1 = "http://localhost:8080/administradores/añadirvehiculo";
   registrarVehiculo(vehiculos: Vehiculos): Observable<Object>{
    this.dato=true;
    return this.httpClient.post(`${this.bdURL1}`,vehiculos);
   }
}
