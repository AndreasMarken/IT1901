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
        duneT = new Time(2, 30, 0);
        harryPotterT = new Time(1, 30, 40);
        jamesBondT = new Time(3, 20, 20);
        duneD = new Date(2001, 05, 02);
        harryPotterD = new Date(2010, 04, 22);
        jamesBondD = new Date(2016, 10, 02);
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
