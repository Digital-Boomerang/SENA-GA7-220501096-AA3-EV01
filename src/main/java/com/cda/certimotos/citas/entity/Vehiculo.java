package com.cda.certimotos.citas.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("vehiculos") // evita ciclos infinitos en JSON
    private Usuario usuario;

    @Column(nullable = false)
    private String placa;

    public Vehiculo() {}

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
