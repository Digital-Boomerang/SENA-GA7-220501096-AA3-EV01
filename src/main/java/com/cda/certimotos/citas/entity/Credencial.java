package com.cda.certimotos.citas.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "credencial")  // nombre real en la BD
public class Credencial {

    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    @OneToOne
    @MapsId  // usa el mismo ID del usuario
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "contrasena") // SIN Ñ
    private String contrasena;
}
