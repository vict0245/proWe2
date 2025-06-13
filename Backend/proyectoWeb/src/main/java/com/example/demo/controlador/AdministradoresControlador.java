package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Administradores;
import com.example.demo.repositorio.AdministradoresRepositorio;


@RestController
@RequestMapping("/administradores")
public class AdministradoresControlador {
	@Autowired
	private AdministradoresRepositorio repositorioA;
	
	@PostMapping("/guardar")
	public Object guardarA(@RequestBody Administradores a) {
		try {
			Administradores nuevoAdministrador = this.repositorioA.save(a);	
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAdministrador);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	
	@PostMapping("/iniciar")
	public Object iniciarSecionA(@RequestParam String id,@RequestParam String password) {
		String Apass=this.repositorioA.buscarAdministrador(0);
		if(Apass.isEmpty()) {
			return "no hay nada jajaja";
		}
		else if(Apass.equals(password)) {
			return "yes";
		}else {
			return "no";
		}
			
	}
}
