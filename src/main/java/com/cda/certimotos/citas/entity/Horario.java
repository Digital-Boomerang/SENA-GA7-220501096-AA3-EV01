package com.cda.certimotos.citas.entity;

import java.sql.Time;

import jakarta.persistence.*;


@Entity
@Table(name = "horario")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")

    private Long id;
    
    // asume columna 'hora' tipo TIME en BD
    @Column(name = "hora")
    private Time hora;

    public Horario() {}

    //getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }
  
}


