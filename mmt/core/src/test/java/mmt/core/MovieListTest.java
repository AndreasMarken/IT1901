package mmt.core;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieListTest {


    private IMovie dune, harryPotter, jamesBond;
    private Time duneT, harryPotterT, jamesBondT;
    private Date duneD, harryPotterD, jamesBondD;
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
    }

    @Test
    
}
