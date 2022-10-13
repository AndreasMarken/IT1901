package mmt.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Class that represents a list of Movie objects and makes the list iterable.
 * Implements Iterable.
 */
public class MovieList implements Iterable<IMovie> {
    private List<IMovie> movieList = new ArrayList<>();

    
    /** 
     * Adds a movie to the list.
     *
     * @param movie movie to be added
     */
    public void addMovie(IMovie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("You cannot add null to the movielist.");
        }
        if (getMovie(movie.getTitle()) != null) {
            throw new IllegalArgumentException("You cannot add a movie that is already added.");
        }
        movieList.add(movie);
    }

    
    /** 
     * Removes movie from list.
     *
     * @param movie movie to be removed
     */
    public void removeMovie(IMovie movie) {
        movieList.remove(movie);
    }

    
    /** 
     * Gets a movie by title.
     *
     * @param title title of the movie
     * @return IMovie with matcing title from the list
     */
    public IMovie getMovie(String title) {
        return movieList.stream().filter(movie -> movie.getTitle().equals(title)).findFirst().orElse(null);
    }

    
    /** 
     * Iterator for the list.
     *
     * @return Iterator for IMovie objects
     */
    @Override
    public Iterator<IMovie> iterator() {
        return movieList.iterator();
    }

    
    /** 
     * Gets movies form list as a Collection.
     *
     * @return Collection of IMovie objects
     */
    public Collection<IMovie> getMovies() {
        return this.movieList;
    } 
}
