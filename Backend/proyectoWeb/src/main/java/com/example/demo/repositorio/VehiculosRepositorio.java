package com.example.demo.repositorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Usuarios;
import com.example.demo.modelo.Vehiculos;

public interface VehiculosRepositorio extends JpaRepository<Vehiculos, Integer> {
	
		
	@Query(value="update vehiculo set estado = :estado where id_vehiculo = :id",nativeQuery=true)
	void estadoDispo(@Param("estado")String estado,@Param("id")Long id_vehiculo);

	Optional <Vehiculos> findByIdVehiculo(int idVehiculo);
	public List<Vehiculos> findByPlaca(String placa);
	public List<Vehiculos> findByMarca(String marca);
	public List<Vehiculos> findByModelo(String modelo);
	public List<Vehiculos> findByColor(String color);
	public Vehiculos findByEstado(String estado);
	public List<Vehiculos> findByValorAlquilerDia(BigDecimal valorAlquilerDia);
	public List<Vehiculos> findByTipo(String tipo);
	
	boolean existsByPlaca(String placa);
	
	@Query(value="select * from Vehiculos where estado = 'DISPONIBLE'",nativeQuery=true)
	List<Vehiculos> VehiculosDisponibles();
}
