package com.cda.certimotos.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cda.certimotos.citas.entity.Credencial;
import com.cda.certimotos.citas.entity.Rol;
import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.repository.CredencialRepository;
import com.cda.certimotos.citas.repository.RolRepository;
import com.cda.certimotos.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final CredencialRepository credRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(
            UsuarioRepository usuarioRepo,
            RolRepository rolRepo,
            CredencialRepository credRepo) {

        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.credRepo = credRepo;
    }

    /* =======================================================
       CREAR CLIENTE AUTOMÁTICO (AGENDAMIENTO)
       ======================================================= */
    public Usuario crearClienteSiNoExiste(Usuario datos) {

        if (datos.getDocumento() == null) {
            throw new RuntimeException("Documento obligatorio");
        }

        Optional<Usuario> existente = usuarioRepo.findByDocumento(datos.getDocumento());
        if (existente.isPresent()) {
            return existente.get();
        }

        Rol rolCliente = rolRepo.findById(2L)
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no existe"));

        datos.setRol(rolCliente);
        return usuarioRepo.save(datos);
    }

    /* =======================================================
       CREAR USUARIO (SIN PASSWORD)
       ======================================================= */
    public Usuario crearUsuario(Usuario usuario) {

        if (usuario.getRol() == null) {
            throw new RuntimeException("Debe indicar un rol válido");
        }

        validarDuplicadosCreacion(usuario);

        Rol rol = rolRepo.findById(usuario.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no válido"));

        usuario.setRol(rol);
        return usuarioRepo.save(usuario);
    }

    /* =======================================================
       REGISTRO USUARIO + PASSWORD
       ======================================================= */
    public Usuario registrarUsuarioConPassword(Usuario usuario, String password) {

        if (usuario.getRol() == null) {
            throw new RuntimeException("Debe indicar un rol válido");
        }

        validarDuplicadosCreacion(usuario);

        Rol rol = rolRepo.findById(usuario.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no válido"));

        usuario.setRol(rol);

        Usuario guardado = usuarioRepo.save(usuario);

        Credencial cred = new Credencial();
        cred.setUsuario(guardado);
        cred.setContrasena(encoder.encode(password));
        credRepo.save(cred);

        return guardado;
    }

    /* =======================================================
       VALIDACIONES SOLO PARA CREACIÓN
       ======================================================= */
    private void validarDuplicadosCreacion(Usuario usuario) {

        if (usuario.getDocumento() != null &&
                usuarioRepo.existsByDocumento(usuario.getDocumento())) {
            throw new RuntimeException("El documento ya está registrado");
        }

        if (usuario.getCorreo() != null &&
                usuarioRepo.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
    }

    /* =======================================================
       LECTURAS
       ======================================================= */
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepo.findById(id);
    }

    /* =======================================================
       ACTUALIZAR USUARIO (CORREGIDO)
       ======================================================= */
    public Usuario actualizarUsuario(Long id, Usuario datos) {

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        /* ---------- DOCUMENTO ---------- */
        if (datos.getDocumento() != null &&
            !datos.getDocumento().equals(usuario.getDocumento())) {

            Optional<Usuario> otro = usuarioRepo.findByDocumento(datos.getDocumento());
            if (otro.isPresent() && !otro.get().getId().equals(id)) {
                throw new RuntimeException("El documento ya está registrado por otro usuario");
            }

            usuario.setDocumento(datos.getDocumento());
        }

        /* ---------- CORREO ---------- */
        if (datos.getCorreo() != null &&
            !datos.getCorreo().equals(usuario.getCorreo())) {

            Optional<Usuario> otro = usuarioRepo.findByCorreo(datos.getCorreo());
            if (otro.isPresent() && !otro.get().getId().equals(id)) {
                throw new RuntimeException("El correo ya está registrado por otro usuario");
            }

            usuario.setCorreo(datos.getCorreo());
        }

        /* ---------- CAMPOS SIMPLES ---------- */
        if (datos.getNombre() != null) usuario.setNombre(datos.getNombre());
        if (datos.getApellido1() != null) usuario.setApellido1(datos.getApellido1());
        if (datos.getApellido2() != null) usuario.setApellido2(datos.getApellido2());
        if (datos.getTelefono() != null) usuario.setTelefono(datos.getTelefono());
        if (datos.getDireccion() != null) usuario.setDireccion(datos.getDireccion());

        /* ---------- ROL ---------- */
        if (datos.getRol() != null) {
            Rol rol = rolRepo.findById(datos.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no válido"));
            usuario.setRol(rol);
        }

        return usuarioRepo.save(usuario);
    }

    /* =======================================================
       ELIMINAR
       ======================================================= */
    public boolean eliminarUsuario(Long id) {
        if (!usuarioRepo.existsById(id)) return false;
        usuarioRepo.deleteById(id);
        return true;
    }

    /* =======================================================
       RECUPERACIÓN PASSWORD (SIMULADO)
       ======================================================= */
    public void enviarEnlaceRecuperacion(Usuario usuario) {
        System.out.println("Enlace enviado a: " + usuario.getCorreo());
    }

    /* =======================================================
       ADMINISTRADORES
       ======================================================= */
    public List<Usuario> listarAdministradores() {
        return usuarioRepo.findByRolId(1L);
    }
}
