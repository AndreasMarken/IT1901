package mmt.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import mmt.core.IActor;
import mmt.core.Movie;

/**
 * Class to serialize (object to text) Movie objects.
 */
public class MovieSerializer extends JsonSerializer<Movie> {

    /**
     * Method to serialize (object to text) Movie objects.
     * Format:
     * {
     * "title": "title"
     * "releaseDate": "yyyy-mm-dd"
     * "duration": "hh:mm:ss"
     * "rating": [...]
     * "watchlist": true/false
     * "cast": [...]
     * }
     *
     * @param movie Movie object to serialize
     * @param jsonGen JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException Method could throw IOException
     */
    @Override
    public void serialize(Movie movie, JsonGenerator jsonGen, SerializerProvider serializerProvider)
        throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("title", movie.getTitle());
        jsonGen.writeStringField("releaseDate", movie.getReleaseDate().toString()); //toString: yyyy-mm-dd
        jsonGen.writeStringField("duration", movie.getDuration().toString()); //toString: hh:mm:ss
        jsonGen.writeObjectField("rating", movie.getRating());
        jsonGen.writeBooleanField("watchlist", movie.getWatchlist());
        jsonGen.writeArrayFieldStart("cast");
        // try {
        if (movie.getCast() == null) {
            jsonGen.writeNull();
        } else {
            for (IActor actor : movie.getCast()) {
                jsonGen.writeObject(actor);
            }
        }
        // } catch (NullPointerException e) {
        //     //No actors
        //     jsonGen.writeNull();
        // }
        jsonGen.writeEndArray();
        jsonGen.writeStringField("ID", movie.getID());
        jsonGen.writeEndObject();
    }
}
