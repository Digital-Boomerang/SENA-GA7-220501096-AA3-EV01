package com.cda.certimotos.citas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cda.certimotos.citas.entity.Horario;
import com.cda.certimotos.citas.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin("*")
public class HorarioController {
    private final HorarioService service;
    public HorarioController(HorarioService service){ this.service = service; }

    @PostMapping
    public ResponseEntity<Horario> crear(@RequestBody Horario h){ return ResponseEntity.ok(service.crear(h)); }

    @GetMapping
    public ResponseEntity<List<Horario>> listar(){ return ResponseEntity.ok(service.listar()); }
}
