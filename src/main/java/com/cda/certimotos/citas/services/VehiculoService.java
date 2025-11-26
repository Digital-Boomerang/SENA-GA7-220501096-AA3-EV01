package com.cda.certimotos.citas.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cda.certimotos.citas.entity.Vehiculo;
import com.cda.certimotos.citas.repository.VehiculoRepository;

@Service
public class VehiculoService {
    private final VehiculoRepository vrepo;
    public VehiculoService(VehiculoRepository vrepo){ this.vrepo = vrepo; }

    public Vehiculo crear(Vehiculo v){ return vrepo.save(v); }
    public List<Vehiculo> listar(){ return vrepo.findAll(); }
    public List<Vehiculo> listarPorUsuario(Long idUsuario){ return vrepo.findByIdUsuario(idUsuario); }
}
