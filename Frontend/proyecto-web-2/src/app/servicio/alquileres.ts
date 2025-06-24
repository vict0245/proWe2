import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alquileres } from '../entidades/alquileres';
import { HttpClient } from '@angular/common/http';
import { LocalDate } from '@js-joda/core';
import { Vehiculos } from '../entidades/vehiculos';

type gdrReserva = {
  vehi: Vehiculos;
  fechaInicio: Date;
  fechaFin: Date;
  valorTotal: number;
  identificacion: String;
};

@Injectable({
  providedIn: 'root',
})
export class AlquileresServicio {
  private bdURL = 'http://localhost:8080/alquiler/detallesVehiculo';

  constructor(private httpClient: HttpClient) {}
  ObtenerListaVehiculos(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.bdURL);
  }

  private bdURLA =
    'http://localhost:8080/alquiler/verificarDisponibilidadVehiculo';
  verificarDisponibilidad(
    id: number,
    fechaInicio: LocalDate,
    fechaFin: LocalDate
  ): Observable<any> {
    return this.httpClient.post(`${this.bdURLA}`, {
      id,
      fechaInicio,
      fechaFin,
    });
  }

  calcularValorTotal(
    id: number,
    fechaInicio: LocalDate,
    fechaFin: LocalDate
  ): Observable<any> {
    const url = `http://localhost:8080/alquiler/valorTotal`;
    return this.httpClient.post(url, { id, fechaInicio, fechaFin });
  }

guardarReserva(si: gdrReserva): Observable<any> {
    // Preparar payload con estructura exacta que espera el backend
    const payload = {
        vehi: si.vehi,  // Asegúrate que coincide con el nombre en el backend
        fechaInicio: this.formatDate(si.fechaInicio), // Formatear fecha
        fechaFin: this.formatDate(si.fechaFin),       // Formatear fecha
        valorTotal: si.valorTotal,
        identificacion: si.identificacion
    };

    console.log('Payload final:', payload);
    
    // URL corregida (observa que es /alquiler, no /alquier)
    return this.httpClient.post(
        'http://localhost:8080/alquiler/guardarReserva',
        payload
    );
}

// Método auxiliar para formatear fechas
private formatDate(date: any): string {
    if (date instanceof Date) {
        return date.toISOString().split('T')[0]; // Formato YYYY-MM-DD
    }
    return date; // Si ya está en formato string
}

  private bdURLC = 'http://localhost:8080/alquiler/listarVehiculosAlquilados';
  obtenerListaAlquilados(): Observable<any> {
    return this.httpClient.get<any>(this.bdURLC);
  }

  private bdURLCU = 'http://localhost:8080/alquiler/listarVehiculosAlquiladosU';
  obtenerListaAlquiladosU(identificacion: number): Observable<any> {
    return this.httpClient.post<any>(this.bdURLCU, identificacion);
  }

  private bdURLD = 'http://localhost:8080/alquiler/Cancelar';

  cancelarAlquiler(id: number): Observable<any> {
    return this.httpClient.post(this.bdURLD, id);
  }

  private bdURLPendientes =
    'http://localhost:8080/alquiler/vehiculosPendientes';
  obtenerListaPendientes(): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.bdURLPendientes}`);
  }

  private bdURLAlid = 'http://localhost:8080/alquiler/obtenerReserva';
  obtenerAlquiler(reserva:any[]): Observable<any> {
    console.log('reserva: ',reserva);
    return this.httpClient.post(`${this.bdURLAlid}`, reserva);
  }
}
