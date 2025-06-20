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

    @Column(unique = true, nullable = false)
    private String usuario;
    private String password;

    @OneToMany(mappedBy = "administrador")
    private List<Gestion_Alquiler> gestiones;

    // Constructor
    public Administradores(String usuario, String password, List<Gestion_Alquiler> gestiones) {
		super();
		this.usuario = usuario;
		this.password = password;
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
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Gestion_Alquiler> getGestiones() {
		return gestiones;
	}
	
	public void setGestiones(List<Gestion_Alquiler> gestiones) {
		this.gestiones = gestiones;
	}
	
}

