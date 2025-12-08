package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Rol;
import com.cda.certimotos.citas.repository.RolRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    private final RolRepository repoRl;

    public RolService(RolRepository repoRl) {
        this.repoRl = repoRl;
    }

    public List<Rol> listar() { return repoRl.findAll(); }

}
