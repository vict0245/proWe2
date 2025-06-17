package com.example.demo.controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.AdministradoresRepositorio;
import com.example.demo.repositorio.AlquileresRepositorio;
import com.example.demo.repositorio.VehiculosRepositorio;


@RestController
@RequestMapping("/administradores")
public class AdministradoresControlador {
	@Autowired
	private AdministradoresRepositorio repositorioA;
	
	@Autowired
	private VehiculosRepositorio repositorioV;
	
	@Autowired
	private AlquileresRepositorio repositorioAlquiler;
	
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
	
	@GetMapping("/listarvehiculos")
	public List<Vehiculos> listarVehiculos(){
		return repositorioV.findAll();
	}
	
	@GetMapping("/buscarvehiculo")
	public ResponseEntity<?> buscarvehiculo(@RequestParam int id){
		Optional<Vehiculos> vehiculoencontrado = repositorioV.findByIdVehiculo(id);
		if (vehiculoencontrado.isPresent()) {
			return ResponseEntity.ok(vehiculoencontrado.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vehiculo con ID" + id + "no fue encontrado");
		}
	}
	
	@PutMapping("/actualizarvehiculo")
	public ResponseEntity<?> actualizarvehiculo(@RequestParam int id,@RequestBody Map<String, Object> cambios){
		Optional<Vehiculos> vehiculoactualizado = repositorioV.findByIdVehiculo(id);
		if(vehiculoactualizado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vehiculo con ID: " +id+ "no encontrado");
		}
		try {
			Vehiculos vehiculo = vehiculoactualizado.get();
			cambios.forEach((clave, valor) -> {
			    switch (clave) {
			        case "placa": vehiculo.setPlaca((String) valor); break;
			        case "marca": vehiculo.setMarca((String) valor); break;
			        case "modelo": vehiculo.setModelo((String) valor); break;
			        case "color": vehiculo.setColor((String) valor); break;
			        case "estado": vehiculo.setEstado((String) valor); break;
			        case "valorAlquilerDia": vehiculo.setValorAlquilerDia(new BigDecimal(valor.toString())); break;
			        case "tipo": vehiculo.setTipo((String) valor); break;
			    }
			});
			Vehiculos actualizado = this.repositorioV.save(vehiculo);
			return ResponseEntity.ok(actualizado);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el vehiculo " + e.getMessage());
		}
	}
	
	@DeleteMapping("/eliminarvehiculo")
	public ResponseEntity<String> eliminar(@RequestParam int id){
		Optional<Vehiculos> vehiculo = repositorioV.findByIdVehiculo(id);

		if(vehiculo.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El vehiculo con Id " + id + " no existe, no se puede eliminar");
		}
		if (tieneAlquileresActivos(vehiculo.get())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body("No se puede eliminar el vehículo porque tiene reservas activas");
	    }
		try {
			repositorioV.deleteById(id);
			return ResponseEntity.ok("Vehiculo eliminado correctamente");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el vehiculo " + e.getMessage());
		}
	}
	
	 private boolean tieneAlquileresActivos(Vehiculos vehiculo) {
	        return repositorioAlquiler.existsByVehiculoAndEstadoIn(vehiculo, List.of("ENTREGADO", "PENDIENTE") 
	        );
	    }
	
}
