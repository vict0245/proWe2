package com.example.demo.controlador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
@CrossOrigin(origins = "http://localhost:4200/")
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
	public ResponseEntity<?> entregarVehiculo(@RequestParam String placa, @RequestHeader("idAdministrador") Long idAdministrador) {
	    
	    Optional<Alquileres> alquilerOpt = repositorioAlquiler.findByVehiculoPlacaAndEstado(placa, "Pendiente");
	    
	    if (alquilerOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Map.of("error", "No hay alquiler para la placa: " + placa));
	    }

	    Alquileres alquiler = alquilerOpt.get();
	    alquiler.setEstado("ENTREGADO");
	    alquiler.getVehiculo().setEstado("ENTREGADO");

	    repositorioAlquiler.save(alquiler);
	    repositorioV.save(alquiler.getVehiculo());

	    Gestion_Alquiler gestion = new Gestion_Alquiler();
	    gestion.setAccion("entregado");
	    gestion.setFechaAccion(LocalDate.now());
	    gestion.setAlquiler(alquiler);
	    gestion.setAdministrador(repositorioA.findById(idAdministrador).orElse(null));

	    repositorioGestion.save(gestion);

	    return ResponseEntity.ok(Map.of(
	        "mensaje", "Vehículo entregado correctamente",
	        "placa", placa,
	        "idAlquiler", alquiler.getIdAlquiler()
	    ));
	}
	
	@PutMapping("/estadoDevuelto")
	public ResponseEntity<?> devolverVehiculo(@RequestParam String placa) {
	    Optional<Alquileres> alquilerRevision = repositorioAlquiler.findByVehiculoPlacaAndEstado(placa, "ENTREGADO");

	    if (alquilerRevision.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    Alquileres alquiler = alquilerRevision.get();

	    LocalDate fechaActual = LocalDate.now();
	    LocalDate fechaInicio = alquiler.getFechaInicio();
	    LocalDate fechaFin = alquiler.getFechaFin();

	    long diasAlquiler = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
	    if (diasAlquiler <= 0) diasAlquiler = 1; // Por si acaso hay fechas inválidas

	    BigDecimal tarifaDiaria = alquiler.getVehiculo().getValorAlquilerDia(); // Asegúrate de que exista este getter
	    BigDecimal valorBase = tarifaDiaria.multiply(BigDecimal.valueOf(diasAlquiler));

	    long retrasoDias = 0;
	    if (fechaActual.isAfter(fechaFin)) {
	        retrasoDias = ChronoUnit.DAYS.between(fechaFin, fechaActual);
	    }

	    BigDecimal valorAdicional = BigDecimal.valueOf(retrasoDias * 10000);
	    BigDecimal nuevoTotal = valorBase.add(valorAdicional);

	    
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

	    Administradores admin = repositorioA.findById(1L).get(); // Temporal
	    gestion.setAdministrador(admin);

	    repositorioGestion.save(gestion);

	    return ResponseEntity.ok(true);
	}
	  
	@PostMapping("/RegistrarAccion")
		ResponseEntity<?> registroAccion(@RequestParam Long idAlquiler, @RequestParam Long idAdministrador, @RequestParam String accion ){
		try {
		Optional<Alquileres> Alquilertengo = repositorioAlquiler.findById(idAlquiler);
		Optional<Administradores> Administradortengo = repositorioA.findById(idAdministrador);
		
		if(Alquilertengo.isPresent() && Administradortengo.isPresent()) {
			LocalDate fechahoy = LocalDate.now();
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

}
