package mmt.core;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Comparators {
    
    public static Comparator<Movie> sortByHighestRating(){
        return Comparator.comparing(Movie::getRatingNumber).reversed();
    }

    public static Comparator<Movie> sortByTitle(){
        return Comparator.comparing(Movie::getTitle);
    }

    public static Comparator<Movie> sortByDuration(){
        return Comparator.comparing(Movie::getDuration);
    }



    public static void main(String[] args) {
        Time time = new Time(3, 20, 0);
        Time time1 = new Time(2, 21, 0);
        Time time2 = new Time(2, 20, 0);
        Movie movie = new Movie("aaaa", time2);
        Movie movie2 = new Movie("hade", time1);
        Movie movie4 = new Movie("x", time);
        Movie movie3 = new Movie("hei", time);
        Rating rating = new Rating(2);
        Rating rating2 = new Rating(7);
        Rating rating3 = new Rating(3);
        Rating rating4 = new Rating(8);
        movie.setRating(rating);
        movie2.setRating(rating2);
        movie3.setRating(rating3);
        movie4.setRating(rating4);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.sort(sortByHighestRating());
        System.out.println(movies);
/*         movies.stream().map(p -> p.getRating()).sorted(sortByHighestRating()).toList();
 *//*         List <IRating> ratings = new ArrayList<>(movies.stream().map(p -> p.getRating()).toList());

 */    
    }

    
}
