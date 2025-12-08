package com.cda.certimotos.citas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cda.certimotos.citas.entity.Credencial;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {

    // Buscar la credencial del usuario por su id (FK)
    Optional<Credencial> findByUsuarioId(Long idUsuario);
}

