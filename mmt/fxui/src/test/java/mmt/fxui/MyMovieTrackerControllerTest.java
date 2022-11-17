package mmt.fxui;

import java.time.LocalDate;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import mmt.core.IMovie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class MyMovieTrackerControllerTest extends ApplicationTest {
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

    private void addThreeMovies() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist, "Ryan Gosling");
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie(
            "Test Movie 2",
            releaseDate,
            durationHours,
            durationMinutes - 2,
            false,
            "Dwayne Johnson",
            "Mark Clerk"
        );
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie("Test Movie 3", releaseDate, durationHours, durationMinutes - 3, true, "John", "Jonas", "Joseph");
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyMovieTracker.fxml"));
        Parent root = loader.load();
        myMovieTrackerController = loader.getController();
        myMovieTrackerController.setAccess(new LocalMmtAccess());
        myMovieTrackerController.setTestingMode(true);
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
    @DisplayName("Test that the initialize method sets the correct controllers.")
    public void testInitialize() {
        Assertions.assertNotNull(
            myMovieTrackerController.getEditMovieController(),
            "The EditMovieController should be loaded and not null."
        );
    }

    @Test
    @DisplayName("Test that you can open the window edit/add a movie.")
    public void testOpenAddEditMovieWindow() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertTrue(
            myMovieTrackerController.editMovieWindow.isVisible(),
            "The window that allows the user to edit a movie should be visible."
        );
    }

    @Test
    @DisplayName("Test that you can also close the window that shows up.")
    public void testCloseAddEditMovieWindow() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#cancelButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertFalse(myMovieTrackerController.editMovieWindow.isVisible());
    }

    @Test
    @DisplayName("Test that you cannot add a duplicate movie")
    public void testAddDuplicateMovie() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMoviesBeforeAddingNew = myMovieTrackerController.getMovies().size();

        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();

        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            numberOfMoviesBeforeAddingNew + 1,
            myMovieTrackerController.getMovies().size(),
            "The amount of moviews should be incremented by one after adding a new movie. Not two."
        );

        Assertions.assertEquals(
            "The title name is already used",
            myMovieTrackerController.getEditMovieController().errorMessage.getText(),
            "The errormessage shown to the user was incorrect."
        );
    }

    @Test
    @DisplayName("Test that you can delete a movie from the movielist.")
    public void testDeleteMovie() {
        int numberOfMovies = myMovieTrackerController.getMovies().size();

        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#deleteMovie"));

        Assertions.assertEquals(numberOfMovies, myMovieTrackerController.getMovies().size());
    }

    @Test
    @DisplayName("Test that you can display only the movies on your watchlist")
    public void displayWatchlistMovie() {
        addThreeMovies();

        clickOn("#watchList");
        WaitForAsyncUtils.waitForFxEvents();

        int numberOfMoviesThatShouldBeOnDisplay = myMovieTrackerController
            .getMovies()
            .stream()
            .filter(m -> m.getWatchlist())
            .toList()
            .size();
        int numberOfMoviesOnDisplay = myMovieTrackerController.movieListView.getChildren().size();

        Assertions.assertEquals(numberOfMoviesThatShouldBeOnDisplay, numberOfMoviesOnDisplay);
    }

    @Test
    @DisplayName("Test that you can sort the movielist")
    public void testSortMovieList() {
        addThreeMovies();

        clickOn("#menuButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sortDuration");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            "Test Movie 3",
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            "Test Movie 2",
            ((Label) (lookup("#Movie1").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            movieTitle,
            ((Label) (lookup("#Movie2").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );

        clickOn("#menuButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sortTitle");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            "Test Movie 2",
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            "Test Movie 3",
            ((Label) (lookup("#Movie1").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            movieTitle,
            ((Label) (lookup("#Movie2").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );

        clickOn("#menuButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sortRating");
        WaitForAsyncUtils.waitForFxEvents();

        Assertions.assertEquals(
            "Test Movie 2",
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            "Test Movie 3",
            ((Label) (lookup("#Movie1").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
        Assertions.assertEquals(
            movieTitle,
            ((Label) (lookup("#Movie2").queryAll().stream().findFirst().get()).lookup("#title")).getText()
        );
    }

    @Test
    @DisplayName("Test that you can give a movie a rating")
    public void testGiveRating() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        int ratingScore = 10;
        String comment = "This was a very good movie. 10/10 recommend. Want to watch it again!";

        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#rateMovie"));

        clickOn("#ratingList");
        for (int i = 1; i < ratingScore; i++) {
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        }
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        clickOn("#commentField");
        write(comment);

        clickOn("#submitReview");

        int actualScore = Integer.parseInt(
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#ratingScore")).getText()
                .substring(0, 2)
        );
        String actualComent =
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#ratingComment")).getText();

        Assertions.assertEquals(ratingScore, actualScore);
        Assertions.assertEquals(comment, actualComent);
    }

    @Test
    @DisplayName("Test that you can give a movie a rating without a comment")
    public void testGiveRatingWithoutComment() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
        writeMovie(movieTitle, releaseDate, durationHours, durationMinutes, isOnWatchlist);
        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        int ratingScore = 7;

        clickOn(lookup("#Movie0").queryAll().stream().findFirst().get().lookup("#rateMovie"));

        clickOn("#ratingList");
        for (int i = 1; i < ratingScore; i++) {
            press(KeyCode.DOWN);
            release(KeyCode.DOWN);
        }
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        clickOn("#submitReview");

        int actualScore = Integer.parseInt(
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#ratingScore")).getText()
                .substring(0, 1)
        );
        String actualComent =
            ((Label) (lookup("#Movie0").queryAll().stream().findFirst().get()).lookup("#ratingComment")).getText();

        Assertions.assertEquals(ratingScore, actualScore);
        Assertions.assertEquals("", actualComent);
    }

    @Test
    @DisplayName("Test that you can open the statistic view from the mmt view")
    public void testChangeView() {
        clickOn("#statisticButton");
        WaitForAsyncUtils.waitForFxEvents();
    }
}
