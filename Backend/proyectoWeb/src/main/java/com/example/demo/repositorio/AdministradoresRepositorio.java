package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Gestion_Alquiler;

@Repository
public interface AdministradoresRepositorio extends JpaRepository<Administradores,Long> {
	
	public List<Administradores> findByNombre(String nombre);
	public List<Administradores> findByTelefono(String telefono);
	public Administradores findByGestiones(Gestion_Alquiler gestion);
	
	
}
