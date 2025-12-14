package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Comentario;
import com.cda.certimotos.citas.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository repo;

    public ComentarioService(ComentarioRepository repo) {
        this.repo = repo;
    }

    public Comentario crear(Comentario c) {

        c.setFechaPublicacion(LocalDate.now()); 

        return repo.save(c);
    }

    public List<Comentario> listar() {
        return repo.findAll();
    }
}