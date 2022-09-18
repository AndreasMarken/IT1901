package pmdb.core;

import java.util.List;

public class Actor extends human {

    private List <Movie> movies;

    public Actor(String name, int age) {
        super(name, age);
    }

    @Override
    private void addMovies(Movie movie){
        movies.add(movie);
    }

    

    
    




}
