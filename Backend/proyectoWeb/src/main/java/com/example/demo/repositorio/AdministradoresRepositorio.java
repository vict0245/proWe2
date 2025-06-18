package com.example.demo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Administradores;
import com.example.demo.modelo.Usuarios;

@Repository
public interface AdministradoresRepositorio extends JpaRepository<Administradores, Integer> {
	
	Optional <Administradores> findByIdAdministrador(Integer idAdministrador);

	@Query(value="select password from administradores where id_administrador=:id",nativeQuery=true)
	public String buscarAdministrador(@Param("idAdministrador")Integer idAdministrador);
}
