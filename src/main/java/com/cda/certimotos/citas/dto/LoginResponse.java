package com.cda.certimotos.citas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
}


