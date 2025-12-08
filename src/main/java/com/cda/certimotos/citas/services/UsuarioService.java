package com.cda.certimotos.citas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cda.certimotos.citas.entity.Rol;
import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.repository.RolRepository;
import com.cda.certimotos.citas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final RolRepository rolRepo;

    public UsuarioService(UsuarioRepository repo, RolRepository rolRepo) {
        this.repo = repo;
        this.rolRepo = rolRepo;
    }

    // Crear cliente automáticamente (rol = 2)
    public Usuario crearClienteSiNoExiste(Usuario datos) {

        Optional<Usuario> existente = repo.findByDocumento(datos.getDocumento());
        if (existente.isPresent()) return existente.get();

        // Rol 2 = CLIENTE
        Rol rolCliente = rolRepo.findById(2L)
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no existe"));

        datos.setRol(rolCliente);

        return repo.save(datos);
    }

    // Crear usuario manual (admin o cliente)
    public Usuario crearUsuario(Usuario usuario) {

        // Validar que venga un rol correcto
        if (usuario.getRol() == null) {
            throw new RuntimeException("Debe indicar un rol válido");
        }

        // Validar que el rol exista
        Rol rol = rolRepo.findById(usuario.getRol().getId())
                .orElseThrow(() -> new RuntimeException("El rol indicado no existe"));

        usuario.setRol(rol);

        return repo.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Usuario> buscarPorDocumento(String doc) {
        return repo.findByDocumento(doc);
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(Long id, Usuario nuevo) {

        return repo.findById(id).map(u -> {

            u.setNombre(nuevo.getNombre());
            u.setApellido1(nuevo.getApellido1());
            u.setApellido2(nuevo.getApellido2());
            u.setDocumento(nuevo.getDocumento());
            u.setCorreo(nuevo.getCorreo());
            u.setTelefono(nuevo.getTelefono());
            u.setDireccion(nuevo.getDireccion());

            // Asignación correcta del rol
            if (nuevo.getRol() != null) {
                Rol rol = rolRepo.findById(nuevo.getRol().getId())
                        .orElseThrow(() -> new RuntimeException("Rol no válido"));

                u.setRol(rol);
            }

            return repo.save(u);

        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public boolean eliminarUsuario(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
