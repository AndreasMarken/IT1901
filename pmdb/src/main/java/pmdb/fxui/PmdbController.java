package pmdb.fxui;

import java.sql.Date;
import java.sql.Time;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import pmdb.core.Movie;

public class PmdbController {

    ObservableList<Movie> movieList= FXCollections.observableArrayList();

    @FXML
    ListView<Movie> movieView = new ListView<>(movieList);


    @FXML
    public void handleAddMovie() {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("PMDB");
        dialog.setHeaderText("Add a movie to the database");
                
        // Set the button types.
        ButtonType addMovie = new ButtonType("Add movie", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addMovie, ButtonType.CANCEL);
        
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField title = new TextField();
        title.setPromptText("Title");

        DatePicker releaseDate = new DatePicker();
        releaseDate.setPromptText("Release date");

        SpinnerValueFactory<Integer> hoursValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 85, 0);
        Spinner<Integer> durationHours = new Spinner<>(hoursValue);

        SpinnerValueFactory<Integer> minutesValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 85, 0);
        Spinner<Integer> durationMinutes = new Spinner<>(minutesValue);        
        
        grid.add(new Label("Title:"), 0, 0);
        grid.add(title, 1, 0);

        grid.add(new Label("Release date:"), 0, 1);
        grid.add(releaseDate, 1, 1);

        grid.add(new Label("Duration:"), 0, 3);

        grid.add(new Label("Hours:"), 0, 4);
        grid.add(durationHours, 1, 4);

        grid.add(new Label("Minutes:"), 2, 4);
        grid.add(durationMinutes, 4, 4);
                
        dialog.getDialogPane().setContent(grid);
        
        Platform.runLater(() -> title.requestFocus());
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addMovie) {
                movieList.add(new Movie(title.getText(), new Time(durationHours.getValue(), durationMinutes.getValue(), 00), Date.valueOf(releaseDate.getValue())));
                movieView.getItems().setAll(movieList);
            }
            return null;
        });
        
        dialog.showAndWait();
    }
}
