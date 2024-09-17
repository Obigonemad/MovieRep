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
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhODUyNThiYjUwOGE5MDM3MWE4NWRkMzRlZGY1ZTUyMyIsIm5iZiI6MTcyNjU2MTg0OS44NjM1MzMsInN1YiI6IjY2ZTE2Y2U4MDAwMDAwMDAwMDIyYWJjOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.oP59a5lAHZGHNHykBDK6DQh2UwWoN2GYQE6mE45y7KQ";
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // 1. Fetch the list of movies
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
