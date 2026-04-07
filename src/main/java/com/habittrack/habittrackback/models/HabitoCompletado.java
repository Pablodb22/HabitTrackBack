package com.habittrack.habittrackback.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "habito_completado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitoCompletado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long habitoId;

    private Long usuarioId;

    private LocalDate fecha;
}
