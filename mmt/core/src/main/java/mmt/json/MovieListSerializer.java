package mmt.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;

public class MovieListSerializer extends JsonSerializer<MovieList> {

    @Override
    public void serialize(MovieList movieList, JsonGenerator jsonGen, SerializerProvider serializers) throws IOException {
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
