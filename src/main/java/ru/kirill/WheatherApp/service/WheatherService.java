package ru.kirill.WheatherApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kirill.WheatherApp.model.dto.location.LocationsDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public Optional<LocationsDto> getWeatherByCityName(String city){

        try {
            HttpClient client = HttpClient.newHttpClient();
            URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?q=" +
                    "units=metric"
                    + "&city="
                    + city
                    + "&appid="
                    + WEATHER_API_TOKEN);

            System.out.println(uri);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(
                            "https://api.openweathermap.org/data/2.5/weather?q="
                                    + city
                                    +"&units=metric"
                                    + "&appid="
                                    + WEATHER_API_TOKEN))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            System.out.println(json);

            LocationsDto locationsDto = objectMapper.readValue(json, LocationsDto.class);

            System.out.println(locationsDto);

            return Optional.of(locationsDto);

        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void test(){
        System.out.println("Test: ");
    }

}
