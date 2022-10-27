package mmt.core;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
    private IMovie starTrek, dune, jamesBond, harryPotter;
    private Date starTrekD, duneD, jamesBondD, harryPotterD;
    private Time starTrekT, duneT, jamesBondT, harryPotterT;
    private IRating rating;
    private MovieList movieList = new MovieList();

    

    @BeforeEach
    public void setUp(){
        starTrekD = Date.valueOf("1990-06-22");
        starTrekT = Time.valueOf("02:00:00");
        starTrek = new Movie("Star Trek", starTrekT, starTrekD);
        rating = new Rating(8);
        duneT = Time.valueOf("02:30:00");
        harryPotterT = Time.valueOf("01:30:40");
        jamesBondT = Time.valueOf("03:20:20");
        duneD = Date.valueOf("2001-06-02");
        harryPotterD = Date.valueOf("2010-05-02");
        jamesBondD = Date.valueOf("2016-11-02");
        dune = new Movie("Dune", duneT, duneD);
        harryPotter = new Movie("Harry Potter", harryPotterT, harryPotterD);
        jamesBond = new Movie("James Bond", jamesBondT, jamesBondD);
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
        Assertions.assertEquals(starTrekT , starTrek.getDuration());
        Assertions.assertEquals(starTrekD, starTrek.getReleaseDate());
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

    @Test
    @DisplayName("Test add and remove function in MovieList")
    public void testAddRemovefunctions(){
        movieList.addMovie(dune);
        movieList.addMovie(harryPotter);
        Assertions.assertEquals((List<IMovie>) movieList.getMovies(), List.of(dune, harryPotter));
        movieList.addMovie(jamesBond);
        movieList.removeMovie(dune);
        movieList.removeMovie(harryPotter);
        movieList.addMovie(starTrek);
        Assertions.assertEquals((List<IMovie>) movieList.getMovies(), List.of(jamesBond,starTrek));

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            movieList.addMovie(null);
            
        });
        Assertions.assertEquals("You cannot add null to the movielist.", exception.getMessage());

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            movieList.addMovie(starTrek);
            
        });
        Assertions.assertEquals("You cannot add a movie that is already added.", exception2.getMessage());

    }


    @Test
    @DisplayName("Test setters and toString methods")
    public void testSettersToString(){
        Assertions.assertEquals("Movie title: " + starTrek.getTitle() + "\n"
        + "Duration: 02 hours 00 minutes" + "\n"
        + "Release date: " + starTrek.getReleaseDate() + "\n"
        + "Rating: " + starTrek.getRating() + "\n"
        + "Watchlist: " + starTrek.getWatchlist() + "\n", starTrek.toString());
       
        starTrek.setDuration(duneT);
        starTrek.setTitle("Dune");
        starTrek.setReleaseDate(duneD);
        Assertions.assertEquals(starTrek.toString(), dune.toString());
    }

}

    
