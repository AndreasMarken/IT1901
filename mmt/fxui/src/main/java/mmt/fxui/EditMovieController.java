package mmt.fxui;

import java.sql.Date;
import java.sql.Time;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import mmt.core.IMovie;
import mmt.core.Movie;

/**
 * Controller that is used to edit or add a new movie to the MyMovieTracker app.
 * The controller is initalised when the app is opened, but the view is hidden
 * until th user has chosen to edit or add a movie to the movielist.
 */
public class EditMovieController {

    @FXML
    private MyMovieTrackerController myMovieTrackerController;

    @FXML 
    private TextField movieTitleField;

    @FXML
    private Spinner<Integer> hours;

    @FXML
    private Spinner<Integer> minutes;

    @FXML
    private DatePicker date;

    @FXML
    private CheckBox watchListCheckBox;

    @FXML
    private Label newEditMovieTab;

    @FXML
    private Label errorMessage;

    private IMovie movie;

    /**
     * The method that is called when the user has completed the editing/adding
     * of a movie. Saves the updated movie or adds the movie to the movielist, and
     * updates the movielistview.
     * If invalid information is given by the user, an errormessage is shown in the view.
     */
    @FXML
    private void edit() {
        if (isValidTitleName(movieTitleField.getText())) {
            String title = movieTitleField.getText();
            if (title.equals("") || title.equals(null)) {
                errorMessage.setText("The title name is not valid.");
                return;
            }
            Time time = new Time(hours.getValue(), minutes.getValue(), 0);
            if (time.getHours() == 00 && time.getMinutes() == 00) {
                errorMessage.setText("The movie must have a duration.");
                return;
            }
            try {
                date.getValue().getYear();
                date.getValue().getMonth();
                date.getValue().getDayOfMonth();
            } catch (NullPointerException e) {
                errorMessage.setText("You must choose a valid date.");
            }
            Date releaseDate = new Date(date.getValue().getYear() - 1900, date.getValue().getMonthValue() - 1, date.getValue().getDayOfMonth());
            boolean watchList = watchListCheckBox.isSelected();

            try {
                if (this.movie == null) {
                    IMovie movie = new Movie(title, time, releaseDate);
                    movie.setOnTakeOfWatchlist(watchList);
                    myMovieTrackerController.addMovie(movie);
                } else {
                    editExistingMovie(title, time, releaseDate, watchList);
                    this.movie = null;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            clearInputFields();
            myMovieTrackerController.updateMovieListView();
            myMovieTrackerController.hideEditMovie(false);
        } else {
            errorMessage.setText("The title name is already used");
        }
    }

    /**
     * Updates the existing movie, that the user has chosen, with the input-information
     * in the app.
     * @param title : The new title to be given
     * @param duration : The new duration to be given
     * @param releaseDate : The new releasedate to be given
     * @param watchList : The new watchlist-status to be given 
     */
    private void editExistingMovie(String title, Time duration, Date releaseDate, boolean watchList) {
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);
        movie.setOnTakeOfWatchlist(watchList);
    }

    /**
     * Cancels the editing/adding of a movie, and hides the view.
     * Clears the inputfields and makes it ready to edit/add a new movie.
     */
    @FXML
    private void cancelEditMovie() {
        this.movie = null;
        clearInputFields();
        myMovieTrackerController.hideEditMovie(false);
    }

    /**
     * Clears the inputfields in the app. 
     */
    private void clearInputFields() {
        movieTitleField.clear();
        hours.decrement(hours.getValue() % 24);
        minutes.decrement(minutes.getValue() % 60);
        date.setValue(null);
        watchListCheckBox.setSelected(false);
    }

    /**
     * The myMovieTrackerController this app is linked, and communicates with.
     * @param myMovieTrackerController the myMovieTrackerController to be set.
     */
    public void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

    /**
     * Initializeses the adding/editing of a movie.
     * @param movie If null is given, a new movie is to be created. Else, a movie will be started to edit.
     */
    protected void editMovie(IMovie movie) {
        errorMessage.setText("");
        this.movie = movie;
        if (this.movie != null) {
            newEditMovieTab.setText("Edit movie");
            fillFields();
        } else {
            newEditMovieTab.setText("New Movie:");
            clearInputFields();
        }
    }

    /**
     * Fills the inputfields with the information of the movie that is to be edited.
     */
    private void fillFields() {
        if (this.movie == null) {
            throw new IllegalStateException("You should not have the oppertunity to edit a movie when you havent selected a movie to edit.");
        }
        movieTitleField.setText(movie.getTitle());  
        hours.increment(movie.getDuration().getHours());
        minutes.increment(movie.getDuration().getMinutes());
        date.setValue(movie.getReleaseDate().toLocalDate());
        watchListCheckBox.setSelected(movie.getWatchlist());
    }

    /**
     * Method to check if a moviename is valid or not.
     * @param title The title to check wheter is valid and free.
     * @return true if the new moviename is valid. fasle if it is not.
     */
    private boolean isValidTitleName(String title) {
        IMovie movie = myMovieTrackerController.getMovieList().getMovie(title);
        if (movie == null || movie == this.movie) {
            return true;
        }
        return false;
    }
}
