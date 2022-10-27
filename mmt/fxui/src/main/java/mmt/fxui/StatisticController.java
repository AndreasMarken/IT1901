package mmt.fxui;

import java.io.IOException;
import java.sql.Time;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mmt.core.IMovie;
import mmt.core.MovieList;

public class StatisticController {
    private MovieList movieList = new MovieList();

    @FXML
    protected Label avRating, avLength, higActor, numOfMovies, numOfWatchMovies, avWLength;

    @FXML
    private Button backButton;

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
        this.higActor.setText(null);
        this.numOfMovies.setText(Integer.toString(getNumerOfMovies()));
        this.numOfWatchMovies.setText(Integer.toString(getNumberOfMoviesOnWatchList()));
        if (getAverageMovieLength(this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList()) == null) {
            this.avWLength.setText("No movies on watchlist");
        } else {
            this.avWLength.setText(getAverageMovieLength(this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList()).toString());
        }
    }

    @FXML
    public void handleBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyMovieTracker.fxml"));
        HBox root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void setMovieList(MovieList movieList) {
        if (movieList == null) {
           throw new NullPointerException("You cannot set null as the movielist");
        }
        this.movieList = movieList;
        this.setStatisticInformation();
    }

    private float getAverageRating() {
        int rating = 0;
        Collection<IMovie> moviesWithRating =  this.movieList.getMovies().stream().filter(m -> m.getRating() != null).toList();
        if (moviesWithRating.size() == 0) {
            return -1;
        }
        for (IMovie movie : moviesWithRating) {
            rating += movie.getRatingNumber();
        }
        return rating / moviesWithRating.size();
    }
    
    private int getNumerOfMovies() {
        return this.movieList.getMovies().size();
    }

    private int getNumberOfMoviesOnWatchList() {
        return this.movieList.getMovies().stream().filter(m -> m.getWatchlist()).toList().size();
    }

    private Time getAverageMovieLength(Collection<IMovie> movies) {
        int numberOfMovies = movies.size();
        if (numberOfMovies == 0) {
            return null;
        }
        int minutes = 0;
        for (IMovie movie : movies) {
            minutes += 60 * movie.getDuration().getHours();
            minutes += movie.getDuration().getMinutes();
        }
        minutes = minutes / numberOfMovies;
        return new Time(minutes / 60, minutes % 60, 0);
    }
}
