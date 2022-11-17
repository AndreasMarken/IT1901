package mmt.fxui;

import java.sql.Date;
import java.sql.Time;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mmt.core.Actor;
import mmt.core.IActor;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class StatisticControllerTest extends ApplicationTest {
    private StatisticController statisticController;
    private MovieList movieList;
    private IMovie movie1 = new Movie("Shawshank Redemption", Time.valueOf("02:22:00"), Date.valueOf("1994-01-06"));
    private IMovie movie2 = new Movie(
        "James Bond no time to die",
        Time.valueOf("02:43:00"),
        Date.valueOf("2022-10-01")
    );
    private IMovie movie3 = new Movie("Joker", Time.valueOf("02:02:00"), Date.valueOf("2019-10-04"));
    private IMovie movie4 = new Movie("Spider-Man: No way home", Time.valueOf("02:28:00"), Date.valueOf("2021-01-28"));
    private IMovie movie5 = new Movie(
        "Spider-Man: Far from home",
        Time.valueOf("02:10:00"),
        Date.valueOf("2019-07-02")
    );

    private IActor actor1 = new Actor("Actor one");
    private IActor actor2 = new Actor("Actor two");
    private IActor actor3 = new Actor("Actor three");
    private IActor actor4 = new Actor("Actor four");

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistic.fxml"));
        Parent root = loader.load();
        statisticController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        movie1.setOnTakeOfWatchlist(true);
        movie3.setOnTakeOfWatchlist(true);

        movie2.setRating(new Rating(9, "Good movie!"));
        movie4.setRating(new Rating(7, "Okey movie."));

        movie1.addActor(actor1);
        movie2.addActor(actor1);
        movie3.addActor(actor1);
        movie1.addActor(actor3);
        movie2.addActor(actor3);
        movie3.addActor(actor3);
        movie4.addActor(actor3);
        movie1.addActor(actor2);
        movie2.addActor(actor4);
        movie3.addActor(actor4);
        movie2.addActor(actor2);

        this.movieList = new MovieList();
        this.movieList.addMovie(movie1);
        this.movieList.addMovie(movie2);
        this.movieList.addMovie(movie3);
        this.movieList.addMovie(movie4);
        this.movieList.addMovie(movie5);

        //Implementation of actor is pending
        Platform.runLater(
            new Runnable() {

                @Override
                public void run() {
                    statisticController.setMovieList(movieList);
                }
            }
        );
    }

    @Test
    @DisplayName("Test that you cannot set null as the movielist")
    public void testSetMovieList() {
        Assertions.assertThrows(
            NullPointerException.class,
            () -> {
                this.statisticController.setMovieList(null);
            },
            "You cannot set null as the movielist in the statistics view."
        );
    }

    @Test
    @DisplayName("Test that every label displays the correct information")
    public void testSetInformation() {
        WaitForAsyncUtils.waitForFxEvents();
        //Assertions.assertEquals(Float.parseFloat(statisticController.avRating.getText()), 8.0);
        Assertions.assertEquals(8.0, Float.parseFloat(statisticController.avRating.getText()));
        //Assertions.assertEquals(Integer.parseInt(statisticController.numOfMovies.getText()), 5);
        Assertions.assertEquals(5, Integer.parseInt(statisticController.numOfMovies.getText()));
        //Assertions.assertEquals(Integer.parseInt(statisticController.numOfWatchMovies.getText()), 2);
        Assertions.assertEquals(2, Integer.parseInt(statisticController.numOfWatchMovies.getText()));
        //Assertions.assertEquals(statisticController.avWLength.getText(), "02:12:00");
        Assertions.assertEquals("02:12:00", statisticController.avWLength.getText());
        //Assertions.assertEquals(statisticController.avLength.getText(), "02:21:00");
        Assertions.assertEquals("02:21:00", statisticController.avLength.getText());
        //Assertions.assertEquals(statisticController.higActor.getText(), "Actor 3");
        Assertions.assertEquals("Actor three", statisticController.higActor.getText());
    }

    @Test
    @DisplayName("Test Back button")
    public void testBackButton() {
        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();
    }
}
