import { Usuario } from './../entidades/usuario';
import { LoginServicio } from './../servicio/login';
import { Component, OnInit } from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  usuario: Usuario = new Usuario();
  constructor(private loginServicio: LoginServicio) {}

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

  loginUsua(){
    this.loginServicio.loginUsuario(this.email,this.password).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido administrador "+response);
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
      this.limpiarCampos();
  }

  loginAdmin(){
    console.log("email: ", this.email, " password: ", this.password);
    this.loginServicio.loginAdmin(this.email,this.password).subscribe({
      next: (response) => {
        switch(response) {
          case true:
            alert("Login correcto bienvenido administrador "+response);
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
      this.limpiarCampos();
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
    alert("Funcionalidad de registro aún no implementada.");
    this.loginServicio.reguistrarUsuario(this.usuario).subscribe({
      next: (response) => {
        if (response) {
          alert("Usuario registrado correctamente.");
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
}
