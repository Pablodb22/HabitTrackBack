package com.habittrack.habittrackback.repositories;

import com.habittrack.habittrackback.models.Habito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {

    @Query("SELECT h FROM Habito h WHERE h.usuario_id = ?1")
    List<Habito> findByUsuario_id(Long usuario_id);
}
