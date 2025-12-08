package com.cda.certimotos.citas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cda.certimotos.citas.entity.Horario;
import com.cda.certimotos.citas.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin("*")
public class HorarioController {

    private final HorarioService service;

    public HorarioController(HorarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Horario horario) {
        try {
            return ResponseEntity.ok(service.crear(horario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear horario: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Horario>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
