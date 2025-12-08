package com.cda.certimotos.citas.repository;
import com.cda.certimotos.citas.entity.Cita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    Optional<Cita> findByUsuarioIdAndFechaAndHorarioId(Long idUsuario, LocalDate fecha, Long idHorario);
    static List<Cita> findByFecha(LocalDate fecha) {
        throw new UnsupportedOperationException("Unimplemented method 'findByFecha'");
    }

}



