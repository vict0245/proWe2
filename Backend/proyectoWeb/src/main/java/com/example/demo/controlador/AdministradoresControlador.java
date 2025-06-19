package com.example.demo.controlador;

import java.util.List;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Gestion_Alquiler;
<<<<<<< HEAD
import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.AdministradoresRepositorio;
=======
import com.example.demo.modelo.Usuarios;
import com.example.demo.modelo.Vehiculos;
import com.example.demo.modelo.credenciales;
import com.example.demo.repositorio.AdministradoresRepositorio;
import com.example.demo.repositorio.AlquileresRepositorio;
>>>>>>> master
import com.example.demo.repositorio.VehiculosRepositorio;


@RestController
@RequestMapping("/administradores")
@CrossOrigin(origins = "http://localhost:4200/")
public class AdministradoresControlador {
	
	@Autowired
	private VehiculosRepositorio repositorioV;
	
	@Autowired
	private AlquileresRepositorio repositorioAlquiler;
	@Autowired
	private AdministradoresRepositorio repositorioA;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private VehiculosRepositorio vehRepo;
	
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
	
	@PostMapping("/buscarPorEmail")
	public Administradores buscarPorGestion(@RequestBody String email){
		return this.repositorioA.findByEmail(email);
	}
	
	@PostMapping("/eliminar")
	public void eliminar(@RequestParam Long id) {
		this.repositorioA.deleteById(id);
	}
	
	@PostMapping("/actualizar")
	public Administradores actualizar(@RequestBody Administradores a) {
		Administradores adminT = this.repositorioA.findById(a.getIdAdministrador()).get();
		adminT.setNombre(a.getNombre());
		adminT.setPassword(encoder.encode(a.getPassword()));
		adminT.setEmail(a.getEmail());
		adminT.setTelefono(a.getTelefono());
		adminT.setGestiones(a.getGestiones());
		Administradores actualizado = this.repositorioA.save(adminT);
		return actualizado;
	}
	
	@PostMapping("guardar")
	public Object guardarA(@RequestBody Administradores a) {
		try {
			String encodepass = encoder.encode(a.getPassword());
			a.setPassword(encodepass);
			Administradores nuevoAdministrador = this.repositorioA.save(a);	
			return nuevoAdministrador;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
		
	// TareasTrello
	@PostMapping("/iniciar")
	public Object iniciarSecionA(@RequestBody credenciales valores) {
		String email = valores.getEmail();
		String password = valores.getPassword();
		Administradores Apass=this.repositorioA.findByEmail(email);
		
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
	
<<<<<<< HEAD
	@PostMapping("/cambiarEstadoVehiclo")
	public void cambiarEstadoVehiclo(@RequestParam Long id,@RequestParam String nuevoEstado) {
		Vehiculos v = this.repositorioA.estadoVehiculo(id);
		v.setEstado(nuevoEstado);
		vehRepo.save(v);
	}
=======
	@PostMapping("/añadirvehiculo")
	public ResponseEntity<?> añadirvehiculo(@RequestBody Vehiculos vehiculos){
		try {
			if(repositorioV.existsByPlaca(vehiculos.getPlaca())) {
	            return ResponseEntity.badRequest().body("La placa ya está registrada");
	        }
			Vehiculos nuevovehiculo = this.repositorioV.save(vehiculos);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevovehiculo);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al procesar la solicitud");
		}
	}
	
>>>>>>> master
}
