package com.cda.certimotos.citas.controller;

import java.util.List;

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

    // Crear usuario (admin o cliente manual)
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario u) {
        return ResponseEntity.ok(service.crearUsuario(u));
    }

    // Registrar cliente automáticamente (si ya existe → retorna el mismo)
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario u) {
        return ResponseEntity.ok(service.crearClienteSiNoExiste(u));
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
