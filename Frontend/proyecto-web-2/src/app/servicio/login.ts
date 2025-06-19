import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../entidades/usuario';

@Injectable({
  providedIn: 'root'
})
export class LoginServicio {
  private urlLoginUsa: string = "http://localhost:8080/usuarios/iniciar";
  private urlLoginAdmin: string = "http://localhost:8080/administradores/iniciar";
  private urlRegistrarUsa: string = "http://localhost:8080/usuarios/guardar";
  
  constructor(private httpClient: HttpClient){}

  loginUsuario(email: string, password: string): Observable<any> {
    return this.httpClient.post(`${this.urlLoginUsa}`,{email,password});
  }

  loginAdmin(email: string, password: string): Observable<any> {
    return this.httpClient.post(`${this.urlLoginAdmin}`,{email,password});
  }

  reguistrarUsuario(usuario: Usuario): Observable<any> {
    return this.httpClient.post(`${this.urlRegistrarUsa}`, usuario);
  }
}
