package com.example.demo.modelo;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administradores")
public class Administradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrador;

    private String nombre;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    private String telefono;

    @OneToMany(mappedBy = "administrador")
    private List<Gestion_Alquiler> gestiones;

    // Constructor
    public Administradores(String nombre, String password, String email, String telefono,
			List<Gestion_Alquiler> gestiones) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.email = email;
		this.telefono = telefono;
		this.gestiones = gestiones;
	}
	public Administradores() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	// Getters y setters
	public Long getIdAdministrador() {
		return idAdministrador;
	}
	public void setIdAdministrador(Long idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public List<Gestion_Alquiler> getGestiones() {
		return gestiones;
	}
	public void setGestiones(List<Gestion_Alquiler> gestiones) {
		this.gestiones = gestiones;
	}
}

