package com.example.demo.controlador;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repositorio.AlquileresRepositorio;

@RestController
@RequestMapping("/alquiler")
@CrossOrigin(origins = "http://localhost:4200/")
public class AlquileresControlador {
	
	@Autowired
	private AlquileresRepositorio repositorioAlquiler;
	
	@PostMapping("/detallesVehiculo")
	public ResponseEntity<?> detalle(){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.mostrarVehi();
	        return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}
	
	@PostMapping("/valorTotalAlquiler")
	public ResponseEntity<?> Vta(@RequestParam int id,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date FI,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date FF) {
		try {
			List<Object> valor = repositorioAlquiler.valorTotal(id, FI, FF);
			return ResponseEntity.ok(valor);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}
	
	@PostMapping("/resumenSolicitud")
	public ResponseEntity<?> Resumen(){
		try {
			List<Object[]> resuSoli = repositorioAlquiler.soli();
			return ResponseEntity.ok(resuSoli);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	

}
