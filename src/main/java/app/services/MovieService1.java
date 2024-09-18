package app.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieService1 {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    public static void fetchDanishMovies() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // 1. Fetch the list of Danish movies
        String discoverMoviesUrl = BASE_URL + "/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&release_date.gte=2019-09-17&sort_by=popularity.desc&with_origin_country=DK";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(discoverMoviesUrl))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();

            // Parse JSON to get the movie IDs
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Get the list of movie results
            JsonNode movies = jsonNode.get("results");

            // Loop through each movie and fetch credits by movie ID
            for (JsonNode movie : movies) {
                String movieId = movie.get("id").asText();
                System.out.println("Fetching credits for Movie ID: " + movieId);

                // Call method to fetch credits for each movie
                fetchMovieCredits(client, movieId);
            }
        } else {
            System.out.println("Error: " + response.statusCode());
        }
    }
    // Method to fetch credits using the movie ID
    private static void fetchMovieCredits(HttpClient client, String movieId) throws Exception {
        String creditsUrl = BASE_URL + "/movie/" + movieId + "/credits?language=da";

        HttpRequest creditsRequest = HttpRequest.newBuilder()
                .uri(new URI(creditsUrl))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .GET()
                .build();

        HttpResponse<String> creditsResponse = client.send(creditsRequest, HttpResponse.BodyHandlers.ofString());

        if (creditsResponse.statusCode() == 200) {
            System.out.println("Credits for Movie ID " + movieId + ": " + creditsResponse.body());
        } else {
            System.out.println("Failed to fetch credits for Movie ID " + movieId + ": " + creditsResponse.statusCode());
        }
    }
}
