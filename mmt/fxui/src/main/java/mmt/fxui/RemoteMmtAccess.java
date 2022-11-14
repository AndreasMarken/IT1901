package mmt.fxui;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MovieModule;

public class RemoteMmtAccess implements IAccess {

    private String apiUri;
    private ObjectMapper oMapper;

    public RemoteMmtAccess(String apiUri){
        this.apiUri = apiUri;
        this.oMapper = new ObjectMapper().registerModule(new MovieModule());
    }

    private URI getUri(){
        return URI.create(apiUri);
    }

    private URI getUriForMovie(String movieTitle){
        return URI.create(apiUri + movieTitle);
    }
    
    public void saveMovieList(MovieList movieList){
        //Never used
        
    }
    
    public MovieList loadMovieList() throws IOException{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(getUri())
            .header("Accept", "application/json")
            .GET()
            .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return oMapper.readValue(response.body(), MovieList.class);
        } catch (IOException | InterruptedException  e) {
            throw new RuntimeException(e);
        }
    }
    

	@Override
	public void setTestMode(boolean testingMode) throws IOException {
		//Never used	
	}

    @Override
    public void addMovie(Movie movie) {
        try {
            String jsonBody = oMapper.writeValueAsString(movie);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(getUri())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonBody))
                .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            
            
            Boolean successfullyAdded = oMapper.readValue(response.body(), Boolean.class);
            if (!(successfullyAdded != null && successfullyAdded)) {
                System.err.println("Failed to store movie: " + movie.getTitle());
            }
            
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
            }
        
    }

    @Override
    public void updateMovie(Movie movie, String oldMovieID) {
        try {
            String jsonBody = oMapper.writeValueAsString(movie);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(getUriForMovie(oldMovieID))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonBody))
                .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            
            
            Boolean successfullyAdded = oMapper.readValue(response.body(), Boolean.class);
            if (!(successfullyAdded != null && successfullyAdded)) {
                System.err.println("Failed to update movie: " + movie.getTitle());
            }
            
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
                }
    }

    @Override
    public void deleteMovie(Movie movie) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(getUriForMovie(movie.getID()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
            
            final HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
           
            Boolean successfullyAdded = oMapper.readValue((String) response.body(), Boolean.class);
            if (!(successfullyAdded != null && successfullyAdded)) {
                System.err.println("Failed to delete movie: " + movie.getTitle());
            }
        }                        
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

               
    
        
    }

    

}

