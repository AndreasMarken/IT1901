package mmt.core;

import java.sql.Date;
import java.sql.Time;

public interface IMovie {
    
    /**
     * Method that retrieves the title of the object.
     * @return returns the title.
     */
    String getTitle();

    /**
     * Method that retrieves the release date of the object.
     * @return returns the release date.
     */
    Date getReleaseDate();

    /**
     * Method that retrieves the the duration of the object.
     * @return returns the duration.
     */
    Time getDuration();

    /**
     * Method that sets the rating to the movie.
     */
    void setRating(IRating rating);

    /**
     * Method that retrieves the Rating of the object.
     * @return returns the rating.
     */
    IRating getRating();

    /**
     * Method to take a Movie/Serie object on and of the watchlist.
     * @param trueOrFalse
     */
    void setOnTakeOfWatchlist(Boolean trueOrFalse);

    /**
     * Method that provides information if a Movie/Serie object is on the watchlist.
     * @return returns true if in watchlist, otherwise false.
     */
    Boolean getWatchlist();

    /**
     * Method that sets a new title for a movie.
     * @param title : the title to be given ot the movie
     */
    void setTitle(String title);   

    void setDuration(Time duration);

    void setReleaseDate(Date releaseDate);

    int getRatingNumber();
}


