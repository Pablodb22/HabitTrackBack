package com.habittrack.habittrackback.services;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.habittrack.habittrackback.models.Habito;
import com.habittrack.habittrackback.models.Usuario;
import com.habittrack.habittrackback.models.HabitoCompletado;
import com.habittrack.habittrackback.repositories.HabitoCompletadoRepository;
import com.habittrack.habittrackback.repositories.HabitoRepository;
import com.habittrack.habittrackback.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class HabitoService {
    
    private final HabitoRepository habitoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HabitoCompletadoRepository habitoCompletadoRepository;

    public Habito crearHabito(Habito habito, String email) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        habito.setUsuario_id(usuario.getId());

        return habitoRepository.save(habito);
    }

    public List<Habito> getHabitsByUser(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Habito> habitos = habitoRepository.findByUsuario_id(usuario.getId());
        
        LocalDate today = LocalDate.now();
        for (Habito h : habitos) {            
            if (h.getCurrentStreak() == null) h.setCurrentStreak(0);
            if (h.getBestStreak() == null) h.setBestStreak(0);

            if (h.isCompleto() && h.getLastCompletedDate() != null && !h.getLastCompletedDate().equals(today)) {
                h.setCompleto(false);
                habitoRepository.save(h);
            }            
            if (h.getLastCompletedDate() != null && 
                !h.getLastCompletedDate().equals(today) && 
                !h.getLastCompletedDate().equals(today.minusDays(1))) {
                h.setCurrentStreak(0);
                habitoRepository.save(h);
            }
        }
        
        return habitos;
    }

    public Habito completarHabito(Long id) {
        Habito habito = habitoRepository.findById(id).orElseThrow(() -> new RuntimeException("Habito no encontrado"));
                
        if (habito.getCurrentStreak() == null) habito.setCurrentStreak(0);
        if (habito.getBestStreak() == null) habito.setBestStreak(0);

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        if (!habito.isCompleto()) {            
            if (!habitoCompletadoRepository.existsByHabitoIdAndFecha(id, today)) {
                HabitoCompletado completado = HabitoCompletado.builder()
                        .habitoId(id)
                        .usuarioId(habito.getUsuario_id())
                        .fecha(today)
                        .build();
                habitoCompletadoRepository.save(completado);
            }
           
            if (habito.getLastCompletedDate() != null) {
                if (habito.getLastCompletedDate().equals(yesterday)) {
                    habito.setCurrentStreak(habito.getCurrentStreak() + 1);
                } else if (!habito.getLastCompletedDate().equals(today)) {
                    habito.setCurrentStreak(1);
                } else {                    
                    habito.setCurrentStreak(Math.max(1, habito.getCurrentStreak()));
                }
            } else {
                habito.setCurrentStreak(1);
            }

            if (habito.getCurrentStreak() > habito.getBestStreak()) {
                habito.setBestStreak(habito.getCurrentStreak());
            }

            habito.setCompleto(true);
            habito.setLastCompletedDate(today);
        }
        
        return habitoRepository.save(habito);
    }

    public Habito eliminarHabito(Long id) {
        Habito habito = habitoRepository.findById(id).orElseThrow(() -> new RuntimeException("Habito no encontrado"));
        habitoRepository.delete(habito);
        return habito;
    }

    public List<HabitoCompletado> getWeeklyCompletions(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);
        return habitoCompletadoRepository.findByUsuarioIdAndFechaBetween(usuario.getId(), start, end);
    }
}
