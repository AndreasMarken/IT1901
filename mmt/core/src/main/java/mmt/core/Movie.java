package mmt.core;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Class to represent Movie objects.
 * Implements the interface IMovie.
 */
public class Movie implements IMovie {
    private String title;
    private Date releaseDate;
    private Time duration;
    private IRating rating;
    private Boolean watchlist = false;
    private Collection<IActor> cast;
    private String id;

    /**
     * One of two constructors for Movie.
     *
     * @param title Title of the Movie
     * @param duration Duration of teh Movie
     * @param releaseDate Release date of the Movie
     */
    public Movie(String title, Time duration, Date releaseDate) {
        if (checkIfNull(duration)) {
            this.duration = duration;
        }
        if (checkIfNull(title)) {
            this.title = title;
        }
        if (checkIfNull(releaseDate)) {
            this.releaseDate = releaseDate;
        }
        this.id = UUID.randomUUID().toString();
    }

    public Movie(String title, Time duration, Date releaseDate, String id) {
        this(title, duration, releaseDate);
        this.id = id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Time getDuration() {
        return this.duration;
    }

    @Override
    public void setDuration(Time duration) {
        if (checkIfNull(duration)) {
            this.duration = duration;
        }
    }

    @Override
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public void setReleaseDate(Date releaseDate) {
        if (checkIfNull(releaseDate)) {
            this.releaseDate = releaseDate;
        }
    }

    @Override
    public IRating getRating() {
        return this.rating;
    }

    @Override
    public void setRating(IRating rating) {
        this.rating = rating;
    }

    @Override
    public int getRatingNumber() {
        if (rating == null) {
            return 0;
        }
        return rating.getRating();
    }

    @Override
    public void setOnTakeOfWatchlist(Boolean trueOrFalse) {
        this.watchlist = trueOrFalse;
    }

    @Override
    public Boolean getWatchlist() {
        return this.watchlist;
    }

    /**
     * Method to check if a object is "null".
     *
     * @param input The object to be checked
     * @return true if object is null, otherwise false
     */
    private Boolean checkIfNull(Object input) {
        if (input == null) {
            throw new IllegalArgumentException(
                "Input value is null on one of the following fields: Title, duration, actors, release date"
            );
        }
        return true;
    }

    @Override
    public Collection<IActor> getCast() {
        if (cast != null) {
            return new ArrayList<IActor>(cast);
        }
        return cast;
    }

    @Override
    public void addActor(IActor actor) {
        if (cast == null) {
            cast = new ArrayList<IActor>();
        }
        if (cast.contains(actor)) {
            throw new IllegalStateException("The actor is already added to the movie!");
        }
        cast.add(actor);
        actor.starredInMovie(this);
    }

    @Override
    public void removeActor(IActor actor) {
        if (!cast.contains(actor)) {
            throw new IllegalArgumentException("The actor you are trying to remove is not in the cast of this movie");
        }
        cast.remove(actor);
        actor.removeMovieFromStarredList(this);
        if (cast.isEmpty()) {
            cast = null;
        }
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String toString() {
        String objRepresentationsString =
            (
                "Movie title: " +
                getTitle() +
                "\n" +
                "Duration: " +
                getDuration().toString().substring(0, 2) +
                " hours " +
                getDuration().toString().substring(3, 5) +
                " minutes" +
                "\n" +
                "Release date: " +
                getReleaseDate() +
                "\n" +
                "Rating: " +
                getRating() +
                "\n" +
                "Watchlist: " +
                getWatchlist() +
                "\n"
            );

        return objRepresentationsString;
    }
}
