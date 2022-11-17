package mmt.fxui;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import mmt.core.IActor;
import mmt.core.IMovie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class EditMovieControllerTest extends ApplicationTest {
    private EditMovieController editMovieController;
    private MyMovieTrackerController myMovieTrackerController;
    private String movieTitle = "Test Title";
    private String releaseDate = "06.10.2022";
    private int durationHours = 2;
    private int durationMinutes = 32;
    private boolean isOnWatchlist = true;

    private void writeMovie(
        String movieTitle,
        String releaseDate,
        int durationHours,
        int durationMinutes,
        boolean isOnWatchlist,
        String... cast
    ) {
        clickOn("#movieTitleField");
        WaitForAsyncUtils.waitForFxEvents();
        write(movieTitle);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#date");
        WaitForAsyncUtils.waitForFxEvents();

        try {
            ((DatePicker) lookup("#date").queryAll().stream().findFirst().get()).setValue(
                    LocalDate.of(
                        Integer.parseInt(releaseDate.substring(6)),
                        Integer.parseInt(releaseDate.substring(3, 5)),
                        Integer.parseInt(releaseDate.substring(0, 2))
                    )
                );
        } catch (NullPointerException e) {
            //Skip date if null is used as input
        }

        clickOn("#hours");
        WaitForAsyncUtils.waitForFxEvents();
        write(Integer.toString(durationHours));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#minutes");
        WaitForAsyncUtils.waitForFxEvents();
        write(Integer.toString(durationMinutes));
        WaitForAsyncUtils.waitForFxEvents();

        if (isOnWatchlist) {
            clickOn("#watchListCheckBox");
            WaitForAsyncUtils.waitForFxEvents();
        }

        if (cast != null) {
            for (String actor : cast) {
                clickOn("#actorNameField");
                WaitForAsyncUtils.waitForFxEvents();
                write(actor);
                WaitForAsyncUtils.waitForFxEvents();
                clickOn("#addActorToMovieButton");
                WaitForAsyncUtils.waitForFxEvents();
            }
        }
    }

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyMovieTracker.fxml"));
        Parent root = loader.load();
        myMovieTrackerController = loader.getController();
        myMovieTrackerController.setAccess(new LocalMmtAccess());
        myMovieTrackerController.setTestingMode(true);
        this.editMovieController = myMovieTrackerController.getEditMovieController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void clearMovieListForTestMovies() {
        IMovie movie = myMovieTrackerController.getMovieList().getMovie(movieTitle);
        if (movie != null) {
            Platform.runLater(
                new Runnable() {

                    @Override
                    public void run() {
                        myMovieTrackerController.deleteMovie(movie);
                    }
                }
            );
        }
    }

    @Test
    @DisplayName("Test that adding a valid movie is possible")
    public void testAddValidMovie() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMoviesBeforeAddingNew = myMovieTrackerController.getMovies().size();

        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertFalse(
            myMovieTrackerController.editMovieWindow.isVisible(),
            "The window for adding a new movie should not be visible"
        );

        Assertions.assertEquals(
            numberOfMoviesBeforeAddingNew + 1,
            myMovieTrackerController.getMovies().size(),
            "The amount of moviews should be incremented by one after adding a new movie."
        );

        IMovie addedMovie = myMovieTrackerController.getMovieList().getMovie(movieTitle);

        Assertions.assertEquals(movieTitle, addedMovie.getTitle(),
                       "Wrong movietitle set");
        //               "Wrong duration set");
        Assertions.assertEquals(
            Time.valueOf(Integer.toString(durationHours) + ":" + Integer.toString(durationMinutes) + ":00"),
            addedMovie.getDuration(),
            "Wrong duration set"
        );
        //Assertions.assertEquals(new Date(2022 - 1900, 9, 6), addedMovie.getReleaseDate(),
        //               "Wrong releasedate set");
        Assertions.assertEquals(Date.valueOf("2022-10-06"), addedMovie.getReleaseDate(), "Wrong releasedate set");
        Assertions.assertEquals(isOnWatchlist, addedMovie.getWatchlist(), "Wrong movietitle set");
    }

    @Test
    @DisplayName("Test that you cannot create a movie with an invalid title")
    public void testInvalidTitle() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("", releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            "The title name is not valid.",
            editMovieController.errorMessage.getText(),
            "The errormessage shown to the user was incorrect"
        );
    }

    @Test
    @DisplayName("Test that you cannot create a movie with an invalid releasedate")
    public void testInvalidReleaseDate() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMoviesBeforeAddingNew = myMovieTrackerController.getMovies().size();

        writeMovie(movieTitle, null, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            numberOfMoviesBeforeAddingNew,
            myMovieTrackerController.getMovies().size(),
            "You should not be able to add a movie with an invalid releasedate"
        );

        Assertions.assertEquals(
            "You must choose a valid date.",
            myMovieTrackerController.getEditMovieController().errorMessage.getText(),
            "The errormessage shown to the user was incorrect"
        );
    }

    @Test
    @DisplayName("Test that you cannot create a movie with an invalid duration")
    public void testInvalidDuration() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMoviesBeforeAddingNew = myMovieTrackerController.getMovies().size();

        writeMovie(movieTitle, releaseDate, 0, 0, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            numberOfMoviesBeforeAddingNew,
            myMovieTrackerController.getMovies().size(),
            "You should not be able to add a movie with an invalid title name"
        );

        Assertions.assertEquals(
            "The movie must have a duration.",
            myMovieTrackerController.getEditMovieController().errorMessage.getText(),
            "The errormessage shown to the user was incorrect"
        );
    }

    @Test
    @DisplayName("Test that you can edit an existing movie")
    public void testEditMovie() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMovies = myMovieTrackerController.getMovies().size();

        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#editMovie"));

        String newTitle = "Test Title 2";

        clickOn("#movieTitleField").eraseText(movieTitle.length());
        write(newTitle);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            numberOfMovies,
            myMovieTrackerController.getMovies().size(),
            "The amount of movies should not change when you edit a movie."
        );

        Assertions.assertNotNull(myMovieTrackerController.getMovieList().getMovie(newTitle));

        Assertions.assertNull(myMovieTrackerController.getMovieList().getMovie(movieTitle));

        Assertions.assertFalse(myMovieTrackerController.editMovieWindow.isVisible());
    }

    @Test
    @DisplayName("Test that you can create a movie with cast")
    public void testAddCast() {
        //Arrange
        final String[] expectedCast = {
            "Vin Diesel",
            "Paul Walker",
            "Michelle Rodriguez",
            "Jordana Brewster",
            "Rick Yune",
            "Matt Schulze",
            "Ted Levine",
            "Johnny Strong"
        };

        //Act
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("Fast and Furious", "01.08.2001", 2, 3, false, expectedCast);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        //Assert
        final Collection<IActor> actualCastObjects = myMovieTrackerController
            .getMovieList()
            .getMovie("Fast and Furious")
            .getCast();
        final String[] actualCast = actualCastObjects.stream().map(actor -> actor.getName()).toArray(String[]::new);
        Assertions.assertArrayEquals(expectedCast, actualCast);
    }

    @Test
    @DisplayName("Test that you can remove an actor from the cast")
    public void testRemoveCast() {
        //Arrange
        final String[] inputCast = { "Vin Diesel" };

        //Act
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("Fast and Furious", "01.08.2001", 2, 3, false, inputCast);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#editMovie"));
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#removeActorFromMovie");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        //Assert
        final Collection<IActor> actualCastObjects = myMovieTrackerController
            .getMovieList()
            .getMovie("Fast and Furious")
            .getCast();
        Assertions.assertNull(actualCastObjects);
    }

    @Test
    @DisplayName("Test that you can remove an actor from the cast")
    public void testAddDuplicateCast() {
        //Arrange
        final String[] inputCast = { "Vin Diesel", "Vin Diesel" };

        //Act
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("Fast and Furious", "01.08.2001", 2, 3, false, inputCast);

        //Assert
        Assertions.assertEquals(
            "The actor is already added to the movie",
            myMovieTrackerController.getEditMovieController().errorMessage.getText()
        );
    }

    @Test
    @DisplayName("Test that cast is serialized and deserialized")
    public void testCastIsSerializedAndDeserialized() {
        //Arrange
        final String[] expectedCast = { "First actor", "Second Actor", "Third Actor" };

        //Act
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("Test Serializing Of Cast", "01.08.2001", 2, 3, false, expectedCast);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#editMovie"));

        //Assert
        final Collection<String> castFromListView = new ArrayList<String>(
            myMovieTrackerController.editMovieController.actorListView.getItems()
        );
        final Collection<IActor> actualCastObjects = myMovieTrackerController
            .getMovieList()
            .getMovie("Test Serializing Of Cast")
            .getCast();
        final String[] actualCast = actualCastObjects.stream().map(actor -> actor.getName()).toArray(String[]::new);
        final String[] actualCastFromListView = castFromListView.toArray(String[]::new);

        Assertions.assertArrayEquals(expectedCast, actualCast);
        Assertions.assertArrayEquals(expectedCast, actualCastFromListView);
    }
}
