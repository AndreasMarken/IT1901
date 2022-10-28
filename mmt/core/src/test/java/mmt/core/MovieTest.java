package mmt.core;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MovieTest {
    private IMovie starTrek, dune, jamesBond, harryPotter;
    private Date starTrekD, duneD, jamesBondD, harryPotterD;
    private Time starTrekT, duneT, jamesBondT, harryPotterT;
    private IRating rating;
    private MovieList movieList = new MovieList();

    

    @BeforeEach
    public void setUp(){
        starTrekD = new Date(1990, 05, 22);
        starTrekT = new Time(02, 00, 00);
        starTrek = new Movie("Star Trek", starTrekT, starTrekD);
        rating = new Rating(8);
        duneT = new Time(2, 30, 0);
        harryPotterT = new Time(1, 30, 40);
        jamesBondT = new Time(3, 20, 20);
        duneD = new Date(2001, 05, 02);
        harryPotterD = new Date(2010, 04, 22);
        jamesBondD = new Date(2016, 10, 02);
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
        + "Duration: " + starTrek.getDuration().getHours() + " hours " + starTrek.getDuration().getMinutes() + " minutes" + "\n"
        + "Release date: " + starTrek.getReleaseDate() + "\n"
        + "Rating: " + starTrek.getRating() + "\n"
        + "Watchlist: " + starTrek.getWatchlist() + "\n", starTrek.toString());
       
        starTrek.setDuration(duneT);
        starTrek.setTitle("Dune");
        starTrek.setReleaseDate(duneD);
        Assertions.assertEquals(starTrek.toString(), dune.toString());
    }

    @Test
    @DisplayName("Test adding an actor")
    public void testAddActor() {
        //Arrange
        IMovie movie = new Movie("Fast and Furious", new Time(02, 00, 00));
        IActor vinDiesel = new Actor("Vin Diesel");

        //Act
        movie.addActor(vinDiesel);

        //Assert
        Assertions.assertEquals(1, movie.getCast().size(), "Movie should contain one, and only one actor");
        Assertions.assertTrue(movie.getCast().contains(vinDiesel));      
    }

    @Test
    @DisplayName("Test that an actor can be removed from the cast of movie")
    public void testRemoveActor() {
        //Arrange
        IMovie movie = new Movie("Fast and Furious", new Time(02, 00, 00));
        IActor vinDiesel = new Actor("Vin Diesel");

        //Act
        movie.addActor(vinDiesel);
        movie.removeActor(vinDiesel);

        //Assert
        Assertions.assertNull(movie.getCast(), "Movie should contain no actors after lastone removed");
    }

    @Test
    public void testAddMultipleActor() {
        //Arrange
        IMovie movie = new Movie("Fast and Furious", new Time(02, 00, 00));
        String[] actors = {"actoOne", "actoTwo", "actoThree", "actoFour"};

        //Act
        for (String actor : actors) {
            movie.addActor(new Actor(actor));
        }
        String[] castFromMovie = movie.getCast().stream().map(actor -> actor.getName()).toArray(String[]::new);

        //Assert
        Assertions.assertEquals(4, movie.getCast().size(), "Movie should contain four, and only four actors");
        Assertions.assertEquals(actors[0], castFromMovie[0]);
        Assertions.assertEquals(actors[1], castFromMovie[1]);
        Assertions.assertEquals(actors[2], castFromMovie[2]);
        Assertions.assertEquals(actors[3], castFromMovie[3]);
    }

    @Test
    public void testGetCastFromMovieList() {
        //Arrange
        IMovie movie = new Movie("Fast and Furious", new Time(02, 00, 00));
        String[] actors = {"actoOne", "actoTwo", "actoThree", "actoFour"};

        //Act
        movieList.addMovie(movie);
        for (String actor : actors) {
            movie.addActor(new Actor(actor));
        }
        String[] castFromMovie = movieList.getCast(movie).stream().map(actor -> actor.getName()).toArray(String[]::new);

        //Assert
        Assertions.assertEquals(4, movie.getCast().size(), "Movie should contain four, and only four actors");
        Assertions.assertEquals(actors[0], castFromMovie[0]);
        Assertions.assertEquals(actors[1], castFromMovie[1]);
        Assertions.assertEquals(actors[2], castFromMovie[2]);
        Assertions.assertEquals(actors[3], castFromMovie[3]);
    }
}

    
