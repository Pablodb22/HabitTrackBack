package com.habittrack.habittrackback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habittrack.habittrackback.services.HabitoService;
import com.habittrack.habittrackback.models.Habito;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 
public class HabitosController {
    
    private final HabitoService habitoService;

    @PostMapping("/create/{email}")
    public ResponseEntity<Habito> crearHabito(@RequestBody Habito habito, @PathVariable("email") String email) {
        return ResponseEntity.ok(habitoService.crearHabito(habito, email));
    }
}
