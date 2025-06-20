package com.example.demo.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Gestion_Alquiler;
import com.example.demo.modelo.Vehiculos;

@Repository
public interface AdministradoresRepositorio extends JpaRepository<Administradores,Long> {
	
	public Administradores findByUsuario(String usuario);
	public Administradores findByGestiones(Gestion_Alquiler gestion);
	
	@Query(value="select * from vehiculos where id_vehiculo=: id",nativeQuery=true)
	public Vehiculos estadoVehiculo(@Param ("id") Long id);
	
}
