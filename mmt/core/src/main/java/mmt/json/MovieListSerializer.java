package mmt.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;

/**
 * Class to serialize (object to text) MovieList objects.
 */
public class MovieListSerializer extends JsonSerializer<MovieList> {

    /**
     * Method to serialize (object to text) MovieList objects.
     *
     * @param movieList MovieList object to serialize
     * @param jsonGen JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException Method could throw IOException
     */
    @Override
    public void serialize(MovieList movieList, JsonGenerator jsonGen, SerializerProvider serializerProvider)
        throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeArrayFieldStart("movies");
        for (IMovie movie : movieList) {
            if (movie instanceof Movie) {
                jsonGen.writeObject(movie);
            }
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}
