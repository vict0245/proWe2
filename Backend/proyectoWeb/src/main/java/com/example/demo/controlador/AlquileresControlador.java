package com.example.demo.controlador;

import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Vehiculos;
import com.example.demo.modelo.credenciales;
import com.example.demo.repositorio.AlquileresRepositorio;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/alquiler")
@CrossOrigin(origins = "http://localhost:4200/")
public class AlquileresControlador {

    @Autowired
    private AlquileresRepositorio repositorioAlquiler;

    @Autowired
    private VehiculosRepositorio repoVehiculos;

    // Mostrar detalles de vehículos disponibles



    // Calcular valor total del alquiler
    @PostMapping("/valorTotalAlquiler")
    public ResponseEntity<?> Vta(@RequestParam credenciales c) {
    	Long id = c.getId();
        LocalDate FI = c.getFechaInicio();
        LocalDate FF = c.getFechaFin();
        try {
            List<BigDecimal> valor = repositorioAlquiler.valorTotal(id, FI, FF);
            return ResponseEntity.ok(valor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resumen de la solicitud de alquiler
    @PostMapping("/resumenSolicitud")
    public ResponseEntity<?> Resumen() {
        try {
            List<Object[]> resuSoli = repositorioAlquiler.soli();
            return ResponseEntity.ok(resuSoli);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Cancelar una reserva y liberar el vehículo
    @PostMapping("/Cancelar")
    public ResponseEntity<?> Cancelar(@RequestParam Long id) {

        System.out.println("🟡 ID recibido para cancelar: " + id);

        if (!repositorioAlquiler.existsById(id)) {
            System.out.println("❌ No se encontró un alquiler con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No se encontró un alquiler con ID " + id + " para eliminar."));
        }

        try {
            Alquileres alquiler = repositorioAlquiler.findById(id).orElse(null);

            if (alquiler == null) {
                System.out.println("❌ Alquiler con ID " + id + " es null.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Alquiler no encontrado."));
            }

            System.out.println("✅ Alquiler encontrado: " + alquiler);

            if (alquiler.getVehiculo() == null) {
                System.out.println("❌ El alquiler no tiene vehículo asociado.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "El alquiler no tiene vehículo asociado."));
            }

            Long idVehiculo = alquiler.getVehiculo().getIdVehiculo();
            System.out.println("🔁 ID del vehículo asociado: " + idVehiculo);

            Vehiculos vehiculo = repoVehiculos.findById(idVehiculo).orElse(null);
            if (vehiculo == null) {
                System.out.println("❌ Vehículo no encontrado.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Vehículo no encontrado con ID: " + idVehiculo));
            }

            alquiler.setEstado("Cancelada");
            repositorioAlquiler.save(alquiler);
            System.out.println("☑️ Estado del alquiler actualizado a 'Cancelada'.");

            vehiculo.setEstado("Disponible");
            repoVehiculos.save(vehiculo);
            System.out.println("☑️ Vehículo liberado (estado = 'Disponible').");

            return ResponseEntity.ok(Map.of("mensaje", "Reserva cancelada y vehículo liberado."));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor al cancelar el alquiler: " + e.getMessage()));
        }
    }


    // Verificar disponibilidad de un vehículo entre fechas
    @PostMapping("/verificarDisponibilidadVehiculo")
    public Object verificarDisponibilidadd(@RequestBody credenciales c) {
        Long idVehiculo = c.getId();
        LocalDate fechaInicio = c.getFechaInicio();
        LocalDate fechaFin = c.getFechaFin();

        List<Alquileres> reservas = repositorioAlquiler.verificarDisponibilidad2(idVehiculo, fechaInicio, fechaFin);

        if (reservas != null && !reservas.isEmpty()) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }


    // Confirmar estado de alquiler y marcar vehículo como "Alquilado"
    @PostMapping("/cambioDisponibilidad")
    public ResponseEntity<?> estadoDispo(@RequestBody Long id) {
        if (!repositorioAlquiler.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un alquiler con ID " + id + " para actualizar.");
        }

        try {
            Alquileres alquiler = repositorioAlquiler.findById(id).orElse(null);
            if (alquiler == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alquiler no encontrado");
            }

            alquiler.setEstado("Alquilado");
            repositorioAlquiler.save(alquiler);

            Long idVehiculo = alquiler.getVehiculo().getIdVehiculo();
            repoVehiculos.estadoDispo("Alquilado", idVehiculo);

            return ResponseEntity.ok("Reserva confirmada y vehículo marcado como alquilado.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor al actualizar el alquiler: " + e.getMessage());
        }
    }

    // Listar vehículos actualmente alquilados
    @PostMapping("/VehiculoAlquilado")
    public ResponseEntity<?> ListaVehiAlqui() {
        try {
            List<Object> lista = repositorioAlquiler.ListaAlqui();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
		
	@PostMapping("/guardarReserva")
	public ResponseEntity<?> guardarReserva(@RequestBody Alquileres reserva) {
	    try {
	        
	        if (reserva.getVehiculo() == null || reserva.getVehiculo().getIdVehiculo() == null) {
	            return ResponseEntity.badRequest().body("Falta el ID del vehículo.");
	        }
	        
	        Long idVehi = reserva.getVehiculo().getIdVehiculo();
	        Vehiculos vehiculoDB = repoVehiculos.findById(idVehi).orElse(null);

	        if (vehiculoDB == null) {
	            return ResponseEntity.badRequest().body("Vehículo no encontrado.");
	        }

	        // Asociar el vehículo real a la reserva
	        reserva.setVehiculo(vehiculoDB);
	        reserva.setEstado("Pendiente");
	        vehiculoDB.setEstado("Alquilado");
	        repositorioAlquiler.save(reserva);
	        repoVehiculos.save(vehiculoDB);
	        

	        return ResponseEntity.ok("Reserva guardada exitosamente.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al guardar la reserva: " + e.getMessage());
	    }
	}



	
	@GetMapping("/detallesVehiculo")
	public ResponseEntity<?> detalle(){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.mostrarVehi();
	        return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}
	
	@GetMapping("/listarVehiculosAlquilados")
	public ResponseEntity<?> listarVehiculosAlquilados(){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.ListaAlquii();
	        return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}
	
	@PostMapping("/valorTotal")
	public Object calcularValorTotal(@RequestBody credenciales c) {
		Long id = c.getId();
        LocalDate fechaInicio = c.getFechaInicio();
        LocalDate fechaFin = c.getFechaFin();
        System.out.println("id:"+id+"\nFI:"+fechaInicio+"\nFF:"+fechaFin);
	    try {
	        BigDecimal valorDiario = repositorioAlquiler.obtenerValorDiario(id);

	        long diferenciaDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

	        BigDecimal valorTotal = valorDiario.multiply(BigDecimal.valueOf(diferenciaDias));

	        return ResponseEntity.ok(valorTotal);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(e.getMessage());
	    }
	}
	
	@GetMapping("/vehiculosPendientes")
	public ResponseEntity<?> vehiculos(){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.ListaPendientes();
	        return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
	}

}
