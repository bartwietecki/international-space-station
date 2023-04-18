package service;

import com.google.gson.Gson;
import dto.HttpAstronautsResponseDto;
import dto.SimpleAstronautsDto;
import entity.Astronauts;
import repository.AstronautsRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public void getAndSaveAstronauts() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("http://api.open-notify.org/astros.json"))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        // Parsing JSON using Gson library
        Astronauts[] astronauts = gson.fromJson(responseBody, Astronauts[].class);

        // Saving astronauts to DB
        //        AstronautsRepository repository = new AstronautsRepository();
        for (Astronauts astronaut : astronauts) {
            astronautsRepository.insert(astronaut);
        }
    }

    public void saveAstronautsFromResponse(HttpAstronautsResponseDto responseDto) {
        List<Astronauts> astronauts = Arrays.stream(responseDto.getPeople())
                .map(person -> new Astronauts(person.getName()))
                .collect(Collectors.toList());

        for (Astronauts astronaut : astronauts) {
            astronautsRepository.insert(astronaut);
        }
    }

}
