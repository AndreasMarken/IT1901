package mmt.json;

//import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mmt.core.Actor;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;

/**
 * Class Moviemodule that extends SimpleModule.
 */
public class MovieModule extends SimpleModule {
    private static final String NAME = "MovieModule";

    //private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

    /**
     * Constructor to add all the serializers and deserializers for the project.
     */
    public MovieModule() {
        //super(NAME, VERSION_UTIL.version());
        super(NAME);
        addSerializer(Rating.class, new RatingSerializer());
        addSerializer(Movie.class, new MovieSerializer());
        addDeserializer(Rating.class, new RatingDeserializer());
        addDeserializer(Movie.class, new MovieDeserializer());
        addSerializer(MovieList.class, new MovieListSerializer());
        addDeserializer(MovieList.class, new MovieListDeserializer());
        addSerializer(Actor.class, new ActorSerializer());
        addDeserializer(Actor.class, new ActorDeserializer());
    }
}
