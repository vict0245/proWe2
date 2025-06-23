package com.example.demo.modelo;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehiculos")
public class Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;
    @Column(unique = true, nullable = false)
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String estado;
    private BigDecimal valorAlquilerDia;
    private String tipo;
    
    @JsonIgnore
    @OneToMany(mappedBy = "vehiculo")
    private List<Alquileres> alquileres;

    // metOdos
    
    
	public Vehiculos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vehiculos(String placa, String marca, String modelo, String color, String estado,
			BigDecimal valorAlquilerDia, String tipo, List<Alquileres> alquileres) {
		super();
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.estado = estado;
		this.valorAlquilerDia = valorAlquilerDia;
		this.tipo = tipo;
		this.alquileres = alquileres;
	}

    // Getters y setters
	
	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorAlquilerDia() {
		return valorAlquilerDia;
	}

	public void setValorAlquilerDia(BigDecimal valorAlquilerDia) {
		this.valorAlquilerDia = valorAlquilerDia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Alquileres> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquileres> alquileres) {
		this.alquileres = alquileres;
	}

}

