package com.example.demo.controlador;

import java.util.HashMap;
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
import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.AdministradoresRepositorio;
import com.example.demo.modelo.Usuarios;
import com.example.demo.modelo.Vehiculos;
import com.example.demo.modelo.credenciales;
import com.example.demo.repositorio.AdministradoresRepositorio;
import com.example.demo.repositorio.AlquileresRepositorio;
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
	public Optional<Administradores> buscarPorId(@RequestBody Long id) {
		return this.repositorioA.findById(id);
	}
	
	@PostMapping("/buscarPorUsuario")
	public Administradores buscarPorNombre(@RequestBody String usuario){
		return this.repositorioA.findByUsuario(usuario);
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
		adminT.setUsuario(a.getUsuario());
		adminT.setPassword(encoder.encode(a.getPassword()));
		adminT.setGestiones(a.getGestiones());
		Administradores actualizado = this.repositorioA.save(adminT);
		return actualizado;
	}
	
	@PostMapping("/guardar")
	public Object guardarA(@RequestBody Administradores a) {
		try {
			String encodepass = encoder.encode(a.getPassword());
			a.setPassword(encodepass);
			this.repositorioA.save(a);	
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
	// TareasTrello
	@PostMapping("/iniciar")
	public Object iniciarSecionA(@RequestBody Object[] valores) {
		String usuario = (String)valores[0];
		String password = (String)valores[1];
		Administradores Apass=this.repositorioA.findByUsuario(usuario);
		if(Apass!=null) {
			if(encoder.matches(password,Apass.getPassword())) {
				Map<String, Object> response = new HashMap<>();
				response.put("success", true);
				response.put("id", Apass.getIdAdministrador());
				return ResponseEntity.ok(response);
			}else {
				return false;
			}
		}
		else {
			return null;
		}
	}
	
	@PostMapping("/cambiarEstadoVehiclo")
	public void cambiarEstadoVehiclo(@RequestBody credenciales c) {
		Long id = c.getId();
		String nuevoEstado = c.getEstado();
		Vehiculos v = this.repositorioA.estadoVehiculo(id);
		v.setEstado(nuevoEstado);
		vehRepo.save(v);
	}

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
	
}
