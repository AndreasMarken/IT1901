package mmt.core;

import java.util.Comparator;


public class Comparators {


    /**
     * 
     * @return a comparator that compares the movies based on their rating. This means that the movie/movies with the highest rating will come first in the list.
     */
    public static Comparator<Movie> sortByHighestRating(){
        return Comparator.comparing(Movie::getRatingNumber).reversed();
    }

     /**
     * 
     * @return a comparator that compares the movies based on their title. The list will be sorted alfabetical.
     */
    public static Comparator<Movie> sortByTitle(){
        return Comparator.comparing(Movie::getTitle);
    }

     /**
     * 
     * @return a comparator that compares the movies based on their duration. The movies with least duration will come first.
     */
    public static Comparator<Movie> sortByDuration(){
        return Comparator.comparing(Movie::getDuration);
    }
    
}
