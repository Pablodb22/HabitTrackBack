package com.habittrack.habittrackback.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.habittrack.habittrackback.models.Habito;
import com.habittrack.habittrackback.models.HabitoCompletado;
import com.habittrack.habittrackback.services.HabitoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") 
public class HabitosController {
    
    private final HabitoService habitoService;

    @PostMapping("/{email}")
    public ResponseEntity<Habito> crearHabito(@RequestBody Habito habito, @PathVariable("email") String email) {
        return ResponseEntity.ok(habitoService.crearHabito(habito, email));
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Habito>> getHabitsByUser(@PathVariable("email") String email) {
        return ResponseEntity.ok(habitoService.getHabitsByUser(email));
    }

    @GetMapping("/{email}/weekly")
    public ResponseEntity<List<HabitoCompletado>> getWeeklyCompletions(@PathVariable("email") String email) {
        return ResponseEntity.ok(habitoService.getWeeklyCompletions(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habito> completarHabito(@PathVariable("id") Long id) {
        return ResponseEntity.ok(habitoService.completarHabito(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Habito> eliminarHabito(@PathVariable("id") Long id) {
        return ResponseEntity.ok(habitoService.eliminarHabito(id));
    }
}
