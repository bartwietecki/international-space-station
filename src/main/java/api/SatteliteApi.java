package api;

import com.google.gson.Gson;
import dto.HttpCoordinatesResponseDto;
import dto.HttpSatteliteResponseDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;

public class SatteliteApi {
    public static HttpSatteliteResponseDto getCurrentSatteliteInfoFromApi() throws IOException, InterruptedException, URISyntaxException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.wheretheiss.at/v1/satellites/25544"))
                .build();

        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        HttpSatteliteResponseDto httpSatteliteResponseDto = gson.fromJson(getResponse.body(), HttpSatteliteResponseDto.class);

        return httpSatteliteResponseDto;

    }
    public static void showCurrentSatteliteVelocity() throws IOException, URISyntaxException, InterruptedException {
        HttpSatteliteResponseDto httpSatteliteResponseDto = SatteliteApi.getCurrentSatteliteInfoFromApi();
        double velocity = httpSatteliteResponseDto.getVelocity();
        String units = httpSatteliteResponseDto.getUnits();
        System.out.println("Current velocity is " + velocity + " " + units);
    }
    public static void showCurrentSatteliteLocationFromApi() throws IOException, InterruptedException, URISyntaxException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.wheretheiss.at/v1/satellites/25544"))
                .build();

        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        HttpSatteliteResponseDto httpSatteliteResponseDto = gson.fromJson(getResponse.body(), HttpSatteliteResponseDto.class);

        String latitude = httpSatteliteResponseDto.getLatitude();
        String longitude = httpSatteliteResponseDto.getLongitude();

        System.out.println(latitude);
        System.out.println(longitude);

        HttpRequest getRequest2 = HttpRequest.newBuilder()
                .uri(new URI("https://api.wheretheiss.at/v1/coordinates/" + latitude + "," + longitude))
                .build();

        System.out.println(getRequest2);
        Gson gson2 = new Gson();
        HttpClient httpClient2 = HttpClient.newHttpClient();
        HttpResponse<String> getResponse2 = httpClient2.send(getRequest2, HttpResponse.BodyHandlers.ofString());

        HttpCoordinatesResponseDto httpCoordinatesResponseDto = gson2.fromJson(getResponse2.body(), HttpCoordinatesResponseDto.class);

        System.out.println("Satellite coordinates: " + httpCoordinatesResponseDto.getLatitude() + " " + httpCoordinatesResponseDto.getLongitude());
        System.out.println("Country code: " + httpCoordinatesResponseDto.getCountry_code());
    }

    public static void showPastLocationFromApi(String date) throws IOException, InterruptedException, URISyntaxException {

        Timestamp timestamp = Timestamp.valueOf(date + " 01:02:03.123456789");

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.wheretheiss.at/v1/satellites/25544/positions?timestamps=" + timestamp
                        .toInstant().getEpochSecond()
                        + "&units=kilometers"))
                .build();

        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(getResponse.body());

        HttpSatteliteResponseDto[] httpSatteliteResponseDto = gson.fromJson(getResponse.body(), HttpSatteliteResponseDto[].class);

        String latitude = httpSatteliteResponseDto[0].getLatitude();
        String longitude = httpSatteliteResponseDto[0].getLongitude();

        System.out.println(latitude);
        System.out.println(longitude);

        HttpRequest getRequest2 = HttpRequest.newBuilder()
                .uri(new URI("https://api.wheretheiss.at/v1/coordinates/" + latitude + "," + longitude))
                .build();

        System.out.println(getRequest2);
        Gson gson2 = new Gson();
        HttpClient httpClient2 = HttpClient.newHttpClient();
        HttpResponse<String> getResponse2 = httpClient2.send(getRequest2, HttpResponse.BodyHandlers.ofString());

        HttpCoordinatesResponseDto httpCoordinatesResponseDto = gson2.fromJson(getResponse2.body(), HttpCoordinatesResponseDto.class);

        System.out.println("Satellite coordinates: " + httpCoordinatesResponseDto.getLatitude() + " " + httpCoordinatesResponseDto.getLongitude());
        System.out.println("Country code: " + httpCoordinatesResponseDto.getCountry_code());
    }
}

