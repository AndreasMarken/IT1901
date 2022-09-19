package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {
    private Movie starTrek;
    private Actor andreas, vebjørn, alf, ole;
    private List <Actor> list;
    private Date releaseDate;
    private Time duration;

    @BeforeEach
    public void setUp(){
        andreas = new Actor("Andreas", 21, 'M');
        vebjørn = new Actor("Vebjørn", 21, 'M');
        alf = new Actor("Alf", 21, 'M');
        ole = new Actor("Ole", 21, 'M');
        list = new ArrayList<>();
        list.addAll(List.of(alf,andreas,vebjørn,ole));
        releaseDate = new Date(1990, 05, 22);
        duration = new Time(02, 00, 00);
        starTrek = new Movie("Star Trek", duration, list, releaseDate);
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
        Assertions.assertEquals(list , starTrek.getActors());
        Assertions.assertEquals(releaseDate, starTrek.getReleaseDate());
        
    }

    @Test
    @DisplayName("Testing the validate method checkIfNull() and that it works for every type of inputvalue")
    public void testCheckIfNull(){
        Assertions.assertDoesNotThrow(()-> {
            Movie movie = new Movie("James Bond", duration, list, releaseDate);
         });
        
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie2 = new Movie("James Bond", null, list, releaseDate);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception.getMessage());

        Exception exception2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie3 = new Movie(null, duration, list, releaseDate);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception2.getMessage());

        Exception exception3 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie4 = new Movie("James Bond", duration, null, releaseDate);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception3.getMessage());


        Exception exception4 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Movie movie2 = new Movie("James Bond", duration, list, null);
        });

        Assertions.assertEquals("Input value is null on one of the following fields: Title, duration, actors, release date", exception4.getMessage());
    }
    
}
