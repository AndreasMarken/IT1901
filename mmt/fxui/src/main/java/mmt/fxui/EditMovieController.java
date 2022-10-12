package mmt.fxui;

import java.sql.Time;
import java.sql.Date;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import mmt.core.IMovie;
import mmt.core.Movie;

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
    protected Label errorMessage;


    private IMovie movie;

    @FXML
    private void edit() {
        if (isValidTitleName(movieTitleField.getText())) {
            String title = movieTitleField.getText();
            if (title.equals("") || title.equals(null)) {
                errorMessage.setText("The title name is not valid.");
                return;
            }
            Time time = new Time(hours.getValue(), minutes.getValue(), 0);
            if (time.getHours() <= 00 && time.getMinutes() <= 00) {
                errorMessage.setText("The movie must have a duration.");
                return;
            }
            try {
                date.getValue().getYear();
                date.getValue().getMonth();
                date.getValue().getDayOfMonth();
                
            } catch (NullPointerException e) {
                errorMessage.setText("You must choose a valid date.");
                return;
            }
            Date releaseDate = new Date(date.getValue().getYear()-1900, date.getValue().getMonthValue()-1, date.getValue().getDayOfMonth());
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

    private void editExistingMovie(String title, Time duration, Date releaseDate, boolean watchList) {
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setReleaseDate(releaseDate);
        movie.setOnTakeOfWatchlist(watchList);
    }

    @FXML
    private void cancelEditMovie() {
        this.movie = null;
        clearInputFields();
        myMovieTrackerController.hideEditMovie(false);
    }

    private void clearInputFields() {
        movieTitleField.clear();
        hours.decrement(hours.getValue()%24);
        minutes.decrement(minutes.getValue()%60);
        date.setValue(null);
        watchListCheckBox.setSelected(false);
    }

    public void setMyMovieTrackerController(MyMovieTrackerController myMovieTrackerController) {
        this.myMovieTrackerController = myMovieTrackerController;
    }

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

    private boolean isValidTitleName(String title) {
        IMovie movie = myMovieTrackerController.getMovieList().getMovie(title);
        if (movie == null || movie == this.movie) {
            return true;
        }
        return false;
    }
}
