package mmt.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import mmt.core.Actor;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MyMovieConfigTest {
    private static MyMovieConfig myMovieConfig;
    private static String userHomeName;

    @BeforeAll
    public static void setUp() {
        myMovieConfig = new MyMovieConfig();
        userHomeName = System.getProperty("user.home");

        myMovieConfig.setFilePath("test");

        myMovieConfig.makefile(myMovieConfig.getPath());
    }

    @Test
    public void testFilePath() {
        Assertions.assertEquals(userHomeName + "/it1901/mmt/serverfiles/test", myMovieConfig.getPath().toString());

        Assertions.assertEquals(userHomeName + "/it1901/mmt/serverfiles/test", myMovieConfig.getFile().toString());
    }

    @Test
    public void testCreateOMapper() {
        ObjectMapper mapper = MyMovieConfig.createOMapper();

        Assertions.assertTrue(mapper.canSerialize(Movie.class));
        Assertions.assertTrue(mapper.canSerialize(Actor.class));
        Assertions.assertTrue(mapper.canSerialize(MovieList.class));
        Assertions.assertTrue(mapper.canSerialize(Rating.class));
    }

    @Test
    public void testSaveMovieList() {
        MovieList movieList = new MovieList();
        movieList.addMovie(new Movie("test", Time.valueOf("02:00:00"), Date.valueOf("1990-06-22")));
        Assertions.assertTrue(myMovieConfig.saveMovieList(movieList));
    }

    @Test
    public void testLegalLoadMovieList() {
        MovieList movieList = new MovieList();
        movieList.addMovie(new Movie("test", Time.valueOf("02:00:00"), Date.valueOf("1990-06-22")));
        myMovieConfig.saveMovieList(movieList);

        try {
            myMovieConfig.loadMovieList();
        } catch (IOException e) {
            Assertions.fail();
        }

        try {
            Assertions.assertEquals(1, myMovieConfig.loadMovieList().getMovies().size());
        } catch (IOException e) {
            Assertions.fail();
        }
    }
}
