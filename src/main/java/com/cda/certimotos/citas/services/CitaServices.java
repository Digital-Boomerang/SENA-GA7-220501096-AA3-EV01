package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Cita;
import com.cda.certimotos.citas.repository.CitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServices {

    private final CitaRepository repository;

    public CitaServices(CitaRepository repository) {
        this.repository = repository;
    }

    // Crear cita
    public Cita crearCita(Cita cita) {
        return repository.save(cita);
    }

    // Listar todas las citas
    public List<Cita> listarCitas() {
        return repository.findAll();
    }

    // Buscar por ID 
    public Cita buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada con id: " + id));
    }

    // Actualizar cita existente
    public Cita actualizarCita(Long id, Cita nueva) {

        Cita citaExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada con id: " + id));

        citaExistente.setFecha(nueva.getFecha());
        citaExistente.setEstado(nueva.getEstado());
        citaExistente.setIdUsuario(nueva.getIdUsuario());
        citaExistente.setIdVehiculo(nueva.getIdVehiculo());
        citaExistente.setIdHorario(nueva.getIdHorario());

        return repository.save(citaExistente);
    }

    // Eliminar cita por ID
    public void eliminarCita(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar. Cita no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
