import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { catchError, Observable, throwError } from 'rxjs';

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
    registrarGestion(placa: string): Observable<any> {
    const idAdministrador = localStorage.getItem('idAdministrador');
    
    if (!idAdministrador) {
        return throwError(() => new Error('No se encontró el ID del administrador'));
    }

    const params = { placa };
    const headers = { 'idAdministrador': idAdministrador };

    return this.httpClient.put(this.bdURL2, null, { params, headers }).pipe(
        catchError(error => {
            console.error('Error en la entrega:', error);
            return throwError(() => error);
        })
    );
}
   private bdURL3 = "http://localhost:8080/gestionAlquiler/estadoDevuelto";
   registrarGestionDevuelto(placa: string): Observable<object>{
    const params = {
      placa: placa.toString()
    };
    return this.httpClient.put(`${this.bdURL3}`,null,{params});
   }
}

