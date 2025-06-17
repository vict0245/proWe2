package com.example.demo.controlador;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> entregarVehiculo(@RequestParam Integer idAlquiler, @RequestParam int idAdministrador) {
	    Optional<Alquileres> alquilerRevision = repositorioAlquiler.findById(idAlquiler);

	    if (alquilerRevision.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alquiler no encontrado");
	    }

	    Alquileres alquiler = alquilerRevision.get();

	    // Cambiar estado del alquiler y del vehículo
	    alquiler.setEstado("EN CURSO");
	    alquiler.getVehiculo().setEstado("ENTREGADO");

	    repositorioAlquiler.save(alquiler);
	    repositorioV.save(alquiler.getVehiculo());

	    // Registrar gestión
	    Gestion_Alquiler gestion = new Gestion_Alquiler();
	    gestion.setAccion("entregado");
	    gestion.setFechaAccion(new Date());
	    gestion.setAlquiler(alquiler);

	    // Aquí debes obtener el administrador autenticado o pasarlo por parámetro
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

	    // Calcular retraso
	    long retrasoHoras = 0;
	    if (fechaActual.after(fechaFin)) {
	        long diferenciaMs = fechaActual.getTime() - fechaFin.getTime();
	        retrasoHoras = TimeUnit.HOURS.convert(diferenciaMs, TimeUnit.MILLISECONDS);
	    }

	    BigDecimal valorAdicional = BigDecimal.valueOf(retrasoHoras * 10000); 
	    BigDecimal nuevoTotal = alquiler.getValorTotal().add(valorAdicional);

	    // Actualizar alquiler
	    alquiler.setFechaEntregaReal(fechaActual);
	    alquiler.setEstado("FINALIZADO");
	    alquiler.setValorAdicional(valorAdicional);
	    alquiler.setValorTotal(nuevoTotal);

	    repositorioAlquiler.save(alquiler);

	    // Cambiar estado del vehículo
	    Vehiculos vehiculo = alquiler.getVehiculo();
	    vehiculo.setEstado("DISPONIBLE");
	    repositorioV.save(vehiculo);

	    // Registrar gestión
	    Gestion_Alquiler gestion = new Gestion_Alquiler();
	    gestion.setAccion("devuelto");
	    gestion.setFechaAccion(fechaActual);
	    gestion.setAlquiler(alquiler);

	    // Aquí también: debes pasar el admin autenticado
	    Administradores admin = repositorioA.findByIdAdministrador(1).get(); // Temporal
	    gestion.setAdministrador(admin);

	    repositorioGestion.save(gestion);

	    return ResponseEntity.ok("Vehículo devuelto correctamente. Total: $" + nuevoTotal);
	}


}
