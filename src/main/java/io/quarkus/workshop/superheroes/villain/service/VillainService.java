package io.quarkus.workshop.superheroes.villain.service;

import io.quarkus.workshop.superheroes.villain.entity.Villain;
import jakarta.validation.Valid;

import java.util.List;

public interface VillainService {

    Villain findVillainById(Long id);

    List<Villain> findVillainsByName(String name);

    List<Villain> findAll();

    Villain createVillain(@Valid Villain villain);

    Villain findRandomVillain();

    Villain updateVillain(@Valid Villain villain);

    void deleteVillainById(Long id);
}
