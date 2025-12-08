package com.cda.certimotos.citas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {

    @JsonProperty("correo")
    private String correo;

    @JsonProperty("contraseña")
    private String password;  // ← nombre interno sin ñ
}