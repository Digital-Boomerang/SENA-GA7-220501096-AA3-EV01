package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.entity.Cita;
import com.cda.certimotos.citas.services.CitaServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin("*")
public class CitaController {

    private final CitaServices service;

    public CitaController(CitaServices service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cita> crear(@RequestBody Cita cita) {
        return ResponseEntity.ok(service.crearCita(cita));
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(service.listarCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> buscar(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizar(@PathVariable long id, @RequestBody Cita cita) {
        try {
            return ResponseEntity.ok(service.actualizarCita(id, cita));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            service.eliminarCita(id);
            return ResponseEntity.noContent().build(); // 204 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
