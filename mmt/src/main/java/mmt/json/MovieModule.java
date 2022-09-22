package mmt.json;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import mmt.core.Movie;
import mmt.core.Rating;

public class MovieModule extends SimpleModule {
    private static final String NAME = "MovieModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    public MovieModule() {
        super(NAME, VERSION_UTIL.version());
        addSerializer(Rating.class,  new RatingSerializer());
        addSerializer(Movie.class, new MovieSerializer());
        addDeserializer(Rating.class, new RatingDeserializer());
        addDeserializer(Movie.class, new MovieDeserializer());
    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new MovieModule());
        Movie movie = new Movie("James Bond", new Time(1, 30, 45), new Date(0135, 5, 12));
        movie.setOnTakeOfWatchlist(true);
        movie.setRating(new Rating(2, "fe"));

        try {
            String json = mapper.writeValueAsString(movie);
            Movie movie2 = mapper.readValue(json, Movie.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
