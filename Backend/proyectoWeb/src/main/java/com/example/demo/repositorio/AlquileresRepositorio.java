package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;

import com.example.demo.modelo.Alquileres;

public interface AlquileresRepositorio extends JpaRepository<Alquileres,Long>{
	
//El sistema muestra un catálogo de vehículos
//disponibles, incluyendo detalles como modelo, 
//tipo, precio por día.
	
	@Query(value=" select modelo,tipo,valor_alquiler_dia from vehiculos where estado = 'Disponible' ",nativeQuery=true)
	public List<Object[]> mostrarVehi();
	
//El sistema calcula el costo total del alquiler basándose en el vehículo y el período.
	
	@Query(value="select valor_total from alquileres v where v.id_vehiculo =: id and v.fecha_inicio =: FI and v.fecha_fin =: FF",nativeQuery=true)
	public List<Object> valorTotal(@Param("id")int id_vehiculo,@Param("FI")Date fecha_inicio,@Param("FF")Date fecha_fin);
	
// El sistema presenta al cliente un resumen de la solicitud, incluyendo el vehículo, las fechas, el costo.
	
	@Query(value="select id_vehiculo,fecha_inicio,fecha_fin,valor_total from alquileres",nativeQuery=true)
	public List<Object[]> soli();
	
//El sistema actualiza el estado de la reserva a "Cancelada" en la base de datos.
	
	@Query(value = "UPDATE vehiculos SET disponible = true WHERE id_vehiculo = :id", nativeQuery = true)
	void liberarVehiculo(@Param("id") int idVehiculo);

//El sistema verifica la disponibilidad del vehículo para las fechas seleccionadas.
	
	@Query(value = "SELECT * FROM alquileres WHERE id_vehiculo = :idVehiculo AND estado = 'Alquilado' AND (" +
            "fecha_inicio <= :fechaFin AND fecha_fin >= :fechaInicio)", nativeQuery = true)
List<Alquileres> verificarDisponibilidad(@Param("idVehiculo") int idVehiculo, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
                                     
                                     
// El sistema consulta la base de datos para identificar todas las reservas de alquiler cuyo estado indica 
	//que el vehículo está actualmente alquilado o en posesión del cliente.
	
	@Query(value = "select * from alquileres where estado = 'Alquilado'",nativeQuery= true)
	public List<Object> ListaAlqui();
	
	
	

	
}
