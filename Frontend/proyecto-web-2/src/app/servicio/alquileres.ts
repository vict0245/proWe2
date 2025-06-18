import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alquileres } from '../entidades/alquileres';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AlquileresServicio {

  private bdURL = "http://localhost:8080/alquiler/detallesVehiculo"

  constructor(private httpClient: HttpClient) {

   }
   ObtenerListaVehiculos(estado:String): Observable<Object[]>{
    return this.httpClient.post<Object[]>(`${this.bdURL}`,estado);
   }

}
