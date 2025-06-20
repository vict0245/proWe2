import { RouterOutlet } from '@angular/router';
import { Usuario } from './../entidades/usuario';
import { LoginServicio } from './../servicio/login';
import { Component, OnInit } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import { TransDatosService } from '../servicio/trans-datos';


@Component({
  standalone: true,
  selector: 'app-login',
  imports: [FormsModule, RouterOutlet],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  usuario: Usuario = new Usuario();
  constructor(private loginServicio: LoginServicio, private route:Router, private transfer:TransDatosService) {}

  OnInit(): void {
    this.abrirLogin_Usua();
  }

  abrirLogin_Admin(){
    const loginAd = document.getElementById("login-Administrador");
    const loginUs = document.getElementById("login-usuario");

    if(loginAd != null){
      if(loginUs != null){
        loginUs.style.display = "none";
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
    this.loginServicio.loginUsuario(this.email,this.password).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido Usuario "+response);
            console.log("Email: ", this.email);
            this.enviar();
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
    console.log("email: ", this.email, " password: ", this.password);
    this.loginServicio.loginAdmin(this.email,this.password).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido administrador "+response);
            this.enviar();
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
    this.email = '';
    this.password = '';
  }

  abrirRegistro() {
    const reguisUs = document.getElementById("registro-usuario");
    const loginUs = document.getElementById("login-usuario");

    if(reguisUs != null){
      if(loginUs != null){
        loginUs.style.display = "none";
      }
      reguisUs.style.display = "block";
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

  enviar() {
    this.transfer.enviarDatos({ email: this.email });
  }
}
