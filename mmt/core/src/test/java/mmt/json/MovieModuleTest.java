package mmt.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.sql.Time;
import java.util.stream.Stream;
import mmt.core.Actor;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MovieModuleTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new MovieModule());
    }

    private static final String movieWithOneRating =
        "{\"title\":\"Bond\",\"releaseDate\":\"2002-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}";
    private static final String movieWithOneRatingAndAnActor =
        "{\"title\":\"Bond\",\"releaseDate\":\"2002-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false,\"cast\":[{\"name\":\"Daniel Craig\"}],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}";
    private static final String movieListWithThreeMovies =
        "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}";
    private static final String duplicateMovieList =
        "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}";

    private static Stream<Arguments> testMovieListDeserializer() {
        return Stream.of(
            Arguments.arguments("James Bond ", Time.valueOf("02:02:00"), Date.valueOf("2022-09-09")),
            Arguments.arguments("Lange flate ballær", Time.valueOf("02:03:00"), Date.valueOf("2022-09-05")),
            Arguments.arguments("iodhosa", Time.valueOf("02:02:00"), Date.valueOf("2022-09-02"))
        );
    }

    private static Stream<Arguments> testIllegalInputMovieListDeserializer() {
        return Stream.of(
            Arguments.arguments(
                "{\"movies\":[{\"tite\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120001\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}"
            ),
            Arguments.arguments(
                "{\"movies\":[{\"title\":\"James Bond \",\"reeaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120001\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}"
            ),
            Arguments.arguments(
                "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duraion\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120001\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}"
            ),
            Arguments.arguments(
                "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120001\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}"
            )
        );
    }

    @Test
    @DisplayName("Test that trying to deserialize something that is not a rating object returns null")
    public void testIllegalRatingInputDeserializer() {
        Assertions.assertThrows(
            JsonProcessingException.class,
            () -> {
                mapper.readValue("This is not a rating object", Rating.class);
            },
            "When trying to deserialize a ratingobject that isn on the correct format, a jsonprocessingexception is to be thrown"
        );

        try {
            Rating rating = mapper.readValue("\"rating\":9,\"comment\":\"Very good.\"", Rating.class);
            Assertions.assertNull(rating);
            rating = mapper.readValue("{\"rating\":null,\"comment\":\"Very good.\"}", Rating.class);
            Assertions.assertNull(rating);
            rating = mapper.readValue("{\"rating\":9,\"comment\":3}", Rating.class);
            Assertions.assertEquals("", rating.getComment());
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that deserializing valid rating objects works as intended.")
    public void testLegalRatingDeserialize() {
        try {
            Rating ratingObject = mapper.readValue("{\"rating\":9,\"comment\":\"Very good.\"}", Rating.class);

            Assertions.assertEquals(9, ratingObject.getRating());
            Assertions.assertEquals("Very good.", ratingObject.getComment());
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the Movie and Rating serializers works as intended.")
    public void testMovieSerializer() {
        Date date = Date.valueOf("2002-01-02");
        Time time = Time.valueOf("01:50:00");
        Movie movie = new Movie("Bond", time, date, "e65b957e-6415-11ed-81ce-0242ac120002");
        movie.setRating(new Rating(9, "Very good."));
        try {
            Assertions.assertEquals(movieWithOneRating, mapper.writeValueAsString(movie));
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the Movie, Rating and Actor serializers works as intended.")
    public void testMovieSerializerWithActor() {
        Date date = Date.valueOf("2002-01-02");
        Time time = Time.valueOf("01:50:00");
        Movie movie = new Movie("Bond", time, date, "e65b957e-6415-11ed-81ce-0242ac120002");
        movie.setRating(new Rating(9, "Very good."));
        movie.addActor(new Actor("Daniel Craig"));
        try {
            Assertions.assertEquals(movieWithOneRatingAndAnActor, mapper.writeValueAsString(movie));
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the deserializers gets the correct object back from a serialiation")
    public void testMovieDeserializer() {
        Date date = Date.valueOf("2002-01-02");
        Time time = Time.valueOf("01:50:00");
        Movie movie = new Movie("Bond", time, date);
        movie.setRating(new Rating(9, "Very good."));

        Assertions.assertEquals("Bond", movie.getTitle());
        Assertions.assertEquals("Very good.", movie.getRating().getComment());

        try {
            Movie movie2 = mapper.readValue(mapper.writeValueAsString(movie), Movie.class);

            Assertions.assertEquals("Bond", movie2.getTitle());
            Assertions.assertEquals("Very good.", movie2.getRating().getComment());
            Assertions.assertEquals(9, movie2.getRating().getRating());
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test the movielist serializer")
    public void testMovieListSerializer() {
        MovieList movieList = new MovieList();
        movieList.addMovie(
            new Movie(
                "James Bond ",
                Time.valueOf("02:02:00"),
                Date.valueOf("2022-09-09"),
                "e65b957e-6415-11ed-81ce-0242ac120002"
            )
        );
        movieList.addMovie(
            new Movie(
                "Lange flate ballær",
                Time.valueOf("02:03:00"),
                Date.valueOf("2022-09-05"),
                "e65b957e-6415-11ed-81ce-0242ac120002"
            )
        );
        movieList.addMovie(
            new Movie(
                "iodhosa",
                Time.valueOf("02:02:00"),
                Date.valueOf("2022-09-02"),
                "e65b957e-6415-11ed-81ce-0242ac120002"
            )
        );

        try {
            Assertions.assertEquals(movieListWithThreeMovies, mapper.writeValueAsString(movieList));
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Test the movielist deserializer")
    public void testMovieListDeserializer(String title, Time duration, Date releaseDate) {
        try {
            MovieList movieList = mapper.readValue(movieListWithThreeMovies, MovieList.class);
            IMovie movie = movieList.getMovie(title);

            Assertions.assertEquals(title, movie.getTitle());
            Assertions.assertEquals(duration, movie.getDuration());
            Assertions.assertEquals(releaseDate, movie.getReleaseDate());
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Test illegal movieliststring to deserialize")
    public void testIllegalInputMovieListDeserializer(String movieListString) {
        try {
            MovieList movielist = mapper.readValue(movieListString, MovieList.class);
            Assertions.assertEquals(2, movielist.getMovies().size());

            movielist = mapper.readValue("\"{This should not return a movielist}\"", MovieList.class);
            Assertions.assertEquals(movielist.getMovies().size(), 0);
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test adding duplicate movies to movielist")
    public void testDuplicateMovieListDeserializer() {
        try {
            MovieList movieList = mapper.readValue(duplicateMovieList, MovieList.class);
            Assertions.assertEquals(1, movieList.getMovies().size());
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the actor serializer works as intended with correct input")
    public void testActorSerializer() {
        Actor actor = new Actor("Tom Cruise");
        String expectedString = "{\"name\":\"Tom Cruise\"}";

        try {
            Assertions.assertEquals(expectedString, mapper.writeValueAsString(actor));
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the actor deserializer works as intended with correct input")
    public void testActorDeserializer() {
        String inputString = "{\"name\":\"Tom Cruise\"}";
        Actor actor = null;
        try {
            actor = mapper.readValue(inputString, Actor.class);
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }

        Assertions.assertNotNull(actor);
        Assertions.assertEquals("Tom Cruise", actor.getName());
    }

    @Test
    @DisplayName("Test that the actor deserializer works as intended with illegal input")
    public void testActorDeserializerIllegalInput() {
        String inputString = "{name:This is not an actor}";
        Actor actor = null;
        try {
            actor = mapper.readValue(inputString, Actor.class);
        } catch (JsonProcessingException e) {}

        Assertions.assertNull(actor);
    }
}
