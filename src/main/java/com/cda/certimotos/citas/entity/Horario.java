package com.cda.certimotos.citas.entity;

import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long id;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "estado")
    private String estado;

    // Un horario puede tener muchas citas
    @OneToMany(mappedBy = "horario")
    @JsonIgnore   // evita recursión infinita en JSON
    private List<Cita> citas;

    public Horario() {}

    // -----------------------
    // GETTERS Y SETTERS
    // -----------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
}
