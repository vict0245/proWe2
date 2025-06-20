package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Vehiculos;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modelo.Vehiculos;

public interface VehiculosRepositorio extends JpaRepository<Vehiculos, Long> {
	
	@Query(value="update vehiculo set estado = :estado where id_vehiculo = :id",nativeQuery=true)
	void estadoDispo(@Param("estado")String estado,@Param("id")Long id_vehiculo);

    // Cambia el estado del vehículo (por ejemplo, de 'Disponible' a 'Alquilado')
    @Query(value = "UPDATE vehiculo SET estado = :estado WHERE id_vehiculo = :id", nativeQuery = true)
    void estadoDispo(@Param("estado") String estado, @Param("id") Long id_vehiculo);

    // Búsqueda por ID del vehículo personalizado (no el ID autogenerado del repositorio)
    Optional<Vehiculos> findByIdVehiculo(int idVehiculo);

    // Búsqueda por atributos del vehículo
    List<Vehiculos> findByPlaca(String placa);
    List<Vehiculos> findByMarca(String marca);
    List<Vehiculos> findByModelo(String modelo);
    List<Vehiculos> findByColor(String color);
    Vehiculos findByEstado(String estado);
    List<Vehiculos> findByValorAlquilerDia(BigDecimal valorAlquilerDia);
    List<Vehiculos> findByTipo(String tipo);

    // Verificación de existencia de una placa
    boolean existsByPlaca(String placa);

    // Obtener vehículos disponibles
    @Query(value = "SELECT * FROM vehiculos WHERE estado = 'DISPONIBLE'", nativeQuery = true)
    List<Vehiculos> VehiculosDisponibles();
}
