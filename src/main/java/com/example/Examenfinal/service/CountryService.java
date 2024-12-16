package com.example.Examenfinal.service;

import com.example.Examenfinal.entity.Country;
import com.example.Examenfinal.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    // Guardar un país
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    // Buscar un país por su nombre
    public Optional<Country> findByName(String name) {
        return countryRepository.findByName(name);
    }

    // Actualizar un país por su nombre
    public Country updateCountry(String name, Country updatedCountry) {
        return countryRepository.findByName(name)
                .map(country -> {
                    country.setContinent(updatedCountry.getContinent());
                    country.setLanguage(updatedCountry.getLanguage());
                    return countryRepository.save(country);
                })
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }


    // Eliminar un país por su nombre
    public void deleteByName(String name) {
        Optional<Country> country = countryRepository.findByName(name);
        if (country.isPresent()) {
            countryRepository.delete(country.get()); // Se debe usar delete con el objeto
        } else {
            throw new RuntimeException("Country not found");
        }
    }


    // Obtener todos los países
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }
}
