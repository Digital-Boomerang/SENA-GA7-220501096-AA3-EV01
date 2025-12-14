package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.entity.Cita;
import com.cda.certimotos.citas.services.CitaServices;
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
    public ResponseEntity<?> crear(@RequestBody Cita c) {
        try {
            Cita creada = service.crearCita(c);
            return ResponseEntity.ok(creada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(service.listarCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.buscarPorIdOptional(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cita c) {
        Cita actualizada = service.actualizarCita(id, c);
        if (actualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean ok = service.eliminarCita(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Cita eliminada");
    }

    @GetMapping("/ocupadas")
    public ResponseEntity<List<String>> getHorasOcupadas(@RequestParam String fecha) {
        try {
            List<String> horas = service.obtenerHorasOcupadasPorFecha(fecha);
            return ResponseEntity.ok(horas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of()); // retorna array vacío en caso de error
        }
    }

    

}
