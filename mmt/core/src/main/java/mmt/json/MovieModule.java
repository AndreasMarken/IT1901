package mmt.json;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mmt.core.Movie;
import mmt.core.MovieList;
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
        addSerializer(MovieList.class, new MovieListSerializer());
        addDeserializer(MovieList.class, new MovieListDeserializer());
    }
}
