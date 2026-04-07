package com.habittrack.habittrackback.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long usuario_id;

    private String nombre;

    private String descripcion;

    private String frecuencia;

    private String categoria;
   
    private String fecha_creacion;

    private boolean completo;
    
    private LocalDate lastCompletedDate;
    
    private Integer currentStreak;
    
    private Integer bestStreak;
}
