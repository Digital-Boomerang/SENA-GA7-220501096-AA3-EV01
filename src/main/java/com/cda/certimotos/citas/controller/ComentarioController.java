package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.entity.Comentario;
import com.cda.certimotos.citas.services.ComentarioService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin("*")
public class ComentarioController {

    private final ComentarioService service;

    public ComentarioController(ComentarioService service) {
        this.service = service;
    }

    @PostMapping
    public Comentario crear(@RequestBody Comentario c) {
        return service.crear(c);
    }

    @GetMapping
    public List<Comentario> listar() {
        return service.listar();
    }
}

