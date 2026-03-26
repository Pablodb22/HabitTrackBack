package com.habittrack.habittrackback.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
}
