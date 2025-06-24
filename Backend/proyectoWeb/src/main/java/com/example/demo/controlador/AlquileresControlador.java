package com.example.demo.controlador;

import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Usuarios;
import com.example.demo.modelo.Vehiculos;
import com.example.demo.modelo.credenciales;
import com.example.demo.repositorio.AlquileresRepositorio;
import com.example.demo.repositorio.UsuariosRepositorio;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/alquiler")
@CrossOrigin(origins = "http://localhost:4200/")
public class AlquileresControlador {

	@Autowired
	private AlquileresRepositorio repositorioAlquiler;

	@Autowired
	private VehiculosRepositorio repoVehiculos;
	
	@Autowired
	private UsuariosRepositorio usuarioRepository;

<<<<<<< HEAD
    // Mostrar detalles de vehículos disponibles


=======
	// Mostrar detalles de vehículos disponibles
	@PostMapping("/detallesVehiculo")
	public ResponseEntity<?> detalle(@RequestBody String estado) {
		try {
			List<Object[]> vehiculos = repositorioAlquiler.mostrarVehi(estado);
			return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
>>>>>>> fccd9131ffed2e3465032b8e89f818d483c18417

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

<<<<<<< HEAD
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
=======
	// Cancelar una reserva y liberar el vehículo
	@PostMapping("/Cancelar")
	public ResponseEntity<?> Cancelar(@RequestBody Long id) {

		if (!repositorioAlquiler.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("error", "No se encontró un alquiler con ID " + id + " para eliminar."));
		}

		try {
			Alquileres alquiler = repositorioAlquiler.findById(id).orElse(null);

			if (alquiler == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Alquiler no encontrado."));
			}

			if (alquiler.getVehiculo() == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("error", "El alquiler no tiene vehículo asociado."));
			}

			Long idVehiculo = alquiler.getVehiculo().getIdVehiculo();

			Vehiculos vehiculo = repoVehiculos.findById(idVehiculo).orElse(null);
			if (vehiculo == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("error", "Vehículo no encontrado con ID: " + idVehiculo));
			}

			alquiler.setEstado("Cancelada");
			repositorioAlquiler.save(alquiler);
>>>>>>> fccd9131ffed2e3465032b8e89f818d483c18417

			vehiculo.setEstado("Disponible");
			repoVehiculos.save(vehiculo);

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
	public Object guardarReserva(@RequestBody credenciales si) {
		Vehiculos vehi = si.getVehi();
		LocalDate inicio = si.getFechaInicio();
		LocalDate fin = si.getFechaFin();
		BigDecimal valorTotal = (BigDecimal) si.getValorTotal();
		String identificacion = si.getIdentificacion();
	    
	    try {
	        // 1. Validaciones básicas
	        if (inicio == null || fin == null || inicio.isAfter(fin)) {
	            return ResponseEntity.badRequest().body("Fechas inválidas. La fecha de inicio debe ser anterior a la fecha fin");
	        }

	        if (valorTotal == null || valorTotal.floatValue() <= 0) {
	            return ResponseEntity.badRequest().body("El valor total debe ser mayor que cero");
	        }
	        
	        // 2. Buscar usuario y vehículo
	        Usuarios usuarioOpt = this.usuarioRepository.findByIdentificacion(identificacion);
	        Optional<Vehiculos> vehiculoOpt = this.repoVehiculos.findById(vehi.getIdVehiculo());

	        if (usuarioOpt==null) {
	            return ResponseEntity.badRequest().body("Usuario no encontrado");
	        }

	        Vehiculos vehiculo = vehiculoOpt.get();

	        // 3. Validar disponibilidad del vehículo
	        if (!"Disponible".equals(vehiculo.getEstado())) {
	            return ResponseEntity.badRequest().body("El vehículo no está disponible para alquiler");
	        }

	        // 4. Crear y guardar la reserva
	        Alquileres nuevaReserva = new Alquileres();
	        nuevaReserva.setUsuario(usuarioOpt);
	        nuevaReserva.setVehiculo(vehiculo);
	        nuevaReserva.setFechaInicio(inicio);
	        nuevaReserva.setFechaFin(fin);
	        nuevaReserva.setEstado("Pendiente");
	        nuevaReserva.setValorTotal(valorTotal);
	        nuevaReserva.setFechaEntregaReal(null);

	        // 5. Actualizar estado del vehículo
	        vehiculo.setEstado("Alquilado");

	        // 6. Persistir los cambios
	        this.repositorioAlquiler.save(nuevaReserva);
	        this.repoVehiculos.save(vehiculo);

	        return true;

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al procesar la reserva: " + e.getMessage());
	    }
	}

	@GetMapping("/detallesVehiculo")
	public ResponseEntity<?> detalle() {
		try {
			List<Object[]> vehiImg = new ArrayList<>();
			List<Object[]> vehiculos = repositorioAlquiler.mostrarVehi();
			for (Object[] so : vehiculos) {
				Object[] NO = new Object[6];
				NO[0] = (((Number) so[0]).longValue());
				NO[1] = ((String) so[1]);
				NO[2] = ((String) so[2]);
				NO[3] = ((Number) so[3]).doubleValue();
				NO[5] = (String) so[5];

				// Convertir el blob (obj[4]) a Base64
				if (so[4] != null) {
					byte[] imagenBytes = (byte[]) so[4];
					String base64 = Base64.getEncoder().encodeToString(imagenBytes);
					NO[4] = base64;
				}
				vehiImg.add(NO);
			}
			return ResponseEntity.ok(vehiImg);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}

	@GetMapping("/listarVehiculosAlquilados")
	public ResponseEntity<?> listarVehiculosAlquilados() {
		try {
			List<Object[]> vehiculos = repositorioAlquiler.ListaAlquii();
			return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}

	@PostMapping("/listarVehiculosAlquiladosU")
	public ResponseEntity<?> listarVehiculosAlquiladosU(@RequestBody String identU) {
		try {
			List<Object[]> vehiculos = repositorioAlquiler.ListaAlquiiU(identU);
			return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}

	@PostMapping("/valorTotal")
	public Object calcularValorTotal(@RequestBody credenciales c) {
		Long id = c.getId();
		LocalDate fechaInicio = c.getFechaInicio();
		LocalDate fechaFin = c.getFechaFin();
		try {
			BigDecimal valorDiario = repositorioAlquiler.obtenerValorDiario(id);

			long diferenciaDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

			BigDecimal valorTotal = valorDiario.multiply(BigDecimal.valueOf(diferenciaDias));

			return ResponseEntity.ok(valorTotal);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
<<<<<<< HEAD
	
	@GetMapping("/vehiculosPendientes")
	public ResponseEntity<?> vehiculos(){
		try {
	        List<Object[]> vehiculos = repositorioAlquiler.ListaPendientes();
	        return ResponseEntity.ok(vehiculos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
			
		}
=======

	@PostMapping("/verificarDisponibilidad1")
	public ResponseEntity<?> verificarDisponibilidad(@RequestBody credenciales c) {
		LocalDate fechaInicio = c.getFechaInicio();
		LocalDate fechaFin = c.getFechaFin();
		try {
			List<Alquileres> reservas = repositorioAlquiler.verificarDisponibilidad(fechaInicio, fechaFin);

			if (reservas.isEmpty()) {
				return ResponseEntity.ok("Disponible");
			} else {
				return ResponseEntity.ok("No disponible. Ya hay una reserva para esas fechas.");
			}
		} catch (Exception e) {
			e.printStackTrace(); // MOSTRAR ERROR EN CONSOLA
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al verificar disponibilidad: " + e.getMessage());
		}
	}

	@GetMapping("/vehiculosPendientes")
	public ResponseEntity<?> vehiculos() {
		try {
			List<Object[]> vehiImg = new ArrayList<>();
			List<Object[]> vehiculos = repositorioAlquiler.ListaPendientes();
			for (Object[] so : vehiculos) {
				Object[] NO = new Object[6];
				NO[0] = (((Number) so[0]).longValue());
				NO[1] = ((String) so[1]);
				NO[2] = ((String) so[2]);
				NO[3] = ((Number) so[3]).doubleValue();
				NO[5] = (String) so[5];

				if (so[4] != null) {
					byte[] imagenBytes = (byte[]) so[4];
					String base64 = Base64.getEncoder().encodeToString(imagenBytes);
					NO[4] = base64;
				}

				vehiImg.add(NO);
			}
			return ResponseEntity.ok(vehiImg);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}
	
	@PostMapping("/obtenerReserva")
	public Object[] obtenerAlqui(@RequestBody Object[] reserva){
		String fechaInicio=(String)reserva[0];
	    String fechaFin=(String)reserva[1];
	    int vehiculo=(int)reserva[3];
	    String usuario=(String)reserva[4];
	    	
	    System.out.println(fechaInicio+" "+fechaFin+" "+vehiculo+" "+usuario);
		Object[] si =this.repositorioAlquiler.traerAlqui(fechaInicio,fechaFin,vehiculo,usuario);
		return si;
>>>>>>> fccd9131ffed2e3465032b8e89f818d483c18417
	}

}
