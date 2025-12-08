package com.cda.certimotos.citas.services;

import com.cda.certimotos.citas.entity.Cita;
import com.cda.certimotos.citas.repository.CitaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class CitaServices {

    private final CitaRepository repository;

    public CitaServices(CitaRepository repository) {
        this.repository = repository;
    }

    public Cita crearCita(Cita cita) {

        // Validar unicidad: usuario + fecha + horario
        Optional<Cita> existe = repository.findByUsuarioIdAndFechaAndHorarioId(
                cita.getUsuario().getId(),
                cita.getFecha(),
                cita.getHorario().getId()
        );

        if (existe.isPresent()) {
            throw new IllegalArgumentException(
                    "El usuario ya tiene una cita para esa fecha y horario."
            );
        }

        return repository.save(cita);
    }

    public List<Cita> listarCitas() {
        return repository.findAll();
    }

    public Optional<Cita> buscarPorIdOptional(Long id) {
        return repository.findById(id);
    }

    public Cita buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cita no encontrada con id: " + id)
                );
    }

    public Cita actualizarCita(Long id, Cita nueva) {
        return repository.findById(id).map(citaExistente -> {

            citaExistente.setFecha(nueva.getFecha());
            citaExistente.setEstado(nueva.getEstado());

            // Relaciones corregidas:
            citaExistente.setUsuario(nueva.getUsuario());
            citaExistente.setVehiculo(nueva.getVehiculo());
            citaExistente.setHorario(nueva.getHorario());

            return repository.save(citaExistente);

        }).orElseThrow(() ->
                new EntityNotFoundException("No se puede actualizar: cita no encontrada.")
        );
    }

    public boolean eliminarCita(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> obtenerHorasOcupadasPorFecha(String fecha) {
        LocalDate fechaCita = LocalDate.parse(fecha);

        List<Cita> citas = CitaRepository.findByFecha(fechaCita);

        return citas.stream()
            .map(c -> c.getHorario().getHora().toString().substring(0, 5)) 
            .toList();
    }

}