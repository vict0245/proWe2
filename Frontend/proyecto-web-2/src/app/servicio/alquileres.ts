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
ObtenerListaVehiculos(): Observable<any[]> {
  return this.httpClient.get<any[]>(this.bdURL);
}

private bdURLA ="http://localhost:8080/alquiler/verificarDisponibilidad"
verificarDisponibilidad(fechaInicio: any, fechaFin: any): Observable<any> {
  const inicio = new Date(fechaInicio);
  const fin = new Date(fechaFin);

  const url = `${this.bdURLA}?fechaInicio=${inicio.toISOString().split('T')[0]}&fechaFin=${fin.toISOString().split('T')[0]}`;
  return this.httpClient.post(url, null, { responseType: 'text' });

}

calcularValorTotal(idVehiculo: number, fechaInicio: any, fechaFin: any): Observable<any> {
  const inicio = new Date(fechaInicio).toISOString().split('T')[0];
  const fin = new Date(fechaFin).toISOString().split('T')[0];
  const url = `http://localhost:8080/alquiler/valorTotal?id=${idVehiculo}&fechaInicio=${inicio}&fechaFin=${fin}`;
  return this.httpClient.post(url, null, { responseType: 'text' });
}

guardarReserva(reserva: any): Observable<any> {
  return this.httpClient.post('http://localhost:8080/alquiler/guardarReserva', reserva, {
    responseType: 'text'
  });
}

private bdURLC = "http://localhost:8080/alquiler/VehiculoAlquilado"
obtenerListaAlquilados(): Observable<any>{
  return this.httpClient.get<any>(this.bdURLC)
}




}
