package com.habittrack.habittrackback.services;

import com.habittrack.habittrackback.dtos.LoginRequestDTO;
import com.habittrack.habittrackback.dtos.RegisterRequestDTO;
import com.habittrack.habittrackback.dtos.SettingsRequestDTO;
import com.habittrack.habittrackback.dtos.UserSettingsDTO;
import com.habittrack.habittrackback.models.Usuario;
import com.habittrack.habittrackback.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario register(RegisterRequestDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
            Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .biografia("")
                .fotoPerfil("")
                .username("")   
                .build();
        return usuarioRepository.save(usuario);
    }

    public Usuario login(LoginRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), usuarioOpt.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return usuarioOpt.get();
    }

    public UserSettingsDTO getUserSettings(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Usuario usuario = usuarioOpt.get();
        return new UserSettingsDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getBiografia(), usuario.getFotoPerfil(), usuario.getUsername());
    }

    public SettingsRequestDTO putUserSettings(String email, SettingsRequestDTO settingsRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(settingsRequest.getNombre());
        usuario.setBiografia(settingsRequest.getBiografia());
        usuario.setFotoPerfil(settingsRequest.getFotoPerfil());
        usuario.setUsername(settingsRequest.getUsername());
        usuarioRepository.save(usuario);
        return new SettingsRequestDTO(usuario.getNombre(), usuario.getBiografia(), usuario.getFotoPerfil(), usuario.getUsername());
    }

    public void deleteUser(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        usuarioRepository.delete(usuarioOpt.get());
    }
}
