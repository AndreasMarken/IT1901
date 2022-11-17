package mmt.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to represent Actor objects.
 * Implements the interface IActor.
 */
public class Actor implements IActor {
    private String name;
    private Collection<IMovie> starredMovies = new ArrayList<>();
    private final Collection<Character> invalidChars = Arrays.asList(
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        '\\',
        '/',
        ':',
        '*',
        '"',
        '<',
        '>',
        '|',
        '~',
        '!',
        '@',
        '#',
        '$',
        '%',
        '^',
        '&',
        '(',
        ')',
        '{',
        '}',
        '_',
        ';'
    );

    /**
     * Constructor for Actor class.
     *
     * @param name of the actor
     */
    public Actor(String name) {
        Boolean actorNameIsNotValid =
            (
                name == (null) ||
                name.isBlank() ||
                !Collections.disjoint(invalidChars, name.chars().mapToObj(e -> (char) e).collect(Collectors.toList()))
            );
        if (actorNameIsNotValid) {
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
    }

    @Override
    public void removeMovieFromStarredList(IMovie movie) throws IllegalArgumentException {
        if (!starredMovies.contains(movie)) {
            throw new IllegalArgumentException(
                "You cannot remove a movie from the actors starring list if the actor doesnt have the movie in its starringlist."
            );
        }
        this.starredMovies.remove(movie);
    }
}
