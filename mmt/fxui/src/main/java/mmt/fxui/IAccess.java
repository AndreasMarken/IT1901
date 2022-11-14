package mmt.fxui;

import java.io.IOException;

import mmt.core.MovieList;

/**
 * Interface accesstype
 * Makes it more readable in MyMovieTracker
 */
public interface IAccess{

    /**
    * Generates a PUT-Request to the server.
    * Sends movielist to server
    *
    * @param MovieList: An object that contains a list of movies.
    @ @throws IOException If the movies cannot be stored in server.
    */
    public void saveMovieList(MovieList movieList) throws IOException;

     /**
    * Generates a GET-Request to the server.
    * Retrieves Movielist from server
    * @throws IOException If the movies cannot be loaded from the file.
    */
    public MovieList loadMovieList() throws IOException;

    /** 
    * Method used to set testmode on and off
    * @param testingMode: true or false
    */ 
    public void setTestMode(boolean testingMode) throws IOException;
}