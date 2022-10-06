package mmt.json;

import java.sql.Date;
import java.sql.Time;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;

public class MovieModuleTest {

    private static ObjectMapper mapper;

    
    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new MovieModule());
        
    }

    private final static String movieWithOneRating = "{\"title\":\"Bond\",\"releaseDate\":\"3903-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false}";
    private final static String movieListWithThreeMovies = "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}";
    private final static String duplicateMovieList = "{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}";

    private static Stream<Arguments> testMovieListDeserializer() {
        return Stream.of(
            Arguments.arguments("James Bond ", new Time(2,2,0), new Date(2022-1900, 8, 9)),
            Arguments.arguments("Lange flate ballær", new Time(2, 3, 0), new Date(2022-1900, 8, 5)),
            Arguments.arguments("iodhosa", new Time(2, 2, 0), new Date(2022-1900, 8, 2))
        );
    }

    private static Stream<Arguments> testIllegalInputMovieListDeserializer() {
        return Stream.of(
            Arguments.arguments("{\"movies\":[{\"tite\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}"),
            Arguments.arguments("{\"movies\":[{\"title\":\"James Bond \",\"reeaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}"),
            Arguments.arguments("{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duraion\":\"02:02:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}"),
            Arguments.arguments("{\"movies\":[{\"title\":\"James Bond \",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchist\":false},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false}]}")
        );
    }

    @Test
    @DisplayName("Test that trying to deserialize something that is not a rating object returns null")
    public void testIllegalRatingInputDeserializer() {
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            mapper.readValue("This is not a rating object", Rating.class);
        }, "When trying to deserialize a ratingobject that isn on the correct format, a jsonprocessingexception is to be thrown");

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
        Date date = new Date(2002, 12, 2);
        Time time = new Time(1, 50, 0);
        Movie movie = new Movie("Bond", time, date);
        movie.setRating(new Rating(9, "Very good."));
        try {
            Assertions.assertEquals(movieWithOneRating, mapper.writeValueAsString(movie));
        } catch (JsonProcessingException e) {
            Assertions.fail();
        }
    }

    @Test
    @DisplayName("Test that the deserializers gets the correct object back from a serialiation")
    public void testMovieDeserializer() {
        Date date = new Date(2002, 12, 2);
        Time time = new Time(1, 50, 0);
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
        movieList.addMovie(new Movie("James Bond ", new Time(2,2,0), new Date(2022-1900, 8, 9)));
        movieList.addMovie(new Movie("Lange flate ballær", new Time(2, 3, 0), new Date(2022-1900, 8, 5)));
        movieList.addMovie(new Movie("iodhosa", new Time(2, 2, 0), new Date(2022-1900, 8, 2)));

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
}
