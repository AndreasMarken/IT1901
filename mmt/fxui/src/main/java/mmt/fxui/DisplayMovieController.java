package mmt.fxui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mmt.core.IMovie;

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
    private Label ratingComment;

    private IMovie movie;

    private MyMovieTrackerController myMovieTrackerController;

    protected void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    public void setMovie(IMovie movie) {
        this.movie = movie;
    }

    public void setMovieInformation() {
        title.setText(movie.getTitle());
        duration.setText(String.format("%02d:%02d", movie.getDuration().getHours(), movie.getDuration().getMinutes()));
        watchList.setText(movie.getWatchlist() ? "Watchlist" : "Not on watchlist");
        releaseDate.setText(movie.getReleaseDate().toString());
        if (movie.getRating() == null) {
            ratingScore.setText("You have not rated this movie yet.");
            ratingComment.setText("You have not rated this movie yet.");
        } else {
            ratingScore.setText(movie.getRating().getRating() + "/10");
            ratingComment.setText(movie.getRating().getComment());
        }
    }

    @FXML
    private void editMovie() {
        myMovieTrackerController.editMovie(movie);
    }

    @FXML
    private void deleteMovie() {
        myMovieTrackerController.deleteMovie(this.movie);
    }

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
