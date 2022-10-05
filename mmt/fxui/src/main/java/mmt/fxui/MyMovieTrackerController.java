package mmt.fxui;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mmt.core.Comparators;
import mmt.core.IMovie;
import mmt.core.MovieList;
import mmt.json.MovieModule;

public class MyMovieTrackerController {

    @FXML
    EditMovieController editMovieController = new EditMovieController();

    @FXML
    private Pane movieListView;

    private MovieList movieList = new MovieList();
    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    private VBox editMovieWindow;

    @FXML
    protected VBox giveRating;

    @FXML
    private CheckBox watchList;
    
    @FXML
    void initialize() throws IOException {
        editMovieController.setMyMovieTrackerController(this);
        hideEditMovie(false);

        //Load the movies registered in the movie.json file.
        mapper.registerModule(new MovieModule());
        MovieList temporaryMovieList = loadMovieListFromFile();
        for (IMovie iMovie : temporaryMovieList) {
            movieList.addMovie(iMovie);
        }
        //Display the movies in the file
        updateMovieListView();
    }

    protected MovieList loadMovieListFromFile() throws IOException {
        //this.movieList = mapper.readValue(new File("movie.json"), MovieList.class);
        return mapper.readValue(new File("../core/src/main/resources/mmt/json/movie.json"), MovieList.class); 
    }

    private void saveMovieListToFile() throws IOException {
        mapper.writeValue(new File("../core/src/main/resources/mmt/json/movie.json"), movieList);
    }

    @FXML
    private void handleSortRating() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByHighestRating());
        updateMovieListView();
    }

    @FXML
    private void handleSortTitle() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByTitle());
        updateMovieListView();
    }

    @FXML
    private void handleSortDuration() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByDuration());
        updateMovieListView();
    }

    @FXML
    private void addNewMovie() {
        editMovie(null);
    }

    protected void editMovie(IMovie movie) {
        editMovieController.editMovie(movie);
        hideEditMovie(true);
    }

    protected void hideEditMovie(boolean hide) {
        editMovieWindow.setVisible(hide);
    }

    protected void displayMovieListView(boolean watchList) {
        try {
            movieListView.getChildren().clear();
            int numberOfMovies = 0;
            double offsetX = movieListView.getPrefWidth() / 2;
            double offsetY = -1.0;

            Collection<IMovie> movies = this.getMovies();

            if (watchList) {
                movies = this.getMovies().stream().filter(m -> m.getWatchlist()).toList();
            }
            
            for (IMovie IMovie : movies) {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("DisplayMovie.fxml"));
                    Pane moviePane = fxmlLoader.load();
                    DisplayMovieController displayMovieController = fxmlLoader.getController();
                    displayMovieController.setMyMovieTrackerController(this);
                    displayMovieController.setMovie(IMovie);
                    displayMovieController.setMovieInformation();
                    movieListView.getChildren().add(moviePane);
                if (offsetY < 0.0) {
                    offsetY = moviePane.getPrefHeight();
                }
                int numberOfMoviesCalc = (int) numberOfMovies / 2;
                
                moviePane.setLayoutY(offsetY * numberOfMoviesCalc);
                moviePane.setLayoutX(offsetX * (numberOfMovies % 2));
                moviePane.setId("Movie"+String.valueOf(numberOfMovies));
                numberOfMovies++;
            }
            int numberOfMoviesCalc = (int) numberOfMovies / 2;
            movieListView.setLayoutY(numberOfMoviesCalc);
        } catch (IOException e) {
            //If the movie was not able to be displayed, try skipping this movie.
        }
        try {
            saveMovieListToFile();
        } catch (IOException e) {
            System.out.println("The movies was not saved to file");
        }
    }

    protected Collection<IMovie> getMovies() {
        return movieList.getMovies();
    }

    protected MovieList getMovieList() {
        return this.movieList;
    }

    protected void deleteMovie(IMovie movie) {
        movieList.removeMovie(movie);
        updateMovieListView();
    }

    protected void addMovie(IMovie movie) {
        try {
            this.movieList.addMovie(movie);
        } catch (IllegalArgumentException e) {
        }
    }

    @FXML
    protected void updateMovieListView() {
        displayMovieListView(watchList.isSelected());
    }
}
