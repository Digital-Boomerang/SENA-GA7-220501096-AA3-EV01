package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Cita;
import com.cda.certimotos.citas.repository.CitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CitaServices {

    private final CitaRepository repository;

    public CitaServices(CitaRepository repository) {
        this.repository = repository;
    }

    public Cita crearCita(Cita cita) {
        // validar unicidad usuario+fecha+horario
        Optional<Cita> existe = repository.findByIdUsuarioAndFechaAndIdHorario(cita.getIdUsuario(), cita.getFecha(), cita.getIdHorario());
        if (existe.isPresent()) {
            throw new IllegalArgumentException("El usuario ya tiene una cita para esa fecha y horario.");
        }
        return repository.save(cita);
    }

    public List<Cita> listarCitas() { return repository.findAll(); }

    public Optional<Cita> buscarPorIdOptional(Long id) { return repository.findById(id); }

    public Cita buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cita no encontrada con id: " + id));
    }

    public Cita actualizarCita(Long id, Cita nueva) {
        return repository.findById(id).map(citaExistente -> {
            citaExistente.setFecha(nueva.getFecha());
            citaExistente.setEstado(nueva.getEstado());
            citaExistente.setIdUsuario(nueva.getIdUsuario());
            citaExistente.setIdVehiculo(nueva.getIdVehiculo());
            citaExistente.setIdHorario(nueva.getIdHorario());
            return repository.save(citaExistente);
        }).orElse(null);
    }

    public boolean eliminarCita(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
