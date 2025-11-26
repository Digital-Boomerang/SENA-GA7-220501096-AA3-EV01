package com.cda.certimotos.citas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cda.certimotos.citas.entity.Vehiculo;
import com.cda.certimotos.citas.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin("*")
public class VehiculoController {
    private final VehiculoService service;
    public VehiculoController(VehiculoService s){ this.service = s; }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v){ return ResponseEntity.ok(service.crear(v)); }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar(){ return ResponseEntity.ok(service.listar()); }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Vehiculo>> listarPorUsuario(@PathVariable Long idUsuario){
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }
}
