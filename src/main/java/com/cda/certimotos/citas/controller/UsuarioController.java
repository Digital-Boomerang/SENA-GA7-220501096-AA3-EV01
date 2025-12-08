package com.cda.certimotos.citas.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Crear usuario (ADMIN o CLIENTE MANUAL)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Usuario u) {
        try {
            return ResponseEntity.ok(service.crearUsuario(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Registro automático de clientes (desde agendamiento)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario u) {
        try {
            return ResponseEntity.ok(service.crearClienteSiNoExiste(u));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {

        Optional<Usuario> usuarioOpt = service.buscarPorId(id);

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(service.actualizarUsuario(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (service.eliminarUsuario(id)) {
            return ResponseEntity.ok("Usuario eliminado");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
}
