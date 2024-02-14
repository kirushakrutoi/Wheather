package ru.kirill.WheatherApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kirill.WheatherApp.model.Location;
import ru.kirill.WheatherApp.model.dto.location.LocationDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class WheatherService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String WEATHER_API_TOKEN;
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather?";


    static {
        try (InputStream stream = WheatherService.class.getClassLoader().getResourceAsStream("\\API.properties")) {
            Properties weatherAPITokenProperties = new Properties();
            weatherAPITokenProperties.load(stream);
            WEATHER_API_TOKEN = weatherAPITokenProperties.getProperty("WEATHER_TOKEN_API");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<LocationDto> getWeatherByCityName(String city){

        try {
            HttpClient client = HttpClient.newHttpClient();

            URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?q="
                    + city
                    + "&units=metric"
                    + "&appid=" + WEATHER_API_TOKEN);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            LocationDto locationsDto = objectMapper.readValue(json, LocationDto.class);

            return Optional.of(locationsDto);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            return Optional.empty();
        }

    }

    public List<LocationDto> getWeatherByCoords(List<Location> locations){
        List<LocationDto> locationDtoList = new ArrayList<>();
        try {
            for(Location location : locations) {
                String lon = location.getLongitude().toString();
                String lat = location.getLatitude().toString();

                HttpClient client = HttpClient.newHttpClient();

                URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?" +
                        "lat=" + lat +
                        "&lon=" + lon
                        + "&units=metric"
                        + "&appid=" + WEATHER_API_TOKEN);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(uri)
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                LocationDto locationDto = objectMapper.readValue(json, LocationDto.class);

                locationDtoList.add(locationDto);
            }
            return locationDtoList;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return new ArrayList<>();
        }
    }

    public void test(){
        System.out.println("Test: ");
    }

}
