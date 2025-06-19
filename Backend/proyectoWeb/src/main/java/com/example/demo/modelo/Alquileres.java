package com.example.demo.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Alquileres")
public class Alquileres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlquiler;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculos vehiculo;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private BigDecimal valorTotal;
    private BigDecimal valorAdicional;
    private LocalDate fechaEntregaReal;

    @OneToMany(mappedBy = "alquiler")
    private List<Gestion_Alquiler> gestiones;

	public Alquileres(Usuarios usuario, Vehiculos vehiculo, LocalDate fechaInicio, LocalDate fechaFin,
			String estado, BigDecimal valorTotal, BigDecimal valorAdicional, LocalDate fechaEntregaReal,
			List<Gestion_Alquiler> gestiones) {
		super();
		this.usuario = usuario;
		this.vehiculo = vehiculo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.valorTotal = valorTotal;
		this.valorAdicional = valorAdicional;
		this.fechaEntregaReal = fechaEntregaReal;
		this.gestiones = gestiones;
	}

	public Alquileres() {
		super();
		// TODO Auto-generated constructor stub
	}


    // Getters y setters
	
	public Long getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(Long idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Vehiculos getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculos vehiculo) {
		this.vehiculo = vehiculo;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorAdicional() {
		return valorAdicional;
	}

	public void setValorAdicional(BigDecimal valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public LocalDate getFechaEntregaReal() {
		return fechaEntregaReal;
	}

	public void setFechaEntregaReal(LocalDate fechaEntregaReal) {
		this.fechaEntregaReal = fechaEntregaReal;
	}

	public List<Gestion_Alquiler> getGestiones() {
		return gestiones;
	}

	public void setGestiones(List<Gestion_Alquiler> gestiones) {
		this.gestiones = gestiones;
	}
    
}

