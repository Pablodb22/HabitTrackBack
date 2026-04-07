package com.habittrack.habittrackback.repositories;

import com.habittrack.habittrackback.models.HabitoCompletado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface HabitoCompletadoRepository extends JpaRepository<HabitoCompletado, Long> {
    List<HabitoCompletado> findByUsuarioIdAndFechaBetween(Long usuarioId, LocalDate start, LocalDate end);
    boolean existsByHabitoIdAndFecha(Long habitoId, LocalDate fecha);
}
