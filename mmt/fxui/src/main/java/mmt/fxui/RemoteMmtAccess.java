package mmt.fxui;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.MovieList;
import mmt.json.MovieModule;

public class RemoteMmtAccess implements IAccess {

    private String apiUri;
    private ObjectMapper oMapper;

    public RemoteMmtAccess(String apiUri){
        this.apiUri = apiUri;
        this.oMapper = new ObjectMapper().registerModule(new MovieModule());
    }

    public URI getUri(){
        return URI.create(apiUri);
    }
    
    public void saveMovieList(MovieList movieList){
        try {
            System.out.println(movieList.getMovies() + "dette er også movielist objekt i remote mmt access");
            String jsonBody = oMapper.writeValueAsString(movieList);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(getUri())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonBody))
                .build();
            final HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body().isEmpty()){
                System.out.println("bodyen er tom i savemovielist remote mmt access");
            }else {
                oMapper.readValue(response.body(), Boolean.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "feilmelding savemovielist");
            System.out.println("eller her");
            throw new RuntimeException("Server is not running: " + e);

        }
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
        } catch (Exception e) {
            System.out.println(e+ "ajkeføalj");
            throw new RuntimeException("Server is not running: " + e);
        }
    }
    

	@Override
	public void setTestMode(boolean testingMode) throws IOException {
		// TODO Auto-generated method stub
		
	}

}

