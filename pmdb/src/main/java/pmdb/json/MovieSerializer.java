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
     * "title": title
     * "releaseDate": dd.mm.yyyy
     * "duration": hh.mm
     * "rating": [...]
     * "watchlist": true/false
     * }
     */

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("title", movie.getTitle());
        jsonGen.writeStringField("realeaseDate", movie.getReleaseDate().toString());
        jsonGen.writeStringField("duration", movie.getDuration().toString());
        jsonGen.writeArrayFieldStart("rating");
        for (Rating rating : movie.getRating()) {
            jsonGen.writeObject(rating);
        }
        jsonGen.writeEndArray();
        jsonGen.writeBooleanField("watchlist", movie.getWatchlist());
    }
    
}
