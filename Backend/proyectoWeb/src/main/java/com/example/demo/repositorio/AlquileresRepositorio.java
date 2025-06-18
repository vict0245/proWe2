package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Alquileres;
import com.example.demo.modelo.Vehiculos;

public interface AlquileresRepositorio extends JpaRepository<Alquileres, Integer>{
	
	 boolean existsByVehiculoAndEstadoIn(Vehiculos vehiculo, List<String> estados);
	 
	 
}
