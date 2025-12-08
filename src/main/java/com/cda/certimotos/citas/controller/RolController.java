package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.entity.Rol;
import com.cda.certimotos.citas.services.RolService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @GetMapping
    public List<Rol> listar() {
        return service.listar();
    }
}
