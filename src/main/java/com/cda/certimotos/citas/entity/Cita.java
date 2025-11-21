package com.cda.certimotos.citas.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id;

    private LocalDate fecha;

    private String estado;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @Column(name = "id_horario")
    private Long idHorario;

    public Cita() {}

    public Cita(LocalDate fecha, String estado, Long idUsuario, Long idVehiculo, Long idHorario) {
        this.fecha = fecha;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idVehiculo = idVehiculo;
        this.idHorario = idHorario;
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Long idVehiculo) { this.idVehiculo = idVehiculo; }

    public Long getIdHorario() { return idHorario; }
    public void setIdHorario(Long idHorario) { this.idHorario = idHorario; }
}
