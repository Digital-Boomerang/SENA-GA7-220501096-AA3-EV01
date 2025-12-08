package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Exportacion;
import com.cda.certimotos.citas.repository.ExportacionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExportacionService {

    private final ExportacionRepository repo;

    public ExportacionService(ExportacionRepository repo) {
        this.repo = repo;
    }

    public Exportacion registrar(Exportacion exp) {
        return repo.save(exp);
    }
}