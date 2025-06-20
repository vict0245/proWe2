package com.example.demo.repositorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Vehiculos;

public interface AlquileresRepositorio extends JpaRepository<Alquileres, Long> {

    // Verifica si un vehículo está en alguno de los estados dados
    boolean existsByVehiculoAndEstadoIn(Vehiculos vehiculo, List<String> estados);

	
//El sistema muestra un catálogo de vehículos
//disponibles, incluyendo detalles como modelo, 
//tipo, precio por día.
	
	@Query(value="SELECT id_vehiculo, modelo, tipo, valor_alquiler_dia FROM vehiculos WHERE estado = 'Disponible'", nativeQuery=true)
	public List<Object[]> mostrarVehi();

	
//El sistema calcula el costo total del alquiler basándose en el vehículo y el período.
	
	@Query(value = "SELECT valor_alquiler_dia FROM vehiculos WHERE id_vehiculo = :id", nativeQuery = true)
	BigDecimal obtenerValorDiario(@Param("id") Long idVehiculo);

// El sistema presenta al cliente un resumen de la solicitud, incluyendo el vehículo, las fechas, el costo.
	
	@Query(value="select id_vehiculo,fecha_inicio,fecha_fin,valor_total from alquileres",nativeQuery=true)
	public List<Object[]> soli();
		

//El sistema verifica la disponibilidad del vehículo para las fechas seleccionadas.
	
	@Query("SELECT a FROM Alquileres a " +
		       "WHERE a.fechaInicio IS NOT NULL AND a.fechaFin IS NOT NULL " +
		       "AND a.fechaInicio <= :fechaFin " +
		       "AND a.fechaFin >= :fechaInicio")
		List<Alquileres> verificarDisponibilidad(@Param("fechaInicio") LocalDate fechaInicio,
		                                         @Param("fechaFin") LocalDate fechaFin);
                            
// El sistema consulta la base de datos para identificar todas las reservas de alquiler cuyo estado indica 
	//que el vehículo está actualmente alquilado o en posesión del cliente.
 	@Query(value = "SELECT v.placa, v.marca, v.modelo, a.id_usuario, a.fecha_inicio, a.fecha_fin, a.estado " +
            "FROM vehiculos v " +
            "INNER JOIN alquileres a ON v.id_vehiculo = a.id_vehiculo " +
            "WHERE a.estado = 'Alquilado'", nativeQuery = true)
	public List<Object[]> ListaAlquii();


	
	

    // Catálogo de vehículos disponibles: modelo, tipo, precio por día
    @Query(value = "SELECT modelo, tipo, valor_alquiler_dia FROM vehiculos WHERE estado = :estado", nativeQuery = true)
    List<Object[]> mostrarVehi(@Param("estado") String estado);

    // Calcula el costo total del alquiler según vehículo y fechas
    @Query(value = "SELECT valor_total FROM alquileres v WHERE v.id_vehiculo = :id AND v.fecha_inicio = :FI AND v.fecha_fin = :FF", nativeQuery = true)
    List<BigDecimal> valorTotal(@Param("id") Long id_vehiculo, @Param("FI") LocalDate fecha_inicio, @Param("FF") LocalDate fecha_fin);

    // Libera un vehículo (marca como disponible)
    @Query(value = "UPDATE vehiculos SET disponible = true WHERE id_vehiculo = :id", nativeQuery = true)
    void liberarVehiculo(@Param("id") Long idVehiculo);

    // Verifica disponibilidad de un vehículo entre fechas específicas
    @Query(value = "SELECT * FROM alquileres WHERE id_vehiculo = :idVehiculo AND estado = 'Alquilado' AND (fecha_inicio <= :fechaFin AND fecha_fin >= :fechaInicio)", nativeQuery = true)
    List<Alquileres> verificarDisponibilidad(@Param("idVehiculo") Long idVehiculo, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    // Lista todas las reservas con estado 'Alquilado'
    @Query(value = "SELECT * FROM alquileres WHERE estado = 'Alquilado'", nativeQuery = true)
    List<Object> ListaAlqui();
}
