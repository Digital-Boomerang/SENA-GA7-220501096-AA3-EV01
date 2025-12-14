package com.cda.certimotos.citas.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String mensaje;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;
}