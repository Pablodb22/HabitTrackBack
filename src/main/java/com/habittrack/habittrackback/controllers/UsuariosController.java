package com.habittrack.habittrackback.controllers;

import com.habittrack.habittrackback.dtos.LoginRequest;
import com.habittrack.habittrackback.dtos.RegisterRequest;
import com.habittrack.habittrackback.models.Usuario;
import com.habittrack.habittrackback.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend to call APIs
public class UsuariosController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = usuarioService.register(request);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuario usuario = usuarioService.login(request);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
