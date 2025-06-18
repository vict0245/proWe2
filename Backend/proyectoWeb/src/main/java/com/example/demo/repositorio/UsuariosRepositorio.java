package com.example.demo.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Usuarios;

public interface UsuariosRepositorio extends JpaRepository<Usuarios, Long>{

	public List<Usuarios> findByNombre(String nombre);
	public List<Usuarios> findByFechaExpedicion(LocalDate fechaExpedicion);
	public List<Usuarios> findByCategoria(String categoria);
	public List<Usuarios> findByVigencia(LocalDate vigencia);
	public List<Usuarios> findByEmail(String email);
	public List<Usuarios> findByTelefono(String telefono);
	
}
