package mmt.fxui;

import java.io.IOException;

import mmt.core.MovieList;

/**
 * Interface accesstype
 * Makes it more readable in MyMovieTracker
 */
public interface IAccess{

    /*
     *
     */
    public void saveMovieList(MovieList movieList) throws IOException;

    /*
     *
     */
    public MovieList loadMovieList() throws IOException;


    public void setTestMode(boolean testingMode) throws IOException;
}