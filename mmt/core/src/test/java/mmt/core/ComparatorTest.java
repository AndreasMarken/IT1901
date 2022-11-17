package mmt.core;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComparatorTest {
    private IMovie dune, harryPotter, jamesBond;
    private Time duneT, harryPotterT, jamesBondT;
    private Date duneD, harryPotterD, jamesBondD;
    private IRating duneIR, harryPotterIR, jamesBondIR;
    private MovieList movieList = new MovieList();

    @BeforeEach
    public void setUp() {
        duneT = Time.valueOf("02:30:00");
        harryPotterT = Time.valueOf("01:30:40");
        jamesBondT = Time.valueOf("03:20:20");
        duneD = Date.valueOf("2001-06-02");
        harryPotterD = Date.valueOf("2010-05-02");
        jamesBondD = Date.valueOf("2016-11-02");
        dune = new Movie("Dune", duneT, duneD);
        harryPotter = new Movie("Harry Potter", harryPotterT, harryPotterD);
        jamesBond = new Movie("James Bond", jamesBondT, jamesBondD);
        duneIR = new Rating(5);
        harryPotterIR = new Rating(8);
        jamesBondIR = new Rating(10);
        dune.setRating(duneIR);
        harryPotter.setRating(harryPotterIR);
        jamesBond.setRating(jamesBondIR);
        movieList.addMovie(dune);
        movieList.addMovie(harryPotter);
        movieList.addMovie(jamesBond);
    }

    @Test
    @DisplayName("Testing method that sorts by rating.")
    public void checkSortByHighestRating() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByHighestRating());
        Assertions.assertEquals(movieList.getMovies(), List.of(jamesBond, harryPotter, dune));
    }

    @Test
    @DisplayName("Testing method sorts by title.")
    public void checkSortByTitile() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByTitle());
        Assertions.assertEquals(movieList.getMovies(), List.of(dune, harryPotter, jamesBond));
    }

    @Test
    @DisplayName("Testing method sorting by duration.")
    public void checkSortByDuration() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByDuration());
        Assertions.assertEquals(movieList.getMovies(), List.of(harryPotter, dune, jamesBond));
    }
}
