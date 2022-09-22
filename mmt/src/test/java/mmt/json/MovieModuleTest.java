package mmt.json;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.Movie;
import mmt.core.Rating;

public class MovieModuleTest {

    private static ObjectMapper mapper;

    
    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new MovieModule());
        
    }

    //private final static String movieWithTwoRatings = "{\"title\":\"Bond\",\"releaseDate\":\"3903-01-02\",\"duration\":\"01:50:00\",\"rating\":[{\"rating\":9,\"comment\":\"Very good.\"},{\"rating\":2,\"comment\":\"\"}],\"watchlist\":false}";
    //private final static String movieWithOneRating = "{\"title\":\"Bond\",\"releaseDate\":\"3903-01-02\",\"duration\":\"01:50:00\",\"rating\":[{\"rating\":9,\"comment:\"Very good.\"}],\"watchlist\":false}";
    private final static String movieWithOneRating = "{\"title\":\"Bond\",\"releaseDate\":\"3903-01-02\",\"duration\":\"01:50:00\",\"rating\":[{\"rating\":9,\"comment\":\"Very good.\"}],\"watchlist\":false}";


    @Test
    @DisplayName("Test that the Movie and Rating serializers works as intended.")
    public void testSerializers() {
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
    public void testDeserializers() {
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
}