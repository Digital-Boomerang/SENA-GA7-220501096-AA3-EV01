package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.entity.Exportacion;
import com.cda.certimotos.citas.services.ExportacionService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/exportaciones")
@CrossOrigin("*")
public class ExportacionController {

    private final ExportacionService service;

    public ExportacionController(ExportacionService service) {
        this.service = service;
    }

    @PostMapping
    public Exportacion registrar(@RequestBody Exportacion exp) {
        return service.registrar(exp);
    }
}