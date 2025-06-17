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
	
	@Query(value=" select modelo,tipo,valor_alquiler_dia from vehiculos",nativeQuery=true)
	public List<Object[]> mostrarVehi();
	
//El sistema calcula el costo total del alquiler basándose en el vehículo y el período.
	
	@Query(value="select valor_total from alquileres v where v.id_vehiculo =: id and v.fecha_inicio =: FI and v.fecha_fin =: FF",nativeQuery=true)
	public List<Object> valorTotal(@Param("id")int id_vehiculo,@Param("FI")Date fecha_inicio,@Param("FF")Date fecha_fin);
	
// El sistema presenta al cliente un resumen de la solicitud, incluyendo el vehículo, las fechas, el costo.
	
	@Query(value="select id_vehiculo,fecha_inicio,fecha_fin,valor_total from alquileres",nativeQuery=true)
	public List<Object[]> soli();
			

	
}
