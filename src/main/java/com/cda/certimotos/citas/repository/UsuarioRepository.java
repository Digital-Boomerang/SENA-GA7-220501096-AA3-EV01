package com.cda.certimotos.citas.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cda.certimotos.citas.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por documento
    Optional<Usuario> findByDocumento(String documento);

    // Buscar por correo
    Optional<Usuario> findByCorreo(String correo);

    // Validaciones opcionales para evitar duplicados
    boolean existsByDocumento(String documento);

    boolean existsByCorreo(String correo);
}
