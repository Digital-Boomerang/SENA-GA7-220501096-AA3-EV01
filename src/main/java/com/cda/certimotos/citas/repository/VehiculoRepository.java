package com.cda.certimotos.citas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cda.certimotos.citas.entity.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByIdUsuario(Long idUsuario);
}
