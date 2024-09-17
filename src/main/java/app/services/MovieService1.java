package app.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieService1 {

    public static void main(String[] args) {
        // Opret HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Opret forespørgsel
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=da&page=1&region=DK&release_date.gte=2019-01-01&release_date.lte=2024-12-31&sort_by=popularity.desc&with_origin_country=DK"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhODUyNThiYjUwOGE5MDM3MWE4NWRkMzRlZGY1ZTUyMyIsIm5iZiI6MTcyNjU2MTg0OS44NjM1MzMsInN1YiI6IjY2ZTE2Y2U4MDAwMDAwMDAwMDIyYWJjOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.oP59a5lAHZGHNHykBDK6DQh2UwWoN2GYQE6mE45y7KQ")
                .GET()
                .build();

        try {
            // Send forespørgsel og få svar
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Kontroller om statuskoden er 200 (OK)
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Opret ObjectMapper til at behandle JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Konverter JSON til Java-objekt (Map eller specifik DTO)
                Map<String, Object> jsonData = objectMapper.readValue(responseBody, Map.class);

                // Udskriv JSON-data (eller behandl dem yderligere)
                System.out.println(jsonData);
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}