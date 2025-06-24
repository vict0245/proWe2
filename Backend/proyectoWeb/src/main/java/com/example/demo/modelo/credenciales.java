package com.example.demo.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class credenciales {
		private String usuario;
		private String identificacion;
		private String password;
		private LocalDate fechaInicio;
		private LocalDate fechaFin;
		private Long id;
		private String estado;
		private Vehiculos vehi;
		private BigDecimal valorTotal;
		
		public credenciales() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public credenciales(Vehiculos vehi,LocalDate fechaInicio , LocalDate fechaFin, BigDecimal valorTotal, String identificacion) {
			this.vehi=vehi;
			this.fechaInicio=fechaInicio;
			this.fechaFin=fechaFin;
			this.valorTotal=valorTotal;
			this.identificacion=identificacion;
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

		public Vehiculos getVehi() {
			return vehi;
		}

		public void setVehi(Vehiculos vehi) {
			this.vehi = vehi;
		}

		public BigDecimal getValorTotal() {
			return valorTotal;
		}

		public void setValorTotal(BigDecimal valorTotal) {
			this.valorTotal = valorTotal;
		}
		
}
