package com.habittrack.habittrackback.services;

import com.habittrack.habittrackback.dtos.LoginRequest;
import com.habittrack.habittrackback.dtos.RegisterRequest;
import com.habittrack.habittrackback.models.Usuario;
import com.habittrack.habittrackback.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario register(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(request.getPassword()) // In a real app, hash this password!
                .build();
        return usuarioRepository.save(usuario);
    }

    public Usuario login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return usuarioOpt.get();
    }
}
