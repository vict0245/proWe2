package com.example.demo.modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Gestion_Alquiler")
public class Gestion_Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestion;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Administradores administrador;

    @ManyToOne
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquileres alquiler;

    private LocalDate fechaAccion;
    private String accion;


    public Gestion_Alquiler(Administradores administrador, Alquileres alquiler, LocalDate fechaAccion,
			String accion) {
		super();
		this.administrador = administrador;
		this.alquiler = alquiler;
		this.fechaAccion = fechaAccion;
		this.accion = accion;
	}

	public Gestion_Alquiler() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters y setters
	public Long getIdGestion() {
		return idGestion;
	}

	public void setIdGestion(Long idGestion) {
		this.idGestion = idGestion;
	}

	public Administradores getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administradores administrador) {
		this.administrador = administrador;
	}

	public Alquileres getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(Alquileres alquiler) {
		this.alquiler = alquiler;
	}

	public LocalDate getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(LocalDate fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
    
}

