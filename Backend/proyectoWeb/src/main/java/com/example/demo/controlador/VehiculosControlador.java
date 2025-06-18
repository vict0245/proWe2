package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping ("/Vehiculos")

public class VehiculosControlador {
	
	@Autowired
	private VehiculosRepositorio repositorioV;
	
	@GetMapping("/VehiculosDisponibles")
	public List<Vehiculos> verVehiculosDis(){
		return repositorioV.VehiculosDisponibles();
		}


}
