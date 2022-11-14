package mmt.fxui;

import java.io.IOException;

import mmt.core.Movie;
import mmt.core.MovieList;

/**
 * Interface accesstype
 * Makes it more readable in MyMovieTracker
 */
public interface IAccess{

    /*
     *
     */
    public void saveMovieList(MovieList movieList) throws IOException;

    /*
     *
     */
    public MovieList loadMovieList() throws IOException;


    public void setTestMode(boolean testingMode) throws IOException;

    public void addMovie(Movie movie);

    public void updateMovie(Movie movie, String oldMovieID);

    public void deleteMovie(Movie movie);
        
    
}

// public boolean putMovieList(MovieList movieList) {
//     try {
//       String jsonBody = objectMapper.writeValueAsString(movieList);
//       HttpRequest request = HttpRequest.newBuilder(baseUri)
//           .header("Accept", "application/json")
//           .header("Content-Type", "application/json")
//           .PUT(BodyPublishers.ofString(jsonBody))
//           .build();
//       final HttpResponse<String> response = HttpClient.newBuilder().build()
//           .send(request, HttpResponse.BodyHandlers.ofString());
//       return objectMapper.readValue(response.body(), Boolean.class);
//     } catch (Exception e) {
//       throw new RuntimeException("Server is not running: " + e);
//     }
//   }