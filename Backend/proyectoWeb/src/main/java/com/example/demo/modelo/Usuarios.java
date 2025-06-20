package com.example.demo.modelo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuarios {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUsuario;
    @Column(unique = true, nullable = false)
    private String identificacion;
    private String nombre;
    private LocalDate fechaExpedicion;
    private String categoria;
    private LocalDate vigencia;
    private String email;
    private String telefono;
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Alquileres> alquileres;
    
    // metOdos

    // constructor
	public Usuarios(String identificacion, String nombre, LocalDate fechaExpedicion, String categoria, LocalDate vigencia,
			String email,String telefono, String password, List<Alquileres> alquileres) {
		super();

		this.identificacion = identificacion;
		this.nombre = nombre;
		this.fechaExpedicion = fechaExpedicion;
		this.categoria = categoria;
		this.vigencia = vigencia;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.alquileres = alquileres;
	}

	public Usuarios() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// Getters y setters

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(LocalDate fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public LocalDate getVigencia() {
		return vigencia;
	}

	public void setVigencia(LocalDate vigencia) {
		this.vigencia = vigencia;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String email) {
		this.identificacion = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Alquileres> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquileres> alquileres) {
		this.alquileres = alquileres;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	
}
