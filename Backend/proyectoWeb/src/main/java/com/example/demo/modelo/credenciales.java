package com.example.demo.modelo;

import java.time.LocalDate;

public class credenciales {
		String usuario;
		String identificacion;
		String password;
		LocalDate fechaInicio;
		LocalDate fechaFin;
		Long id;
		String estado;
		
		public credenciales() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public credenciales(String identificacion, String password) {
			super();
			this.identificacion = identificacion;
			this.password = password;
		}
		
		public credenciales(String usuario, String password,boolean si) {
			super();
			if(si==true) {
				this.usuario = usuario;
				this.password = password;
			}else {
				System.out.println("boolean false");
			}
			
		}
		
		public credenciales(Long id, LocalDate fechaInicio,LocalDate fechaFin) {
			super();
			this.id=id;
			this.fechaInicio=fechaInicio;
			this.fechaFin=fechaFin;
		}
		
		public credenciales(LocalDate fechaInicio,LocalDate fechaFin) {
			super();
			this.fechaInicio=fechaInicio;
			this.fechaFin=fechaFin;
		}
		
		public credenciales(Long id, String estado) {
			super();
			this.id=id;
			this.estado=estado;
		}
		
		public String getIdentificacion() {
			return identificacion;
		}
		
		public void setIdentificacion(String email) {
			this.identificacion = email;
		}
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}

		public LocalDate getFechaInicio() {
			return fechaInicio;
		}

		public void setFechaInicio(LocalDate fechaInicio) {
			this.fechaInicio = fechaInicio;
		}

		public LocalDate getFechaFin() {
			return fechaFin;
		}

		public void setFechaFin(LocalDate fechaFin) {
			this.fechaFin = fechaFin;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		
		
}
