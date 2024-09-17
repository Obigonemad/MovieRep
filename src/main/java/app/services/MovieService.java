package app.services;


import app.dto.ActorDTO;
import app.dto.DirectorDTO;
import app.dto.MovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    public List<MovieDTO> getDanishMovies() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        /*  Henter listen over danske film */
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

            /* Parse JSON for at få filmlisten */
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            JsonNode movies = jsonNode.get("results");

            /*  For hver film, oprettes en MovieDTO og tilføj actors og director */
            for (JsonNode movie : movies) {
                int movieId = movie.get("id").asInt();
                MovieDTO movieDTO = new MovieDTO();
                movieDTO.setId(movieId);
                movieDTO.setTitle(movie.get("original_title").asText());
                movieDTO.setRating(movie.get("vote_average").asDouble());
                movieDTO.setReleaseDate(LocalDate.parse(movie.get("release_date").asText()));

                // Henter skuespillere og instruktører for filmen
                fetchMovieCredits(client, movieId, movieDTO);

                // Tilføjer MovieDTO til listen
                movieDTOList.add(movieDTO);
            }
        } else {
            System.out.println("Error: " + response.statusCode());
        }
        return movieDTOList;
    }

    /* Metode til at hente credits (skuespillere og instruktør) og opdatere MovieDTO */
    private void fetchMovieCredits(HttpClient client, int movieId, MovieDTO movieDTO) throws Exception {
        String creditsUrl = BASE_URL + "/movie/" + movieId + "/credits?language=en-US";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(creditsUrl))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();

            /* Parse JSON for at få cast og crew */
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            List<ActorDTO> actorDTOList = new ArrayList<>();
            DirectorDTO directorDTO = null;

            /* Henter cast (cast bruges i API. Acting - skuespillere) */
            JsonNode cast = jsonNode.get("cast");
            for (JsonNode actorNode : cast) {
                String knownFor = actorNode.get("known_for_department").asText();
                if (knownFor.equals("Acting")) {
                    ActorDTO actorDTO = new ActorDTO();
                    actorDTO.setId(actorNode.get("id").asInt());
                    actorDTO.setName(actorNode.get("name").asText());
                    actorDTOList.add(actorDTO);
                }
            }

            /* Henter crew ( Crew fra API. instruktører) */
            JsonNode crew = jsonNode.get("crew");
            for (JsonNode crewMember : crew) {
                String knownFor = crewMember.get("known_for_department").asText();
                if (knownFor.equals("Directing")) {
                    directorDTO = new DirectorDTO();
                    directorDTO.setId(crewMember.get("id").asInt());
                    directorDTO.setName(crewMember.get("name").asText());
                    break;  // Hvis du kun vil have én instruktør
                }
            }
            /* Tilføjer skuespillere og instruktør til MovieDTO */
            movieDTO.setActors(actorDTOList);
            movieDTO.setDirector(directorDTO);
        } else {
            System.out.println("Error: " + response.statusCode());
        }
    }
}
