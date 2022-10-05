package mmt.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MovieList implements Iterable<IMovie> {
    private List<IMovie> movieList = new ArrayList<>();

    public MovieList() {
    }

    public void addMovie(IMovie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("You cannot add null to the movielist.");
        }
        if (getMovie(movie.getTitle()) != null) {
            throw new IllegalArgumentException("You cannot add a movie that is already added.");
        }
        movieList.add(movie);
    }

    public void removeMovie(IMovie movie) {
        movieList.remove(movie);
    }

    public IMovie getMovie(String title) {
        return movieList.stream().filter(movie -> movie.getTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    public Iterator<IMovie> iterator() {
        return movieList.iterator();
    }

    public Collection<IMovie> getMovies() {
        return this.movieList;
    } 
}
