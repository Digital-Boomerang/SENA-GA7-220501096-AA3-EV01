package com.cda.certimotos.citas.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exportacion")
public class Exportacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fecha;

    @Column(name = "ruta_archivo")
    private String rutaArchivo;
}
