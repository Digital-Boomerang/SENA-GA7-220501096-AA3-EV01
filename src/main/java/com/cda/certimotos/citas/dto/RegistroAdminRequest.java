package com.cda.certimotos.citas.dto;

import lombok.Data;

@Data
public class RegistroAdminRequest {
    private String nombre;
    private String correo;
    private String documento;
    private String telefono;
    private String contrasena;
}

