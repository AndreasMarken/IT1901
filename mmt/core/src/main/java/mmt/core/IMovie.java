package mmt.core;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

/**
 * Movie Interface.
 */
public interface IMovie {
    /**
     * Method that retrieves the title of the movie.
     *
     * @return returns the title.
     */
    String getTitle();

    /**
     * Each Movie object has a unique ID. The ID is a generated UUID.
     * Example ID: eab55236-6bf8-4ec2-82b3-7688cd88a483
     *
     * @return the unique ID of this movie object.
     */
    String getID();

    /**
     * Method that sets a new title for a movie.
     *
     * @param title The title to be given ot the movie.
     */
    void setTitle(String title);

    /**
     * Method that retrieves the the duration of the movie.
     *
     * @return returns the duration.
     */
    Time getDuration();

    /**
     * Method to set the duration of the movie.
     *
     * @param duration The duration of the movie
     */
    void setDuration(Time duration);

    /**
     * Method that retrieves the release date of the movie.
     *
     * @return returns the release date.
     */
    Date getReleaseDate();

    /**
     * Method to set the release date of the movie.
     *
     * @param releaseDate The release date.
     */
    void setReleaseDate(Date releaseDate);

    /**
     * Method that retrieves the Rating of the movie.
     *
     * @return returns the rating.
     */
    IRating getRating();

    /**
     * Method that sets the rating to the movie.
     *
     * @param rating Rating object.
     */
    void setRating(IRating rating);

    /**
     * Method that retrieves the rating of the movie (1-10).
     *
     * @return rating (1-10)
     */
    int getRatingNumber();

    /**
     * Method to take a Movie/Serie object on and of the watchlist.
     *
     * @param trueOrFalse True -> adds to watchlist. False -> take off watchlist.
     */
    void setOnTakeOfWatchlist(Boolean trueOrFalse);

    /**
     * Method that provides information if a Movie/Serie object is on the watchlist.
     *
     * @return returns true if in watchlist, otherwise false.
     */
    Boolean getWatchlist();

    /**
     * Method that retrieves all the actors starring in the movie.
     *
     * @return a collection of IActor objects, or null if the movie has no actors
     */
    Collection<IActor> getCast();

    /**
     * Method that adds an actor to the cast of the movie.
     *
     * @param actor the actor to be addded to the cast
     * @throws IllegalStateException if the actor to be added already exists in the cast
     */
    void addActor(IActor actor);

    /**
     * Method that removes an actor from the cast of the movie.
     *
     * @param actor the actor that should be removed from the cast
     * @throws IllegalArgumentException if the actor to be removed does not exist in the cast
     */
    void removeActor(IActor actor);
}
