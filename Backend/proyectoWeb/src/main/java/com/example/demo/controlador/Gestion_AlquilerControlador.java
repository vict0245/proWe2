package com.example.demo.controlador;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Gestion_Alquiler;
import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.AdministradoresRepositorio;
import com.example.demo.repositorio.AlquileresRepositorio;
import com.example.demo.repositorio.Gestion_AlquilerRepositorio;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/gestionAlquiler")
public class Gestion_AlquilerControlador {
	
	@Autowired
	private AdministradoresRepositorio repositorioA;
	
	@Autowired
	private VehiculosRepositorio repositorioV;
	
	@Autowired
	private AlquileresRepositorio repositorioAlquiler;
	
	@Autowired
	private Gestion_AlquilerRepositorio repositorioGestion;
	
	@PutMapping("/estadoEntregado")
	public ResponseEntity<?> entregarVehiculo(@RequestParam Integer idAlquiler, @RequestParam Integer idAdministrador) {
	    Optional<Alquileres> alquilerRevision = repositorioAlquiler.findById(idAlquiler);

	    if (alquilerRevision.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alquiler no encontrado");
	    }

	    Alquileres alquiler = alquilerRevision.get();

	    alquiler.setEstado("ENTREGADO");
	    alquiler.getVehiculo().setEstado("ENTREGADO");

	    repositorioAlquiler.save(alquiler);
	    repositorioV.save(alquiler.getVehiculo());

	    Gestion_Alquiler gestion = new Gestion_Alquiler();
	    gestion.setAccion("entregado");
	    gestion.setFechaAccion(new Date());
	    gestion.setAlquiler(alquiler);

	    Administradores admin = repositorioA.findByIdAdministrador(idAdministrador).get(); // Temporal
	    gestion.setAdministrador(admin);

	    repositorioGestion.save(gestion);
	    

	    return ResponseEntity.ok("Vehículo entregado correctamente");
	}
	
	@PutMapping("/estadoDevuelto")
	public ResponseEntity<?> devolverVehiculo(@RequestParam Integer idAlquiler) {
	    Optional<Alquileres> alquilerRevision = repositorioAlquiler.findById(idAlquiler);

	    if (alquilerRevision.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alquiler no encontrado");
	    }

	    Alquileres alquiler = alquilerRevision.get();

	    Date fechaActual = new Date();
	    Date fechaFin = alquiler.getFechaFin();

	    long retrasoHoras = 0;
	    if (fechaActual.after(fechaFin)) {
	        long diferenciaMs = fechaActual.getTime() - fechaFin.getTime();
	        retrasoHoras = TimeUnit.HOURS.convert(diferenciaMs, TimeUnit.MILLISECONDS);
	    }

	    BigDecimal valorAdicional = BigDecimal.valueOf(retrasoHoras * 10000); 
	    BigDecimal nuevoTotal = alquiler.getValorTotal().add(valorAdicional);

	    alquiler.setFechaEntregaReal(fechaActual);
	    alquiler.setEstado("FINALIZADO");
	    alquiler.setValorAdicional(valorAdicional);
	    alquiler.setValorTotal(nuevoTotal);

	    repositorioAlquiler.save(alquiler);

	    Vehiculos vehiculo = alquiler.getVehiculo();
	    vehiculo.setEstado("DISPONIBLE");
	    repositorioV.save(vehiculo);

	    Gestion_Alquiler gestion = new Gestion_Alquiler();
	    gestion.setAccion("DEVUELTO");
	    gestion.setFechaAccion(fechaActual);
	    gestion.setAlquiler(alquiler);

	    Administradores admin = repositorioA.findByIdAdministrador(1).get(); // Temporal
	    gestion.setAdministrador(admin);

	    repositorioGestion.save(gestion);

	    return ResponseEntity.ok("Vehículo devuelto correctamente. Total: $" + nuevoTotal);
	}
	  
	@PostMapping("/RegistrarAccion")
		ResponseEntity<?> registroAccion(@RequestParam Integer idAlquiler, @RequestParam Integer idAdministrador, @RequestParam String accion ){
		try {
		Optional<Alquileres> Alquilertengo = repositorioAlquiler.findById(idAlquiler);
		Optional<Administradores> Administradortengo = repositorioA.findByIdAdministrador(idAdministrador);
		
		if(Alquilertengo.isPresent() && Administradortengo.isPresent()) {
			Date fechahoy = new Date();
			Gestion_Alquiler gestion = new Gestion_Alquiler();
			gestion.setAdministrador(Administradortengo.get());
			gestion.setAlquiler(Alquilertengo.get());
			gestion.setFechaAccion(fechahoy);
			gestion.setAccion(accion);
			
			repositorioGestion.save(gestion);
			return ResponseEntity.ok("Accion Registrada");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador o Alquiler no encontrado.");
		}
	}catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la accion");
	}


}
	
	@GetMapping("/ping")
	public String ping() {
	    return "pong";
	}
}
