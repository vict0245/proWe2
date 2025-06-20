package com.example.demo.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Usuarios;
import com.example.demo.modelo.credenciales;
import com.example.demo.repositorio.UsuariosRepositorio;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200/")
public class UsuariosControlador {

	@Autowired
	private UsuariosRepositorio repositorioU;
	
	@Autowired
	private PasswordEncoder encoder;
	
	//CRUD
	@GetMapping("/ver")
	public List<Usuarios> ver(){
		return this.repositorioU.findAll();
	}
	
	@PostMapping("/buscarPorId")
	public Optional<Usuarios> buscarPorId(@RequestParam Long id) {
		return this.repositorioU.findById(id);
	}
	
	@PostMapping("/buscarPorIdentificacion")
	public List<Usuarios> buscarPorIdentificacion(@RequestParam String identificacion){
		return this.repositorioU.findByTelefono(identificacion);
	}
	
	@PostMapping("/buscarPorNombre")
	public List<Usuarios> buscarPorNombre(@RequestParam String nombre){
		return this.repositorioU.findByNombre(nombre);
	}
	
	@PostMapping("/buscarPorFechaExpedicion")
	public List<Usuarios> buscarPorFechaExpedicion(@RequestBody LocalDate fechaExpedicion){
		return this.repositorioU.findByFechaExpedicion(fechaExpedicion);
	}
	
	@PostMapping("/buscarPorCategoia")
	public List<Usuarios> buscarPorCategoria(@RequestParam String categoria){
		return this.repositorioU.findByTelefono(categoria);
	}
	
	@PostMapping("/buscarPorVigencia")
	public List<Usuarios> buscarPorVigencia(@RequestParam String vigencia){
		return this.repositorioU.findByTelefono(vigencia);
	}
	
	@PostMapping("/buscarPorEmail")
	public List<Usuarios> buscarPorEmail(@RequestParam String email){
		return this.repositorioU.findByTelefono(email);
	}
	
	@PostMapping("/buscarPorTelefono")
	public List<Usuarios> buscarPorTelefono(@RequestParam String telefono){
		return this.repositorioU.findByTelefono(telefono);
	}
	
	@PostMapping("/eliminar")
	public void eliminar(@RequestParam Long id) {
		this.repositorioU.deleteById(id);
	}
	
	@PostMapping("/actualizar")
	public Usuarios actualizar(@RequestBody Usuarios u) {
		Usuarios usuaT = this.repositorioU.findById(u.getIdUsuario()).get();
		usuaT.setIdentificacion(u.getIdentificacion());
		usuaT.setNombre(u.getNombre());
		usuaT.setFechaExpedicion(u.getFechaExpedicion());
		usuaT.setCategoria(u.getCategoria());
		usuaT.setVigencia(u.getVigencia());
		usuaT.setTelefono(u.getTelefono());
		
		Usuarios actualizado = this.repositorioU.save(usuaT);
		return actualizado;
	}
	
	@PostMapping("/guardar")
	public Object guardarA(@RequestBody Usuarios u) {
		try {
			String encodepass = encoder.encode(u.getPassword());
			u.setPassword(encodepass);
			this.repositorioU.save(u);	
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// TareasTrello
		@PostMapping("/iniciar")
		public Object iniciarSecionA(@RequestBody credenciales valores) {
			String identificacion = valores.getIdentificacion();
			String password = valores.getPassword();
			Usuarios Apass=this.repositorioU.findByIdentificacion(identificacion);
			
			if(Apass!=null) {
				if(encoder.matches(password,Apass.getPassword())) {
					return true;
				}else {
					return false;
				}
			}
			else {
				return null;
			}
				
		}
}


	