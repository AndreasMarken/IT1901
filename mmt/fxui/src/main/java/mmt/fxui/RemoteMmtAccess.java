package mmt.fxui;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Time;
import java.sql.Date;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;

public class RemoteMmtAccess {

    private String apiUri;
    private ObjectMapper oMapper;

    public RemoteMmtAccess(String apiUri, MyMovieConfig config){
        this.apiUri = apiUri;
        this.oMapper = config.createOMapper();
    }

    public URI getUri(){
        return URI.create(apiUri);
    }
    
    public void storeMovieListInServer(MovieList movieList){
        try {
            String jsonString = oMapper.writeValueAsString(movieList);
/*             System.out.println(jsonString);
 */         HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(getUri())
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(jsonString))
                    .build();
            final HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body() + "dette er bodyen.....");
            if (!oMapper.readValue(response.body(), Boolean.class)){
                System.out.println("Nå da.....");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }

    public MovieList getMovieListStoredInServer(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getUri())
                .header("Accept", "application/json")
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        try {
            HttpResponse<String> response = client
                    .send(request, BodyHandlers.ofString());             
            return oMapper.readValue(response.body(), MovieList.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


 /*    public static void main(String[] args) {
        MovieList list = new MovieList();
        Date date = new Date(2001, 3, 5);
        Time tid = new Time(2, 4, 5);
        Movie movie = new Movie("hei", tid, date);
        list.addMovie(movie);
        MyMovieConfig c = new MyMovieConfig();
        RemoteMmtAccess a = new RemoteMmtAccess("http://localhost:8080/mmt", c);
        a.storeMovieListInServer(list);
    }
 */

}

