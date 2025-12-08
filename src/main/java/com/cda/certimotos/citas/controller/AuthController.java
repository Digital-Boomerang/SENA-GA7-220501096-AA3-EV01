package com.cda.certimotos.citas.controller;

import com.cda.certimotos.citas.dto.LoginRequest;
import com.cda.certimotos.citas.dto.LoginResponse;
import com.cda.certimotos.citas.entity.Usuario;
import com.cda.certimotos.citas.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario u = authService.login(
                    loginRequest.getCorreo(),
                    loginRequest.getPassword()
            );

            return ResponseEntity.ok(
                    new LoginResponse(
                            u.getId(),
                            u.getNombre(),
                            u.getCorreo(),
                            "Administrador"
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
