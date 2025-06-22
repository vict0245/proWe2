import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alquileres } from '../entidades/alquileres';
import { HttpClient } from '@angular/common/http';
import { LocalDate } from '@js-joda/core';

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

private bdURLA ="http://localhost:8080/alquiler/verificarDisponibilidadVehiculo"
verificarDisponibilidad(id:number,fechaInicio: LocalDate, fechaFin: LocalDate): Observable<any> {
return this.httpClient.post(`${this.bdURLA}`,{id,fechaInicio, fechaFin});

}

calcularValorTotal(id: number, fechaInicio: LocalDate, fechaFin: LocalDate): Observable<any> {
  const url = `http://localhost:8080/alquiler/valorTotal`;
  return this.httpClient.post(url,{id, fechaInicio, fechaFin});
}

guardarReserva(reserva: any): Observable<any> {
  return this.httpClient.post('http://localhost:8080/alquiler/guardarReserva', reserva, {
    responseType: 'text'
  });
}

private bdURLC = "http://localhost:8080/alquiler/listarVehiculosAlquilados"
obtenerListaAlquilados(): Observable<any>{
  return this.httpClient.get<any>(this.bdURLC)
}

private bdURLD = "http://localhost:8080/alquiler/Cancelar"
// Angular servicio
cancelarAlquiler(id: number): Observable<any> {
  return this.httpClient.post(`${this.bdURLD}?id=${id}`, {});
}





}
