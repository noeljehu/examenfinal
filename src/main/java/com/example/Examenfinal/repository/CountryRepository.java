package com.example.Examenfinal.repository;

import com.example.Examenfinal.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
    void deleteByName(String name);
}
