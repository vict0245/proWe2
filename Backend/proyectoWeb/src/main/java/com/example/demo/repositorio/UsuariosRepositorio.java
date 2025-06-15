package com.example.demo.repositorio;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Usuarios;

public interface UsuariosRepositorio extends JpaRepository<Usuarios, Integer>{
	
	Optional <Usuarios> findByIdUsuario(int idUsuario);
	public List<Usuarios> findByNombre(String nombre);
	public List<Usuarios> findByFechaExpedicion(LocalDate fechaExpedicion);
	public List<Usuarios> findByCategoria(String categoria);
	public List<Usuarios> findByVigencia(LocalDate vigencia);
	public Usuarios findByEmail(String email);
	public List<Usuarios> findByTelefono(String telefono);

}
