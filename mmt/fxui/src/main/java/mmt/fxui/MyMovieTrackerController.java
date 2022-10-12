package mmt.fxui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
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
    protected Pane movieListView;

    private MovieList movieList = new MovieList();
    private ObjectMapper mapper = new ObjectMapper();

    @FXML
    protected VBox editMovieWindow;

    @FXML
    protected VBox giveRating;

    @FXML
    private CheckBox watchList;

    private boolean testingMode = false;

    private Path getSaveFilePath(String fileName) {
        return getSaveFolderPath().resolve(fileName);
    }

    private Path getSaveFolderPath() {
        return Path.of(System.getProperty("user.home"), "it1901", "mmt", "saveFiles");
    }

    @FXML
    void initialize() throws IOException {
        editMovieController.setMyMovieTrackerController(this);
        hideEditMovie(false);

        // Load the movies registered in the movie.json file.
        mapper.registerModule(new MovieModule());
        MovieList temporaryMovieList = loadMovieListFromFile();
        for (IMovie iMovie : temporaryMovieList) {
            movieList.addMovie(iMovie);
        }
        // Display the movies in the file
        updateMovieListView();
    }

    protected MovieList loadMovieListFromFile() throws IOException {
        //If the filepath does not exist, it will be generated.
        Files.createDirectories(getSaveFolderPath());
        try {
            if (testingMode) {
                Files.createFile(getSaveFilePath("movieTest.json"));
            } else {
                Files.createFile(getSaveFilePath("movie.json"));
            }   
        } catch (FileAlreadyExistsException e) {
            //If the file already exist, FileAlreadyExistException will be thrown.
            //Do nothing if the file already exists
        }
        
        //this.movieList = mapper.readValue(new File("movie.json"), MovieList.class);
        try {
            if (testingMode) {
                return mapper.readValue(getSaveFilePath("movieTest.json").toFile(), MovieList.class);
            }
            return mapper.readValue(getSaveFilePath("movie.json").toFile(), MovieList.class);
        } catch (MismatchedInputException e) {
            return new MovieList();
            //If there is no information stored in the file, return a new instance of a movielist.
        }
        
    }

    private void saveMovieListToFile() throws IOException {
        if (testingMode) {  
            mapper.writeValue(getSaveFilePath("movieTest.json").toFile(), movieList);
        } else {
            mapper.writeValue(getSaveFilePath("movie.json").toFile(), movieList);
        }
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

    public EditMovieController getEditMovieController() {
        return this.editMovieController;
    }

    protected void setTestingMode(boolean testingMode) throws IOException {
        this.testingMode = testingMode;
        this.movieList = new MovieList();
        saveMovieListToFile();
        updateMovieListView();
    }
}
