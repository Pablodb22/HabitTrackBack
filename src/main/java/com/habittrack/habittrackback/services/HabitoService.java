package com.habittrack.habittrackback.services;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.habittrack.habittrackback.models.Habito;
import com.habittrack.habittrackback.models.Usuario;
import com.habittrack.habittrackback.repositories.HabitoRepository;
import com.habittrack.habittrackback.repositories.UsuarioRepository;


@Service
@RequiredArgsConstructor
public class HabitoService {
    
    private final HabitoRepository habitoRepository;
    private final UsuarioRepository usuarioRepository;

    public Habito crearHabito(Habito habito, String email) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        habito.setUsuario_id(usuario.getId());

        return habitoRepository.save(habito);
    }

    public List<Habito> getHabitsByUser(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return habitoRepository.findByUsuario_id(usuario.getId());
    }
    
}
