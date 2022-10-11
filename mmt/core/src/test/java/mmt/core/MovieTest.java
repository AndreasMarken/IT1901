package mmt.core;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
    private IMovie starTrek;
    private Date releaseDate;
    private Time duration;
    private IRating rating;

    @BeforeEach
    public void setUp(){
        releaseDate = new Date(1990, 05, 22);
        duration = new Time(02, 00, 00);
        starTrek = new Movie("Star Trek", duration, releaseDate);
        rating = new Rating(8);
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
    @DisplayName("Test watchlist function and if getRatingNumber returns 0 when it is null")
    public void testFunctions(){
        starTrek.setOnTakeOfWatchlist(true);
        Assertions.assertEquals(starTrek.getWatchlist(), true);
        starTrek.setOnTakeOfWatchlist(false);
        Assertions.assertEquals(starTrek.getWatchlist(), false);   
        Assertions.assertEquals(starTrek.getRatingNumber(), 0);
        starTrek.setRating(rating);
        Assertions.assertEquals(starTrek.getRatingNumber(), 8);

    }

}

    
