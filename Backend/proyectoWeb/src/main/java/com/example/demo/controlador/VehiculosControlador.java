package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/Vehiculos")
@CrossOrigin(origins = "http://localhost:4200/")


public class VehiculosControlador {
	
	@Autowired
	private VehiculosRepositorio repositorioV;
	
	@GetMapping("/VehiculosDisponibles")
	public List<Vehiculos> verVehiculosDis(){
		return repositorioV.VehiculosDisponibles();
		}

	private VehiculosRepositorio RepoVehiculos;
	
	@PostMapping("/guardar")
	public ResponseEntity<Vehiculos> Guardar(@RequestBody Vehiculos vehiculo){
		try {
			Vehiculos nuevoVehi = this.RepoVehiculos.save(vehiculo);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehi);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	
	}
	

}
