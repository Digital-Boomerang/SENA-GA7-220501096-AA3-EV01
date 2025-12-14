package com.cda.certimotos.citas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cda.certimotos.citas.entity.Rol;
import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.repository.UsuarioRepository;
import com.cda.certimotos.citas.services.UsuarioService;
import com.cda.certimotos.citas.dto.RecuperarPasswordRequest;
import com.cda.certimotos.citas.dto.RegistroAdminRequest;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    // ============================================================
    // CREAR USUARIO (ADMIN O CREACIÓN MANUAL)
    // ============================================================
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario u) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.crearUsuario(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear usuario: " + e.getMessage());
        }
    }

    // ============================================================
    // REGISTRO AUTOMÁTICO (CLIENTES DESDE AGENDAMIENTO)
    // ============================================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario u) {
        try {
            return ResponseEntity.ok(service.crearClienteSiNoExiste(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en registro: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR USUARIOS
    // ============================================================
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    // ============================================================
    // OBTENER POR ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {

        Optional<Usuario> usuarioOpt = service.buscarPorId(id);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        return ResponseEntity.ok(usuarioOpt.get());
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(service.actualizarUsuario(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar usuario: " + e.getMessage());
        }
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.eliminarUsuario(id)) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    // ============================================================
    // RECUPERAR CONTRASEÑA
    // ============================================================
    @PostMapping("/recuperar-password")
    public ResponseEntity<?> recuperarPassword(@RequestBody RecuperarPasswordRequest request) {

        if (request.getCorreo() == null || request.getCorreo().isBlank()) {
            return ResponseEntity.badRequest().body("El correo es obligatorio");
        }

        Optional<Usuario> usuario = usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Correo no registrado");
        }

        try {
            service.enviarEnlaceRecuperacion(usuario.get());
            return ResponseEntity.ok("Correo de recuperación enviado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        }
    }

    @PostMapping("/registrar-admin")
    public ResponseEntity<?> registrarAdmin(@RequestBody RegistroAdminRequest req) {
        try {
            // Crear objeto usuario ADMIN
            Usuario u = new Usuario();
            u.setNombre(req.getNombre());
            u.setCorreo(req.getCorreo());
            u.setDocumento(req.getDocumento());
            u.setTelefono(req.getTelefono());

            // Asignar correctamente el ROL = 1 (ADMIN)
            Rol rolAdmin = new Rol();
            rolAdmin.setId(1L); // solo el ID; el nombre ya está en la BD
            u.setRol(rolAdmin);

            // Registrar usuario + contraseña encriptada
            Usuario creado = service.registrarUsuarioConPassword(
                    u,
                    req.getContrasena()
            );

            return ResponseEntity.ok(creado);

        } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error al registrar admin: " + e.getMessage());
        }
    }

    @GetMapping("/admins")
    public ResponseEntity<?> listarAdministradores() {
        return ResponseEntity.ok(service.listarAdministradores());
    }
}
