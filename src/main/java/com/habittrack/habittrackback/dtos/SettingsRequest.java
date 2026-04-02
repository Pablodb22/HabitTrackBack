package com.habittrack.habittrackback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingsRequest {
    
    private String nombre;    
    private String biografia;
    private String fotoPerfil;
    private String username;
    
}
