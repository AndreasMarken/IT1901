package mmt.fxui;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Collections;


import com.fasterxml.jackson.databind.ObjectMapper;

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
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import mmt.core.Comparators;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.core.Rating;
import mmt.json.MovieModule;

public class MmtController {

    private ObservableList<Movie> movieList= FXCollections.observableArrayList();
    private ObservableList<Movie> onWatchlist= FXCollections.observableArrayList();
    private MovieList movieList2 = new MovieList();
    private ObjectMapper mapper = new ObjectMapper();
    private Boolean bool = false;

    @FXML
    private ListView<Movie> movieView = new ListView<>(movieList);

    @FXML
    private MenuButton rating;

    @FXML
    public void handleAddMovie() {
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("MMT");
        dialog.setHeaderText("Add a movie to the database");
                
        ButtonType addMovie = new ButtonType("Add movie", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addMovie, ButtonType.CANCEL);
        
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

        SpinnerValueFactory<Integer> minutesValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
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
                try{
                    movieList.add(new Movie(title.getText(), new Time(durationHours.getValue(), durationMinutes.getValue(), 00), Date.valueOf(releaseDate.getValue())));
                    movieView.getItems().setAll(movieList);
                    movieList2.addMovie(new Movie(title.getText(), new Time(durationHours.getValue(), durationMinutes.getValue(), 00), Date.valueOf(releaseDate.getValue())));
                }catch(NullPointerException e){
                    movieList.add(new Movie(title.getText(), new Time(durationHours.getValue(), durationMinutes.getValue(), 00)));
                    movieView.getItems().setAll(movieList);
                    movieList2.addMovie(new Movie(title.getText(), new Time(durationHours.getValue(), durationMinutes.getValue(), 00)));
                }
                
            }
            return null;
        });
        
        dialog.showAndWait();
    }

    @FXML
    public void handleSave(){
        mapper.registerModule(new MovieModule());
        //String savingString = "";
        // for (Movie movie : movieList) {
        //     try {
        //         savingString += mapper.writeValueAsString(movie);
        //         mapper.writeValue(new File("mmt/src/main/resources/mmt/json/movie.json"), savingString);
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }
            try {
                mapper.writeValue(new File("../core/src/main/resources/mmt/json/movie.json"), movieList2);
                //src/main/resources/mmt/json/
            } catch (IOException e) {
                System.out.println("fail");
            }   
    }

    @FXML
    public void handleLoad() {
        mapper.registerModule(new MovieModule());
        try {
            
            MovieList tmp = mapper.readValue(new File("../core/src/main/resources/mmt/json/movie.json"), MovieList.class);
            for (IMovie movie : tmp) {
                try {
                    movieList2.addMovie(movie);
                    movieList.add(new Movie(movie.getTitle(), movie.getDuration(), movie.getReleaseDate()));
                    movieView.getItems().setAll(movieList);
                } catch(IllegalArgumentException e) {
                    System.out.println("Skipping already added movie.");
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    @FXML
    public void handleSortRating(){
        if (bool){
            Collections.sort(onWatchlist, Comparators.sortByHighestRating());
           updateWatchlist();
        }
        else{
            Collections.sort(movieList, Comparators.sortByHighestRating());
            movieView.getItems().setAll(movieList);
        } 
    }

    @FXML
    public void handleSortTitle(){
        if (bool){
            Collections.sort(onWatchlist, Comparators.sortByTitle());
            updateWatchlist();
        }
        else{
            Collections.sort(movieList, Comparators.sortByTitle());
            movieView.getItems().setAll(movieList);
        } 
    }

    @FXML
    public void handleSortDuration(){
        if (bool){
            Collections.sort(onWatchlist, Comparators.sortByDuration());
            updateWatchlist();
        }
        else{
            Collections.sort(movieList, Comparators.sortByDuration());
            updateHomePage();
        } 
    }

    @FXML
    public void setOnWatchlist(){
        movieView.getSelectionModel().getSelectedItem().setOnTakeOfWatchlist(true);
        onWatchlist.add(movieView.getSelectionModel().getSelectedItem());
        if(bool){
            updateWatchlist();
        }
        else {
            updateHomePage();
        }
    }

    @FXML
    public void takeOFWatchlist(){
        movieView.getSelectionModel().getSelectedItem().setOnTakeOfWatchlist(false);
        onWatchlist.remove(movieView.getSelectionModel().getSelectedItem());
        if(bool){
            updateWatchlist();
        }
        else {
            updateHomePage();
        }
    }

    @FXML
    public void setRating(){
        Dialog<Movie> dialog = new Dialog<>();
        dialog.setTitle("Rating");
        dialog.setHeaderText("Please set a rating on the movie");

        ButtonType addMovie = new ButtonType("Set rating", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addMovie, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField rating = new TextField();
        rating.setPromptText("Rating");
        grid.add(new Label("Rating:"), 0, 0);
        grid.add(rating, 1, 0);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> rating.requestFocus());
        dialog.showAndWait();

        Rating r = new Rating(Integer.parseInt(rating.getText()));
        movieView.getSelectionModel().getSelectedItem().setRating(r);
        if(bool){
            updateWatchlist();
        }
        else {
            updateHomePage();
        }

    }

    @FXML
    public void showMoviesOnWatchlist(){
        updateWatchlist();
        this.bool = true;  
    }

    @FXML
    public void showHomePage(){
        updateHomePage();
        this.bool = false;
    }


    private void updateHomePage(){
        movieView.getItems().setAll(movieList);
    }

    private void updateWatchlist(){
        movieView.getItems().setAll(onWatchlist);
    }


}
