package com.example.demo.repositorio;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 8469a7720a612051ea84f9530c212a8eb13aa507

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Usuarios;

public interface UsuariosRepositorio extends JpaRepository<Usuarios, Integer>{
	
<<<<<<< HEAD
	Optional <Usuarios> findByIdUsuario(int idUsuario);
=======
	public Usuarios findByIdUsuario(int idUsuario);
>>>>>>> 8469a7720a612051ea84f9530c212a8eb13aa507
	public List<Usuarios> findByNombre(String nombre);
	public List<Usuarios> findByFechaExpedicion(LocalDate fechaExpedicion);
	public List<Usuarios> findByCategoria(String categoria);
	public List<Usuarios> findByVigencia(LocalDate vigencia);
	public Usuarios findByEmail(String email);
	public List<Usuarios> findByTelefono(String telefono);

}
