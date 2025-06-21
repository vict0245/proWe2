import { RouterOutlet } from '@angular/router';
import { Usuario } from './../entidades/usuario';
import { LoginServicio } from './../servicio/login';
import { Component, OnInit } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import { TransDatosService } from '../servicio/trans-datos';
import { Admin } from '../entidades/admin';


@Component({
  standalone: true,
  selector: 'app-login',
  imports: [FormsModule, RouterOutlet],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  usuarioA:string = '';
  identificacion: string = '';
  password: string = '';
  usuario: Usuario = new Usuario();
  admin:Admin = new Admin();
  constructor(private loginServicio: LoginServicio, private route:Router, private transfer:TransDatosService) {}

  OnInit(): void {
    this.abrirLogin_Usua();
  }

  abrirLogin_Admin(){
    const loginAd = document.getElementById("login-Administrador");
    const loginUs = document.getElementById("login-usuario");
    const reguisAd = document.getElementById("registro-admin");
    

    if(loginAd != null){
      if(loginUs != null){
        loginUs.style.display = "none";
      }if(reguisAd != null){
        reguisAd.style.display = "none";
      }
      loginAd.style.display = "block";
      
    }
  }

    abrirLogin_Usua(){
    const loginAd = document.getElementById("login-Administrador");
    const loginUs = document.getElementById("login-usuario");
    const reguisUs = document.getElementById("registro-usuario");
    

    if(loginUs != null){
      if(loginAd != null){
        loginAd.style.display = "none";
      }if(reguisUs != null){
        reguisUs.style.display = "none";
      }
      loginUs.style.display = "block";
      
    }
  }

  cerrarLogin(){
    const loginAd = document.getElementById("login-Administrador");
    const loginUs = document.getElementById("login-usuario");
    const reguisUs = document.getElementById("registro-usuario");
    
    if(loginAd!=null && loginUs!=null && reguisUs!=null){
      loginAd.style.display = "none";
      loginUs.style.display = "none";
      reguisUs.style.display = "none";
    }
  }

  loginUsua(){
    this.loginServicio.loginUsuario(this.identificacion,this.password).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido Usuario "+response);
            this.enviar(this.identificacion);
            this.limpiarCampos();
            this.route.navigate(['/bannerUsuario']);
            this.cerrarLogin();
            break;
          case false:
            alert("Login incorrecto compruebe email o contraseña "+response);
            break;
          case null:
            alert("Usuario no encontrado, por favor registrese.");
            break;
          default:
            alert("Error desconocido, por favor intente más tarde.");
        }
      }
      , error: (error) => {console.error("Login failed", error);}})
    }

  loginAdmin(){
    this.loginServicio.loginAdmin(this.usuarioA,this.password,true).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido administrador "+response);
            this.enviar(this.usuarioA);
            this.limpiarCampos();
            this.cerrarLogin();
            this.route.navigate(['/bannerAdministrador']);
            break;
          case false:
            alert("Login incorrecto compruebe email o contraseña "+response);
            break;
          case null:
            alert("Usuario no encontrado, por favor registrese.");
            break;
          default:
            alert("Error desconocido, por favor intente más tarde.");
            break;
        }
      }
      , error: (error) => {console.error("Login failed", error);}})
  }

  limpiarCampos() {
    this.usuarioA = '';
    this.identificacion = '';
    this.password = '';
  }

  abrirRegistroUsa() {
    const reguisUs = document.getElementById("registro-usuario");
    const loginUs = document.getElementById("login-usuario");

    if(reguisUs != null){
      if(loginUs != null){
        loginUs.style.display = "none";
      }
      reguisUs.style.display = "block";
    }
  }

  abrirRegistroAdmin() {
    const reguisAd = document.getElementById("registro-admin");
    const loginAd = document.getElementById("login-Administrador");

    if(reguisAd != null){
      if(loginAd != null){
        loginAd.style.display = "none";
      }
      reguisAd.style.display = "block";
    }
  }

  registrarUsuario() {
    this.loginServicio.reguistrarUsuario(this.usuario).subscribe({
      next: (response) => {
        if (response) {
          alert("Usuario registrado correctamente, por favor inicie sesión.");
        } else {
          alert("Error al registrar usuario, por favor intente más tarde.");
        }
      } ,
      error: (error) => {
        console.error("Registro failed", error);
        alert("Error al registrar usuario, por favor intente más tarde.");
      }
    })
  }

  registrarAdmin(){
    this.loginServicio.reguistrarAdministrador(this.admin).subscribe({
      next: (response) => {
        if (response) {
          alert("Administrador registrado correctamente, por favor inicie sesión.");
        } else {
          alert("Error al registrar Administrador, intente de nuevo.");
        }
      } ,
      error: (error) => {
        console.error("Registro failed", error);
        alert("Error al registrar Administrador, por favor intente más tarde.");
      }
    })
  }

  enviar(dato: any = null) {
    this.transfer.enviarDatos(dato);
  }
}
