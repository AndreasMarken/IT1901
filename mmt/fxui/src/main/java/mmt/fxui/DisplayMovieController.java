package mmt.fxui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mmt.core.IActor;
import mmt.core.IMovie;

/**
 * DisplayMovieController the controller that is used to show movies in the movielistview.
 * Sets the movie-information and shows it to the user. Used in the displayMovieListView method.
 */
public class DisplayMovieController {
    @FXML
    private Label title;

    @FXML
    private Label duration;

    @FXML
    private Label watchList;

    @FXML
    private Label releaseDate;

    @FXML
    private Label ratingScore;

    @FXML
    private Label actors;

    @FXML
    private Label ratingComment;

    private IMovie movie;

    private MyMovieTrackerController myMovieTrackerController;

    /**
     * The myMovieTrackerController this app is linked, and communicates with.
     *
     * @param myMovieTrackerController the myMovieTrackerController to be set.
     */
    protected void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    /**
     * Sets the movie of this display.
     *
     * @param movie The movie to be set in the display
     */
    public void setMovie(IMovie movie) {
        this.movie = movie;
    }

    /**
     * Sets the information in the display from the movie stored in the controller.
     */
    public void setMovieInformation() {
        title.setText(movie.getTitle());
        duration.setText(String.format("%02d:%02d", Integer.parseInt(movie.getDuration().toString().substring(0, 2)), Integer.parseInt(movie.getDuration().toString().substring(3, 5))));
        watchList.setText(movie.getWatchlist() ? "Watchlist" : "Not on watchlist");
        releaseDate.setText(movie.getReleaseDate().toString());
        if (movie.getCast() != null) {
            String cast = "";
            for (IActor actor : movie.getCast()) {
                cast += ", " + actor.getName();
            }
            actors.setText(cast.substring(2));
        } else {
            actors.setText("No cast set for this movie");
        }

        if (movie.getRating() == null) {
            ratingScore.setText("You have not rated this movie yet.");
            ratingComment.setText("You have not rated this movie yet.");
        } else {
            ratingScore.setText(movie.getRating().getRating() + "/10");
            ratingComment.setText(movie.getRating().getComment());
        }
    }

    /**
     * Sets up the edit button. Used to edit the movie that is shown in the display.
     * Tells the myMovieTrackerController that this movie is to be edited.
     */
    @FXML
    private void editMovie() {
        myMovieTrackerController.editMovie(movie);
    }

    /**
     * Sets up the delete button. Used to delete the movie that is shown in the display.
     * Tells the myMovieTrackerController that this movie is to be deleted.
     */
    @FXML
    private void deleteMovie() {
        myMovieTrackerController.deleteMovie(this.movie);
    }

    /**
     * Sets up the rate button. Used to rate the movie that is shown in the display.
     * Opens up a new window that starts the rating-view. Loads it with the information
     * that this controller has stored. Tells the myMovieTrackerController that a movie
     * is being rated.
     *
     * @throws IOException if the Rating-view cannot be shown.
     */
    @FXML
    private void rateMovie() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Rating.fxml"));
        Pane ratingPane = fxmlLoader.load();
        GiveRatingController ratingController = fxmlLoader.getController();
        ratingController.setMyMovieTrackerController(myMovieTrackerController);
        ratingController.setInformation(movie);

        myMovieTrackerController.giveRating.getChildren().add(ratingPane);
    }
}
