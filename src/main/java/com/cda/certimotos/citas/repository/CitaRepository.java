package com.cda.certimotos.citas.repository;
import com.cda.certimotos.citas.entity.Cita;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    Optional<Cita> findByIdUsuarioAndFechaAndIdHorario(Long idUsuario, LocalDate fecha, Long idHorario);
}



