package mmt.fxui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.Rating;

/**
 * Controller that is used to giving a movie a rating. Sets a new rating to the movie, based on the input values from the user.
 */
public class GiveRatingController {
    private MyMovieTrackerController myMovieTrackerController;

    private IMovie movieToRate;

    @FXML
    private Label movieName;

    @FXML
    private ComboBox<Integer> ratingList;

    @FXML
    private TextArea ratingCommentField;

    @FXML
    private Button cancelButton;

    /**
     * Method to be run upon creating the controller and app.
     * Adds the list of 1-10 to the possible ratings a movie can be given.
     */
    @FXML
    private void initialize() {
        ratingList.getItems().addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ratingList.setValue(1);
    }

    /**
     * Cancels the creating of a review and closes the tab.
     */
    @FXML
    private void cancelEditReview() {
        clearFields();
        myMovieTrackerController.giveRating.getChildren().clear();
    }

    /**
     * Clears the fields in the review-view.
     */
    private void clearFields() {
        ratingCommentField.clear();
        ratingList.setValue(1);
    }

    /**
     * Saves the review to the given movie.
     * Closes the tab and updates the movielistview to the user.
     */
    @FXML
    private void save() {
        String oldMovieID = movieToRate.getID();
        int rating = ratingList.getSelectionModel().getSelectedIndex() + 1;
        String comment = ratingCommentField.getText();

        if (comment.equals("")) {
            movieToRate.setRating(new Rating(rating, ""));
        } else {
            movieToRate.setRating(new Rating(rating, comment));
        }
        cancelEditReview();
        if (movieToRate instanceof Movie) {
            myMovieTrackerController.dataAccess.updateMovie((Movie) movieToRate, oldMovieID);
        }
        myMovieTrackerController.updateMovieListView();
    }

    /**
     * Sets the movietrackercontroller this object is linked with.
     *
     * @param myMovieTrackerController the myMovieTrackerController to be linked with.
     */
    protected void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    /**
     * Fills in the fields in the review-view with the information to the given movie.
     *
     * @param movie The movie to get the name from.
     */
    public void setInformation(IMovie movie) {
        movieToRate = movie;
        movieName.setText(movie.getTitle());
    }
}
