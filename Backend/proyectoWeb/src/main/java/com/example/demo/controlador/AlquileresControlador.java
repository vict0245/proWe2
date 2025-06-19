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

import com.example.demo.modelo.Alquileres;
import com.example.demo.repositorio.AlquileresRepositorio;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/alquiler")
@CrossOrigin(origins = "http://localhost:4200/")
public class AlquileresControlador {
	
	@Autowired
	private AlquileresRepositorio repositorioAlquiler;
	
	@Autowired
	private VehiculosRepositorio RepoVehiculos;
	
	@PostMapping("/detallesVehiculo")
	public ResponseEntity<?> detalle(@RequestParam String estado){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.mostrarVehi(estado);
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
	@PostMapping("Cancelar")
	public ResponseEntity<?> Cancelar(@RequestParam Long id,@RequestParam String estado){
		this.repositorioAlquiler.mostrarVehi(estado);
		  Alquileres alquiler = repositorioAlquiler.findById(id).orElse(null);
	    if (repositorioAlquiler.existsById(id)) {
	        try {
	        	 alquiler.setEstado("Cancelada");
	             repositorioAlquiler.save(alquiler);
	             
	             Long idVehiculo = alquiler.getVehiculo().getIdVehiculo();
	             repositorioAlquiler.liberarVehiculo(idVehiculo);
	            return ResponseEntity.ok("Reserva cancelada y vehículo liberado.");

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error interno del servidor al eliminar el aquiler: " + e.getMessage());
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("No se encontró un alquiler con ID " + id + " para eliminar.");
	    }
	}
	
	@PostMapping("/verificarDisponibilidad")
	public ResponseEntity<?> verificarDisponibilidad(@RequestParam int idVehiculo,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {                                                                              
	    try {
	        List<Alquileres> reservas = repositorioAlquiler.verificarDisponibilidad(idVehiculo, fechaInicio, fechaFin);

	        if (reservas.isEmpty()) {
	            return ResponseEntity.ok("Disponible");
	        } else {
	            return ResponseEntity.ok("No disponible. Ya hay una reserva para esas fechas.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error al verificar disponibilidad: " + e.getMessage());
	    }
	}

	
	@PostMapping("/cambioDisponibilidad")
	public ResponseEntity<?> estadoDispo(@RequestParam Long id){
		
		Alquileres alquiler = repositorioAlquiler.findById(id).orElse(null);
		if(repositorioAlquiler.existsById(id)) {
			try {
				alquiler.setEstado("Alquilado");
	            repositorioAlquiler.save(alquiler);
	            
	            Long idVehiculo = alquiler.getVehiculo().getIdVehiculo();
	            RepoVehiculos.estadoDispo("Alquilado", idVehiculo);
	            return ResponseEntity.ok("Reserva confirmada y vehículo marcado como alquilado.");
  
			}catch (Exception en) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error interno del servidor al actualizar el alquiler: " + en.getMessage());
			}
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un alquiler con ID " + id + " para actualizar.");
}
		
	}
	
	
	
	@PostMapping("/VehiculoAlquilado")
	public ResponseEntity<?> ListaVehiAlqui(){
		try {
	        List<Object> lista = repositorioAlquiler.ListaAlqui();
	        return ResponseEntity.ok(lista);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}
	

}
