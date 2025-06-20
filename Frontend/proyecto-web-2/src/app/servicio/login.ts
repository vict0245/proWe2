import { Admin } from './../entidades/admin';
import { Usuario } from './../entidades/usuario';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginServicio {
  private urlLoginUsa: string = "http://localhost:8080/usuarios/iniciar";
  private urlLoginAdmin: string = "http://localhost:8080/administradores/iniciar";
  private urlRegistrarUsa: string = "http://localhost:8080/usuarios/guardar";
  private urlRegistrarAdmin: string = "http://localhost:8080/administradores/guardar";
  
  constructor(private httpClient: HttpClient){}

  loginUsuario(identificacion: string, password: string): Observable<any> {
    return this.httpClient.post(`${this.urlLoginUsa}`,{identificacion,password});
  }

  loginAdmin(usuarioA: string, password: string,t:boolean): Observable<any> {
    console.log(usuarioA, password, t);
    return this.httpClient.post(`${this.urlLoginAdmin}`,[usuarioA,password,t]);
  }

  reguistrarUsuario(usuario: Usuario): Observable<any> {
    return this.httpClient.post(`${this.urlRegistrarUsa}`, usuario);
  }
  
  reguistrarAdministrador(Admin:Admin): Observable<any> {
    return this.httpClient.post(`${this.urlRegistrarAdmin}`, Admin);

  }
}
