package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.modelo.Administradores;

@Repository
public interface AdministradoresRepositorio extends JpaRepository<Administradores,Long> {
	
	
	@Query(value="select password from administradores where id_administrador=:id",nativeQuery=true)
	public String buscarAdministrador(@Param("id")int id);
}
