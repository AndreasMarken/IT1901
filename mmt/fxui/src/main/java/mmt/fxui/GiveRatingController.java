package mmt.fxui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mmt.core.IMovie;
import mmt.core.Rating;

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

    @FXML
    private void initialize() {
        ratingList.getItems().addAll(List.of(1,2,3,4,5,6,7,8,9,10));
        ratingList.setValue(1);
    }

    @FXML
    private void cancelEditReview() {
        clearFields();

        myMovieTrackerController.giveRating.getChildren().clear();
    }

    private void clearFields() {
        ratingCommentField.clear();
        
        ratingList.setValue(1);
    }

    @FXML
    private void save() {
        int rating = ratingList.getSelectionModel().getSelectedIndex()+1;
        String comment = ratingCommentField.getText();

        if (comment.equals("")) {
            movieToRate.setRating(new Rating(rating));
        } else {
            movieToRate.setRating(new Rating(rating, comment));
        }
        cancelEditReview();
        myMovieTrackerController.updateMovieListView();
    }

    public void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    public void setInformation(IMovie movie) {
        movieToRate = movie;
        movieName.setText(movie.getTitle());
    }
}
