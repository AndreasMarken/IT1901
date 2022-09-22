package pmdb.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pmdb.core.Movie;
import pmdb.core.Rating;

public class MovieSerializer extends JsonSerializer<Movie> {

    /*
     * Format:
     * {
     * "title": "title"
     * "releaseDate": "yyyy-mm-dd"
     * "duration": "hh:mm:ss"
     * "rating": [...]
     * "watchlist": true/false
     * }
     */

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("title", movie.getTitle());
        jsonGen.writeStringField("releaseDate", movie.getReleaseDate().toString()); //toString: yyyy-mm-dd
        jsonGen.writeStringField("duration", movie.getDuration().toString()); //toString: hh:mm:ss
        jsonGen.writeArrayFieldStart("rating");

        //TODO
        //Movie has no ratinglist yet!

        // for (Rating rating : movie.getRating()) {
        //     jsonGen.writeObject(rating);
        // }
        
        jsonGen.writeObject(movie.getRating());
        jsonGen.writeEndArray();
        jsonGen.writeBooleanField("watchlist", movie.getWatchlist());
    }
    
}
