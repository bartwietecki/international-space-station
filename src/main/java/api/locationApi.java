package api;

import com.google.gson.Gson;
import dto.HttpLocationResponseDto;
import dto.Iss_position;
import entity.Location;
import repository.LocationRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class locationApi {

    public static void getCurrentLocation() throws IOException, InterruptedException, URISyntaxException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://api.open-notify.org/iss-now"))
                .build();

        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(getResponse.body());

        HttpLocationResponseDto httpLocationResponseDto = gson.fromJson(getResponse.body(), HttpLocationResponseDto.class);

        System.out.println(httpLocationResponseDto);
        Iss_position iss_position = httpLocationResponseDto.getIss_position();
        Location location = new Location(iss_position.getLatitude() ,iss_position.getLongitude());

        final LocationRepository locationRepository = new LocationRepository();
        locationRepository.insert(location);

    }
}
