package com.example.demo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.modelo.Vehiculos;
import com.example.demo.repositorio.VehiculosRepositorio;

@RestController
@RequestMapping("/Vehiculos")
@CrossOrigin(origins = "http://localhost:4200/")
public class VehiculosControlador {

    @Autowired
    private VehiculosRepositorio vehiculosRepositorio;

    // Guardar un nuevo vehículo
    @PostMapping("/guardar")
    public ResponseEntity<Vehiculos> guardar(@RequestBody Vehiculos vehiculo) {
        try {
            Vehiculos nuevo = vehiculosRepositorio.save(vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Listar todos los vehículos disponibles (estado = 'DISPONIBLE')
    @GetMapping("/disponibles")
    public ResponseEntity<List<Vehiculos>> obtenerVehiculosDisponibles() {
        try {
            List<Vehiculos> disponibles = vehiculosRepositorio.VehiculosDisponibles();
            return ResponseEntity.ok(disponibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Buscar vehículos por placa
    @GetMapping("/buscarPorPlaca")
    public ResponseEntity<List<Vehiculos>> buscarPorPlaca(@RequestParam String placa) {
        try {
            List<Vehiculos> resultado = vehiculosRepositorio.findByPlaca(placa);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Buscar vehículos por marca
    @GetMapping("/buscarPorMarca")
    public ResponseEntity<List<Vehiculos>> buscarPorMarca(@RequestParam String marca) {
        try {
            List<Vehiculos> resultado = vehiculosRepositorio.findByMarca(marca);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Buscar vehículos por tipo
    @GetMapping("/buscarPorTipo")
    public ResponseEntity<List<Vehiculos>> buscarPorTipo(@RequestParam String tipo) {
        try {
            List<Vehiculos> resultado = vehiculosRepositorio.findByTipo(tipo);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Buscar vehículo por ID (id_vehiculo personalizado)
    @GetMapping("/buscarPorId")
    public ResponseEntity<Vehiculos> buscarPorId(@RequestParam int idVehiculo) {
        return vehiculosRepositorio.findByIdVehiculo(idVehiculo)
                .map(vehiculo -> ResponseEntity.ok(vehiculo))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    
    @GetMapping("/tipoVehiculo")
	public ResponseEntity<List<Vehiculos>> tipoV(@RequestParam String tipo){
		 System.out.println("Tipo recibido en backend: " + tipo);
		List<Vehiculos> vehiculo = vehiculosRepositorio.tipo(tipo);
		return ResponseEntity.ok(vehiculo);
	
	}

}
