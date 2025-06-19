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

   private bdURL2 = "http://localhost:8080/gestionAlquiler/estadoEntregado";
   registrarGestion(idAlquiler: number, idAdministrador: number): Observable<object>{
    const params = {
      idAlquiler: idAlquiler.toString(),
    idAdministrador: idAdministrador.toString()
    };
    return this.httpClient.put(`${this.bdURL2}`,null,{params});
   }
   private bdURL3 = "http://localhost:8080/gestionAlquiler/estadoDevuelto";
   registrarGestionDevuelto(idAlquiler: number): Observable<object>{
    const params = {
      idAlquiler: idAlquiler.toString()
    };
    return this.httpClient.put(`${this.bdURL3}`,null,{params});
   }
}

