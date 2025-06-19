package com.example.demo.repositorio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Vehiculos;

public interface AlquileresRepositorio extends JpaRepository<Alquileres, Long> {

    // Verifica si un vehículo está en alguno de los estados dados
    boolean existsByVehiculoAndEstadoIn(Vehiculos vehiculo, List<String> estados);

    // Catálogo de vehículos disponibles: modelo, tipo, precio por día
    @Query(value = "SELECT modelo, tipo, valor_alquiler_dia FROM vehiculos WHERE estado = :estado", nativeQuery = true)
    List<Object[]> mostrarVehi(@Param("estado") String estado);

    // Calcula el costo total del alquiler según vehículo y fechas
    @Query(value = "SELECT valor_total FROM alquileres v WHERE v.id_vehiculo = :id AND v.fecha_inicio = :FI AND v.fecha_fin = :FF", nativeQuery = true)
    List<BigDecimal> valorTotal(@Param("id") int id_vehiculo, @Param("FI") Date fecha_inicio, @Param("FF") Date fecha_fin);

    // Muestra resumen de solicitud: vehículo, fechas y valor total
    @Query(value = "SELECT id_vehiculo, fecha_inicio, fecha_fin, valor_total FROM alquileres", nativeQuery = true)
    List<Object[]> soli();

    // Libera un vehículo (marca como disponible)
    @Query(value = "UPDATE vehiculos SET disponible = true WHERE id_vehiculo = :id", nativeQuery = true)
    void liberarVehiculo(@Param("id") Long idVehiculo);

    // Verifica disponibilidad de un vehículo entre fechas específicas
    @Query(value = "SELECT * FROM alquileres WHERE id_vehiculo = :idVehiculo AND estado = 'Alquilado' AND (fecha_inicio <= :fechaFin AND fecha_fin >= :fechaInicio)", nativeQuery = true)
    List<Alquileres> verificarDisponibilidad(@Param("idVehiculo") int idVehiculo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    // Lista todas las reservas con estado 'Alquilado'
    @Query(value = "SELECT * FROM alquileres WHERE estado = 'Alquilado'", nativeQuery = true)
    List<Object> ListaAlqui();
}
