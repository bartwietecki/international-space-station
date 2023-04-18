package api;

import com.google.gson.Gson;
import dto.HttpLocationResponseDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LocationApi {

    public static HttpLocationResponseDto getCurrentLocationFromApi() throws IOException, InterruptedException, URISyntaxException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("http://api.open-notify.org/iss-now"))
                .build();

        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(getResponse.body());

        HttpLocationResponseDto httpLocationResponseDto = gson.fromJson(getResponse.body(), HttpLocationResponseDto.class);

        return httpLocationResponseDto;

    }
}
