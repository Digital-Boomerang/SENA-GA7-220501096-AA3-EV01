package com.cda.certimotos.citas.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id;

    private LocalDate fecha;

    private String estado = "pendiente";

    // Cita → Usuario (muchas citas por usuario)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties("citas")  // evita recursión infinita
    private Usuario usuario;

    // Cita → Vehiculo (un vehículo puede tener varias citas)
    @ManyToOne
    @JoinColumn(name = "id_vehiculo")
    @JsonIgnoreProperties("citas")
    private Vehiculo vehiculo;

    // Cita → Horario (muchas citas por horario)
    @ManyToOne
    @JoinColumn(name = "id_horario")
    @JsonIgnoreProperties("citas")
    private Horario horario;

    public Cita() {}

    // ------------------------
    // GETTERS Y SETTERS
    // ------------------------

    public Long getId() { return id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }
}
