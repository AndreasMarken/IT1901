package mmt.fxui;

import java.io.IOException;
import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mmt.core.IActor;
import mmt.core.IMovie;
import mmt.core.MovieList;

/**
 * The controller that is used to display the statistics of a given movielist to the.
 * Statistics view to the user. Used in the MMT app.
 */
public class StatisticController {
    private MovieList movieList = new MovieList();

    @FXML
    protected Label avRating;

    @FXML
    protected Label avWLength;

    @FXML
    protected Label numOfWatchMovies;

    @FXML
    protected Label higActor;

    @FXML
    protected Label avLength;

    @FXML
    protected Label numOfMovies;

    @FXML
    private Button backButton;

    /**
     * Method that sets the movielist statistic information to the statistics view.
     * Used when the setMovieList method is used, and is not able to be called when not used in that method.
     */
    private void setStatisticInformation() {
        if (getAverageRating() == -1) {
            this.avRating.setText("No ratings given");
        } else {
            this.avRating.setText(Float.toString(getAverageRating()));
        }

        if (getAverageMovieLength(this.movieList.getMovies()) == null) {
            this.avLength.setText("No movies in the database");
        } else {
            this.avLength.setText(getAverageMovieLength(this.movieList.getMovies()).toString());
        }
        this.higActor.setText(getMostCommonActor());
        this.numOfMovies.setText(Integer.toString(getNumerOfMovies()));
        this.numOfWatchMovies.setText(Integer.toString(getNumberOfMoviesOnWatchList()));
        if (getAverageMovieLength(this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList()) == null) {
            this.avWLength.setText("No movies on watchlist");
        } else {
            Collection<IMovie> ml = this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList();
            this.avWLength.setText(getAverageMovieLength(ml).toString());
        }
    }

    /**
     * Changes this current view to the MyMovieTrackerView. Used when the back button is clicked.
     *
     * @throws IOException if it was unnable to open and display the MyMovieTrackerView.
     */
    @FXML
    public void handleBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyMovieTracker.fxml"));
        HBox root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to set the movielist from the mmt controller.
     * Sets the movielist that is to display the statistics to the user.
     *
     * @param movieList the movielist to be set
     */
    protected void setMovieList(MovieList movieList) {
        if (movieList == null) {
            throw new NullPointerException("You cannot set null as the movielist");
        }
        this.movieList = movieList;
        this.setStatisticInformation();
    }

    /**
     * Method used to get the average rating on all the movies in the movielist. This only checks for moveis that have a rating.
     * Method that is used when setting the information in the statstics view.
     *
     * @return the average rating given to the movies.
     */
    private float getAverageRating() {
        int rating = 0;
        Collection<IMovie> moviesWithRating =
            this.movieList.getMovies().stream().filter(m -> m.getRating() != null).toList();
        if (moviesWithRating.size() == 0) {
            return -1;
        }
        for (IMovie movie : moviesWithRating) {
            rating += movie.getRatingNumber();
        }
        return rating / (float) moviesWithRating.size();
    }

    /**
     * Method used to get the number of movies in the movielist.
     * Method that is used when setting the information in the statstics view.
     *
     * @return the number of movies that is in the movielist.
     */
    private int getNumerOfMovies() {
        return this.movieList.getMovies().size();
    }

    /**
     * Method used to get the number of movies on the users watchlist in the movielist.
     * Method that is used when setting the information in the statstics view.
     *
     * @return the number of movies that is on the watchlist.
     */
    private int getNumberOfMoviesOnWatchList() {
        return this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList().size();
    }

    /**
     * Method used to get the average movielength of a list of movies given as an input.
     * Method that is used when setting the information in the statstics view.
     *
     * @param movies the list of movies to get the average duration of.
     * @return null if the length of the list is 0. The average lenght of the movielist given as a Time object.
     */
    private Time getAverageMovieLength(Collection<IMovie> movies) {
        int numberOfMovies = movies.size();
        if (numberOfMovies == 0) {
            return null;
        }
        int minutes = 0;
        for (IMovie movie : movies) {
            //minutes += 60 * movie.getDuration().getHours();
            minutes += 60 * Integer.parseInt(movie.getDuration().toString().substring(0, 2));
            //minutes += movie.getDuration().getMinutes();
            minutes += Integer.parseInt(movie.getDuration().toString().substring(3, 5));
        }
        minutes = minutes / numberOfMovies;
        return Time.valueOf(minutes / 60 + ":" + minutes % 60 + ":00");
    }

    /**
     * Method used to search through the database to get the actor that plays in the most movies.
     *
     * @return The actor that plays in the most movies, if there are no actors in the database, return "No actors".
     */
    private String getMostCommonActor() {
        Map<String, Integer> actorsmap = new HashMap<>();

        for (IMovie movie : movieList) {
            if (movie.getCast() != null) {
                for (IActor actor : movie.getCast()) {
                    actorsmap.put(actor.getName(), actorsmap.getOrDefault(actor.getName(), 0) + 1);
                }
            }
        }

        if (actorsmap.size() == 0) {
            return "No actors";
        }
        return actorsmap
            .entrySet()
            .stream()
            .max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ? 1 : -1)
            .get()
            .getKey();
    }
}
