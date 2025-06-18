package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Vehiculos;

public interface VehiculosRepositorio extends JpaRepository<Vehiculos,Long> {
	
	@Query(value="update vehiculo set estado = :estado where id_vehiculo = :id",nativeQuery=true)
	void estadoDispo(@Param("estado")String estado,@Param("id")int id_vehiculo);

}
