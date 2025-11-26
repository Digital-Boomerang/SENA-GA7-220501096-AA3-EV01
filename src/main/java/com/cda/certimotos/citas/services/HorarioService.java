package com.cda.certimotos.citas.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cda.certimotos.citas.entity.Horario;
import com.cda.certimotos.citas.repository.HorarioRepository;

@Service
public class HorarioService {
    private final HorarioRepository repo;
    public HorarioService(HorarioRepository repo){ this.repo = repo; }

    public Horario crear(Horario h){ return repo.save(h); }
    public List<Horario> listar(){ return repo.findAll(); }
}