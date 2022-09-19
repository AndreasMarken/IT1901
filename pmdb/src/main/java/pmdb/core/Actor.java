package pmdb.core;

import java.util.ArrayList;
import java.util.List;

public class Actor extends human {

    private List <Movie> movies = new ArrayList<>();

    public Actor(String name, int age, char gender) {
        super(name, age, gender);
    }

    public List<Movie> getMovies() {
        return movies;
    }

   public void addMovie(Movie movie){
    movies.add(movie);
   }

   


   

    

    
    




}
