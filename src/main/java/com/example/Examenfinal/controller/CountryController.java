package com.example.Examenfinal.controller;

import com.example.Examenfinal.entity.Country;
import com.example.Examenfinal.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Crear un país
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCountry(@Valid @RequestBody Country country) {
        Country createdCountry = countryService.saveCountry(country);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "País creado correctamente");
        response.put("data", createdCountry);
        return ResponseEntity.status(201).body(response); // 201 Created
    }

    // Obtener un país por nombre
    @GetMapping("/{name}")
    public ResponseEntity<Map<String, Object>> getCountryByName(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        return countryService.findByName(name)
                .map(country -> {
                    response.put("message", "País encontrado");
                    response.put("data", country);
                    return ResponseEntity.ok(response); // 200 OK
                })
                .orElseGet(() -> {
                    response.put("message", "País no encontrado");
                    response.put("data", null);
                    return ResponseEntity.status(404).body(response); // 404 Not Found
                });
    }

    // Actualizar un país por nombre
    @PutMapping("/{name}")
    public ResponseEntity<Map<String, Object>> updateCountry(
            @PathVariable String name, @Valid @RequestBody Country updatedCountry) {
        Map<String, Object> response = new HashMap<>();
        try {
            Country updated = countryService.updateCountry(name, updatedCountry);
            response.put("message", "País actualizado correctamente");
            response.put("data", updated);
            return ResponseEntity.ok(response); // 200 OK
        } catch (RuntimeException ex) {
            response.put("message", "País no encontrado");
            response.put("data", null);
            return ResponseEntity.status(404).body(response); // 404 Not Found
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, Object>> deleteCountry(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Intentamos eliminar el país
            countryService.deleteByName(name);
            // Si se eliminó correctamente, devolvemos un mensaje con código 204
            response.put("message", "País eliminado correctamente");
            response.put("data", null);
            return ResponseEntity.status(204).body(response); // 204 No Content (sin contenido en la respuesta)
        } catch (RuntimeException ex) {
            // Si el país no se encuentra, devolvemos un mensaje con código 404
            response.put("message", "País no encontrado");
            response.put("data", null);
            return ResponseEntity.status(404).body(response); // 404 Not Found
        }
    }



    // Obtener todos los países
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCountries() {
        List<Country> countries = countryService.findAllCountries();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Lista de países obtenida correctamente");
        response.put("data", countries);
        return ResponseEntity.ok(response); // 200 OK
    }
}
