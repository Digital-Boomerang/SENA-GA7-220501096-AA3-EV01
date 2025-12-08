package com.cda.certimotos.citas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.entity.Vehiculo;
import com.cda.certimotos.citas.repository.VehiculoRepository;
import com.cda.certimotos.citas.repository.UsuarioRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepo;
    private final UsuarioRepository usuarioRepo;

    public VehiculoService(VehiculoRepository vehiculoRepo, UsuarioRepository usuarioRepo) {
        this.vehiculoRepo = vehiculoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // Crear vehículo
    public Vehiculo crear(Vehiculo v) {

        // Validar usuario antes de guardar
        if (v.getUsuario() == null || v.getUsuario().getId() == null) {
            throw new RuntimeException("Debe asociar el vehículo a un usuario válido");
        }

        Usuario usuario = usuarioRepo.findById(v.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        v.setUsuario(usuario);

        return vehiculoRepo.save(v);
    }

    // Listar todos los vehículos
    public List<Vehiculo> listar() {
        return vehiculoRepo.findAll();
    }

    // Listar por id_usuario (relación ManyToOne)
    public List<Vehiculo> listarPorUsuario(Long idUsuario) {
        return vehiculoRepo.findByUsuario_Id(idUsuario);
    }
}
