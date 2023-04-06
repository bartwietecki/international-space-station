package org.service;

import org.dto.SimpleAstronautsDto;
import org.entity.Astronauts;
import org.repository.AstronautsRepository;

import java.util.List;
import java.util.Set;

public class AstronautsService {

    private final AstronautsRepository astronautsRepository;

    public AstronautsService(AstronautsRepository astronautsRepository) {
        this.astronautsRepository = astronautsRepository;
    }

    public void addAstronauts(String name) throws IllegalArgumentException {
        if (name != null) {
            Astronauts astronauts = new Astronauts();
            astronauts.setName(name);

            astronautsRepository.insert(astronauts);
        } else {
            throw new IllegalArgumentException("Name cannot be null");
        }

    }

    public List<SimpleAstronautsDto> findAll() {
        Set<Astronauts> astronaut = astronautsRepository.findAll();
        return astronaut.stream()
                .map(astronauts -> new SimpleAstronautsDto(astronauts.getId(),
                        astronauts.getName()))
                .toList();
    }

}
