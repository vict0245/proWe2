package com.example.demo.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Gestion_Alquiler;
import com.example.demo.repositorio.AdministradoresRepositorio;


@RestController
@RequestMapping("/administradores")
public class AdministradoresControlador {
	@Autowired
	private AdministradoresRepositorio repositorioA;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// CRUD
	@GetMapping("/ver")
	public List<Administradores> ver(){
		return this.repositorioA.findAll();
	}
	
	@PostMapping("/buscarPorId")
	public Optional<Administradores> buscarPorId(@RequestParam Long id) {
		return this.repositorioA.findById(id);
	}
	
	@PostMapping("/buscarPorNombre")
	public List<Administradores> buscarPorNombre(@RequestParam String nombre){
		return this.repositorioA.findByNombre(nombre);
	}
	
	@PostMapping("/buscarPorTelefono")
	public List<Administradores> buscarPorTelefono(@RequestParam String telefono){
		return this.repositorioA.findByTelefono(telefono);
	}
	
	@PostMapping("/buscarPorGestion")
	public Administradores buscarPorGestion(@RequestBody Gestion_Alquiler gestion){
		return this.repositorioA.findByGestiones(gestion);
	}
	
	@PostMapping("/eliminar")
	public void eliminar(@RequestParam Long id) {
		this.repositorioA.deleteById(id);
	}
	
	@PostMapping("/actualizar")
	public Administradores actualizar(@RequestBody Administradores a) {
		Administradores adminT = this.repositorioA.findById(a.getIdAdministrador()).get();
		adminT.setNombre(a.getNombre());
		adminT.setPassword(a.getPassword());
		adminT.setEmail(a.getEmail());
		adminT.setTelefono(a.getTelefono());
		adminT.setGestiones(a.getGestiones());
		Administradores actualizado = this.repositorioA.save(adminT);
		return actualizado;
	}
	
	@PostMapping("/guardar")
	public Object guardarA(@RequestBody Administradores a) {
		try {
			String encodepass = encoder.encode(a.getPassword());
			a.setPassword(encodepass);
			Administradores nuevoAdministrador = this.repositorioA.save(a);	
			return encodepass;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	
	
	// TareasTrello
	@PostMapping("/iniciar")
	public Object iniciarSecionA(@RequestParam Long id,@RequestParam String password) {
		Administradores Apass=this.repositorioA.findById(id).get();
		if(Apass!=null) {
			if(encoder.matches(password,Apass.getPassword())) {
				return "yes";
			}else {
				return "no";
			}
		}
		else {
			return "no hay nada jajaja";
		}
			
	}
}
