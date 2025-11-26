package com.cda.certimotos.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    
    // 1. Crear cliente automáticamente (usado desde módulo "citas")
   
    public Usuario crearClienteSiNoExiste(Usuario datos) {
        Optional<Usuario> existente = repo.findByDocumento(datos.getDocumento());
        if (existente.isPresent()) return existente.get();
        datos.setIdRol(2L); // 2 = cliente
        return repo.save(datos);
    }

    public Usuario crearUsuario(Usuario usuario) { return repo.save(usuario); }
    public List<Usuario> listarUsuarios() { return repo.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return repo.findById(id); }
    public Optional<Usuario> buscarPorDocumento(String doc) { return repo.findByDocumento(doc); }

    public Usuario actualizarUsuario(Long id, Usuario nuevo) {
        return repo.findById(id).map(u -> {
            u.setNombre(nuevo.getNombre());
            u.setApellido1(nuevo.getApellido1());
            u.setApellido2(nuevo.getApellido2());
            u.setDocumento(nuevo.getDocumento());
            u.setCorreo(nuevo.getCorreo());
            u.setTelefono(nuevo.getTelefono());
            u.setDireccion(nuevo.getDireccion());
            u.setIdRol(nuevo.getIdRol());
            return repo.save(u);
        }).orElse(null);
    }

    public boolean eliminarUsuario(Long id) {
        if (repo.existsById(id)) { repo.deleteById(id); return true; }
        return false;
    }
}

