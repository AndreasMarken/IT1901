package mmt.fxui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MovieModule;

/**
 * RemoteMmtAccess implements IAccess.
 * Handles remote saving/loading.
 */
public class RemoteMmtAccess implements IAccess {
    private String apiUri;
    private ObjectMapper omapper;

    public RemoteMmtAccess(String apiUri) {
        this.apiUri = apiUri;
        this.omapper = new ObjectMapper().registerModule(new MovieModule());
    }

    private URI getUri() {
        return URI.create(apiUri);
    }

    private URI getUriForMovie(String movieTitle) {
        return URI.create(apiUri + movieTitle);
    }

    public void saveMovieList(MovieList movieList) {
        //Never used
    }

    @Override
    public MovieList loadMovieList() throws IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(getUri()).header("Accept", "application/json").GET().build();
        try {
            final HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            return omapper.readValue(response.body(), MovieList.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addMovie(Movie movie) {
        try {
            String jsonBody = omapper.writeValueAsString(movie);
            HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUri())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();
            final HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

            Boolean successfullyAdded = Boolean.parseBoolean(response.body());
            if (!successfullyAdded) {
                System.err.println("Failed to store movie: " + movie.getTitle());
            }
            return successfullyAdded;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMovie(Movie movie, String oldMovieID) {
        try {
            String jsonBody = omapper.writeValueAsString(movie);
            HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUriForMovie(oldMovieID))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonBody))
                .build();
            final HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

            Boolean successfullyAdded = Boolean.parseBoolean(response.body());
            if (!successfullyAdded) {
                System.err.println("Failed to update movie: " + movie.getTitle());
            }
            return successfullyAdded;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        try {
            HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUriForMovie(movie.getID()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

            final HttpResponse<String> response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

            Boolean successfullyAdded = Boolean.parseBoolean(response.body());
            if (!successfullyAdded) {
                System.err.println("Failed to delete movie: " + movie.getTitle());
            }

            return successfullyAdded;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
