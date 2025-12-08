package com.cda.certimotos.citas.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cda.certimotos.citas.entity.Credencial;
import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.repository.CredencialRepository;
import com.cda.certimotos.citas.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final CredencialRepository credencialRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            CredencialRepository credencialRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.credencialRepository = credencialRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String correo, String rawPassword) {

        // Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        // Validar que sea ADMIN → rol.id = 1
        if (usuario.getRol() == null || usuario.getRol().getId() != 1L) {
            throw new RuntimeException("Solo administradores pueden iniciar sesión");
        }

        // Buscar credencial por fk = id_usuario
        Credencial cred = credencialRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("El administrador no tiene credencial registrada"));

        // Validar contraseña
        if (!passwordEncoder.matches(rawPassword, cred.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }
}
