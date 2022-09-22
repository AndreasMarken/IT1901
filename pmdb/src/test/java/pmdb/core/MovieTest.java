package pmdb.core;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
    private Movie starTrek;
    private Date releaseDate;
    private Time duration;

    @BeforeEach
    public void setUp(){
        releaseDate = new Date(1990, 05, 22);
        duration = new Time(02, 00, 00);
        starTrek = new Movie("Star Trek", duration, releaseDate);
    }


    @Test
    @DisplayName("Testing that the fields in the class is non-accesible without using getters.")
    public void testPrivateFields() {
        PrivateFieldTester.checkPrivateFields(Movie.class);
    }

    @Test
    @DisplayName("Testing that the constructor is working")
    public void testingConstructor(){
        Assertions.assertEquals("Star Trek", starTrek.getTitle());
        Assertions.assertEquals(duration , starTrek.getDuration());
        Assertions.assertEquals(releaseDate, starTrek.getReleaseDate());
        Assertions.assertEquals(false, starTrek.getWatchlist());
        
    }

    @Test
    @DisplayName("Testing the validate method checkIfNull() and that it works for every type of inputvalue")
    public void testCheckIfNull(){
        Assertions.assertDoesNotThrow(()-> {
            Movie movie = new Movie("James Bond", duration, releaseDate);
         });
        
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie2 = new Movie("James Bond", null, releaseDate);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception.getMessage());

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie3 = new Movie(null, duration, releaseDate);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception2.getMessage());


        Exception exception4 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie2 = new Movie("James Bond", duration, null);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception4.getMessage());
    }
    
}
