package mmt.core;

import java.util.Collection;

/**
 * Actor Interface.
 */
public interface IActor {
    /**
     * Method to get the name of the actor belonging to this actor object.
     *
     * @return The actors name as a String
     */
    String getName();

    /**
     * Method used to get all the movies that this actor has starred in.
     *
     * @return a List containing IMovie objects of the movies that this actor has starred in.
     */
    Collection<IMovie> getStarredMovies();

    /**
     * Method that adds a movie that this actor has starred in, to the existing.
     * starredMovieList.
     *
     * @param movie the movie to be added.
     * @throws IllegalStateException if movie is already added to the actors starred movie list.
     */
    void starredInMovie(IMovie movie) throws IllegalStateException;

    /**
     * Method that removes a movie from this actors starredMovieList. Used if an actor.
     * is wrongfully added to the movie, or has left the cast.
     *
     * @param movie the movie to be removed from the starredMovie list.
     * @throws IllegalArgumentException if this actor never was starred in the movie given as an input.
     */
    void removeMovieFromStarredList(IMovie movie) throws IllegalArgumentException;
}
