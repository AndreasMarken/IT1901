package mmt.fxui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mmt.core.Comparators;
import mmt.core.IMovie;
import mmt.core.MovieList;
import mmt.json.MovieModule;

/**
 * The main controller used for the My Movie Tracker Application.
 * This controller controls the logic in the app, and delegates tasks to other controllers.
 * Is connected to the MyMovieTracker.fxml file, which contains the application layout.
 */
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
    
    /**
     * Method that runs upon initializing the controller and app.
     *
     * @throws IOException When movies from the file cannot be loaded.
     */
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

    /**
     * Loads movies form the given file.
     *
     * @return MovieList: An object that contains a list of movies.
     * @throws IOException If the movies cannot be loaded from the file.
     */
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

    /**
     * Saves the movielist that is stored in the controller to the given file.
     *
     * @throws IOException if the movies cannot be saved to file.a
     */
    private void saveMovieListToFile() throws IOException {
        if (testingMode) {  
            mapper.writeValue(getSaveFilePath("movieTest.json").toFile(), movieList);
        } else {
            mapper.writeValue(getSaveFilePath("movie.json").toFile(), movieList);
        }
    }

    /**
     * Sorts the movielist based on rating from best to worst.
     */
    @FXML
    private void handleSortRating() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByHighestRating());
        updateMovieListView();
    }

    /**
     * Sorts the movielist based on title in alfabetical order.
     */
    @FXML
    private void handleSortTitle() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByTitle());
        updateMovieListView();
    }

    /**
     * Sorts the movielist based on duration from shortest to longest.
     */
    @FXML
    private void handleSortDuration() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByDuration());
        updateMovieListView();
    }

    /**
     * Method to be run when adding a new movie to the movielist is done by the user.
     */
    @FXML
    private void addNewMovie() {
        editMovie(null);
    }

    /**
     * Shows the add/edit movie view. Tells the editmoviecontroller which movie to be edited.
     *
     * @param movie the movie to be edited. If input is null, a new movie is to be created.
     */
    protected void editMovie(IMovie movie) {
        editMovieController.editMovie(movie);
        hideEditMovie(true);
    }

    /**
     * Hides/Shows the edit/add movie view.
     *
     * @param hide true if the view shold be visible, false if it should be hidden.
     */
    protected void hideEditMovie(boolean hide) {
        editMovieWindow.setVisible(hide);
    }

    /**
     * Displays the movies in the movielist to the user in the app.
     *
     * @param watchList : True if only movies on the watchlist is to be shown, false otherwise.
     */
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
            
            for (IMovie movie : movies) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("DisplayMovie.fxml"));
                DisplayMovieController displayMovieController = fxmlLoader.getController();
                displayMovieController.setMyMovieTrackerController(this);
                displayMovieController.setMovie(movie);
                displayMovieController.setMovieInformation();
                Pane moviePane = fxmlLoader.load();
                movieListView.getChildren().add(moviePane);
                if (offsetY < 0.0) {
                    offsetY = moviePane.getPrefHeight();
                }
                int numberOfMoviesCalc = (int) numberOfMovies / 2;
                
                moviePane.setLayoutY(offsetY * numberOfMoviesCalc);
                moviePane.setLayoutX(offsetX * (numberOfMovies % 2));
                moviePane.setId("Movie" + String.valueOf(numberOfMovies));
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

    /**
     * Method to get the list of movies in the movielist.
     *
     * @return A collection of the movies.
     */
    protected Collection<IMovie> getMovies() {
        return movieList.getMovies();
    }

    /**
     * Method to get the movielist that this controller is linked to.
     *
     * @return The Movielist-object.
     */
    protected MovieList getMovieList() {
        return this.movieList;
    }

    /**
     * Deletes the given movie from the movielist and updates the movielistview to the user.
     *
     * @param movie the movie to be deleted.
     */
    protected void deleteMovie(IMovie movie) {
        movieList.removeMovie(movie);
        updateMovieListView();
    }

    /**
     * Adds movie to the users movielist.
     *
     * @param movie the movie to be added.
     */
    protected void addMovie(IMovie movie) {
        try {
            this.movieList.addMovie(movie);
        } catch (IllegalArgumentException e) {
            System.out.println("Movie was not added.");
        }
    }

    /**
     * Updates themovielistview based on wheter the watchlistcheckbox is checked or not.
     */
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
