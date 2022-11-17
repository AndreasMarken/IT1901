package mmt.fxui;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mmt.core.Comparators;
import mmt.core.IActor;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;

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

    @FXML
    private Button statisticButton;

    private MovieList movieList = new MovieList();

    @FXML
    protected VBox editMovieWindow;

    @FXML
    protected VBox giveRating;

    @FXML
    private CheckBox watchList;

    @FXML
    private TextField actorInputField;

    @FXML
    private String apiUri;

    @FXML
    private Text accessFeedback;

    protected IAccess dataAccess;

    /**
     * Method that runs upon initializing the controller and app.
     * Tries connecting to server, if not possible it runs localy
     *
     * @throws IOException When movies from the file cannot be loaded.
     */
    @FXML
    void initialize() throws IOException {
        editMovieController.setMyMovieTrackerController(this);
        hideEditMovie(false);
        try {
            this.dataAccess = new RemoteMmtAccess(apiUri);
            movieList = dataAccess.loadMovieList();
            accessFeedback.setText("Connected to server");
            accessFeedback.setFill(Color.GREEN);
        } catch (Exception e) {
            this.dataAccess = new LocalMmtAccess();
            movieList = dataAccess.loadMovieList();
            accessFeedback.setText("Not connected to server");
            accessFeedback.setFill(Color.RED);
        }

        updateMovieListView();
    }

    /**
     * Sorts the movielist based on rating from best to worst.
     */
    @FXML
    private void handleSortRating() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByHighestRating());
        searchActor();
    }

    /**
     * Sorts the movielist based on title in alfabetical order.
     */
    @FXML
    private void handleSortTitle() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByTitle());
        searchActor();
    }

    /**
     * Sorts the movielist based on duration from shortest to longest.
     */
    @FXML
    private void handleSortDuration() {
        Collections.sort((List<IMovie>) movieList.getMovies(), Comparators.sortByDuration());
        searchActor();
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
     * @param moviList : MovieList to be displayed
     */
    protected void displayMovieListView(boolean watchList, MovieList movieList) {
        try {
            movieListView.getChildren().clear();
            int numberOfMovies = 0;
            double offsetX = movieListView.getPrefWidth() / 2;
            double offsetY = -1.0;

            Collection<IMovie> movies = movieList.getMovies();

            if (watchList) {
                movies = movies.stream().filter(m -> m.getWatchlist()).toList();
            }

            for (IMovie movie : movies) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("DisplayMovie.fxml"));
                Pane moviePane = fxmlLoader.load();
                DisplayMovieController displayMovieController = fxmlLoader.getController();
                displayMovieController.setMyMovieTrackerController(this);
                displayMovieController.setMovie(movie);
                displayMovieController.setMovieInformation();
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
    }

    /**
     * Method used to set the testingmode. When performing the test, you do not want to destroy
     * the users database. Therefore you can set the controller to testing mode, which changes the file
     * that this controller writes to.
     *
     * @param testingMode True if testingmode is to be set, false if not.
     * @throws IOException If it was unable to save the movielist to file.
     */
    protected void setTestingMode(boolean testingMode) throws IOException {
        if (this.dataAccess instanceof LocalMmtAccess) {
            LocalMmtAccess localAccess = (LocalMmtAccess) dataAccess;
            localAccess.setTestMode(testingMode);
            this.movieList = new MovieList();
            dataAccess.saveMovieList(movieList);
            updateMovieListView();
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
        if (movie instanceof Movie) {
            dataAccess.deleteMovie((Movie) movie);
        }
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
     * Updates the movie listview based on wheter the watchlistcheckbox is checked or not.
     */
    @FXML
    protected void updateMovieListView() {
        displayMovieListView(watchList.isSelected(), this.movieList);
    }

    /**
     * Method used to get the editmoviecontroller. Mostly used for testing, where this controller is needed.
     *
     * @return EditMovieController: the editmoviecontroller that this controller is connected to.
     */
    public EditMovieController getEditMovieController() {
        return this.editMovieController;
    }

    /**
     * Changes this current view to the statisticsview. Used when the statisticsview button is clicked.
     *
     * @throws IOException if it was unnable to open and display the statistic view.
     */
    @FXML
    public void showStatistics() throws IOException {
        Stage stage = (Stage) statisticButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Statistic.fxml"));
        Parent root = fxmlLoader.load();
        StatisticController statisticController = fxmlLoader.getController();
        statisticController.setMovieList(this.movieList);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void searchActor() {
        getMoviesFromActorSearch(
            () -> {
                if (actorInputField.getText().equals("")) {
                    updateMovieListView();
                }
            }
        );
    }

    private void getMoviesFromActorSearch(Runnable search) {
        MovieList movieListActors = new MovieList();
        for (IMovie movie : this.movieList) {
            if (movie.getTitle().contains(actorInputField.getText())) {
                movieListActors.addMovie(movie);
            }
            if (movie.getCast() != null) {
                for (IActor actor : movie.getCast()) {
                    if (actor.getName().contains(actorInputField.getText())) {
                        if (movieListActors.getMovie(movie.getTitle()) == null) {
                            movieListActors.addMovie(movie);
                            break;
                        }
                    }
                }
            }
        }
        displayMovieListView(watchList.isSelected(), movieListActors);
        search.run();
    }

    /**
     * Sets the access.
     *
     * @param access to be set
     */
    public void setAccess(IAccess access) {
        if (access != null) {
            this.dataAccess = access;
        }
    }
}
