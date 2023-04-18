package service;

import api.AstronautsApi;
import com.google.gson.Gson;
import dto.HttpAstronautsResponseDto;
import dto.SimpleAstronautsDto;
import entity.Astronauts;
import repository.AstronautsRepository;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AstronautsService {

    private final AstronautsRepository astronautsRepository;
    private final HttpClient httpClient;
    private final Gson gson;

    public AstronautsService(AstronautsRepository astronautsRepository) {
        this.astronautsRepository = astronautsRepository;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
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

    public void saveAstronautsFromResponse(HttpAstronautsResponseDto responseDto) {
        List<Astronauts> astronauts = Arrays.stream(responseDto.getPeople())
                .map(person -> new Astronauts(person.getName()))
                .collect(Collectors.toList());

        for (Astronauts astronaut : astronauts) {
            astronautsRepository.insert(astronaut);
        }
    }

    public int getNumberOfCurrentAstronautsFromApi()    {
        try {
            HttpAstronautsResponseDto responseDto = AstronautsApi.getCurrentAstronautsFromApi();
            return responseDto.getNumber();
        } catch (Exception e) {
            System.err.println("Error while getting number of astronauts: " + e.getMessage());
            return -1;
        }
    }

}
