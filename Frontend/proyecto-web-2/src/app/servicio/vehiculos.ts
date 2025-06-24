import { Usuario } from './../entidades/usuario';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehiculos } from '../entidades/vehiculos';
import { catchError, Observable, throwError } from 'rxjs';
import { TransDatosService } from '../servicio/trans-datos';

@Injectable({
  providedIn: 'root',
})
export class VehiculosServicio {
  Usuario="";
  dato: boolean;

  constructor(private httpClient: HttpClient,private transfer:TransDatosService) {}
  private bdURL1 = 'http://localhost:8080/administradores/añadirvehiculo';
  registrarVehiculo(vehiculos: FormData): Observable<Object> {
    this.dato = true;
    return this.httpClient.post(`${this.bdURL1}`, vehiculos);
  }

  private bdURL2 = 'http://localhost:8080/gestionAlquiler/estadoEntregado';
  registrarGestion(placa: string): Observable<any> {
    this.info();

    if (!this.Usuario) {
      return throwError(
        () => new Error('No se encontró el ID del administrador')
      );
    }
    
    return this.httpClient.post(this.bdURL2,[ placa,this.Usuario.toString()]).pipe(
      catchError((error) => {
        console.error('Error en la entrega:', error);
        return throwError(() => error);
      })
    );
  }

  info(){
    this.transfer.datos$.subscribe(data => {
      if(data!=null){
        this.Usuario = data || '';
      }else{
      console.warn('no se recibieron datos');
    }
  });
  }

  private bdURL3 = 'http://localhost:8080/gestionAlquiler/estadoDevuelto';
  registrarGestionDevuelto(placa: string): Observable<object> {
    const params = {
      placa: placa.toString(),
    };
    return this.httpClient.put(`${this.bdURL3}`, null, { params });
  }

  private bdURL4 = 'http://localhost:8080/Vehiculos/tipoVehiculo';
  tipoVehiculo(tipo: string): Observable<any> {
    return this.httpClient.get(
      `http://localhost:8080/Vehiculos/tipoVehiculo?tipo=${tipo}`
    );
  }
}
