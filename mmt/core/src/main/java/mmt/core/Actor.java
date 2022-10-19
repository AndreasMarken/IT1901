package mmt.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Actor implements IActor{
    private String name;
    private Collection<IMovie> starredMovies = new ArrayList<>();

    public Actor(String name) {
        if (name.equals("") || name.equals(null)) {
            throw new IllegalArgumentException("The input must be valid");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<IMovie> getStarredMovies() {
        return List.copyOf(starredMovies);
    }

    @Override
    public void starredInMovie(IMovie movie) throws IllegalStateException {
        if (starredMovies.contains(movie)) {
            throw new IllegalStateException("An actor cannot starr in a movie more than once.");
        }
        this.starredMovies.add(movie);
        movie.addActor(this);
    }

    @Override
    public void removeMovieFromStarredList(IMovie movie) throws IllegalArgumentException {
        if (!starredMovies.contains(movie)) {
            throw new IllegalArgumentException("You cannot remove a movie from the actors starring list if the actor doesnt have the movie in its starringlist.");
        }
        this.starredMovies.remove(movie);
        movie.removeActor(this);
    }
}
