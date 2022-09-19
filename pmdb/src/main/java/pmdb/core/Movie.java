package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Movie implements IMovieSeries {


    private String title;
    private List <Actor> actors;
    private Date releaseDate;
    private Time duration;
    private Rating rating;
    private Boolean watchlist;

    public Movie(String title, Time duration, List<Actor> actors, Date releaseDate) {
        this.duration = duration;
        this.title = title;
        this.actors = actors;
        addMovieToActor(actors);
        this.releaseDate = releaseDate;
    }


    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public List<Actor> getActors() {
        return this.actors;
    }

    @Override
    public Time getDuration(){
        return this.duration;
    }

    @Override
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    @Override
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public Rating getRating() {
        return this.rating;
    }

    @Override
    public void setOnTakeOfWatchlist(Boolean trueOrFalse) {
        this.watchlist = trueOrFalse;
    }

    @Override
    public Boolean getWatchlist(){
        return this.watchlist;
    }

    public void addMovieToActor(List <Actor> actors){
        for (Actor actor : actors) {
            actor.addMovie(this);
        }
    }

    


    @Override
    public String toString() {
        return "Movie [actors=" + actors + ", duration=" + duration + ", rating=" + rating + ", releaseDate="
                + releaseDate + ", title=" + title + ", watchlist=" + watchlist + "]";
    }


    public static void main(String[] args) {
        
        List <Actor> liste = new ArrayList<>();
        List <Actor> liste2 = new ArrayList<>();
        Date dato = new Date(1990, 05, 22);
        Time tid = new Time(02, 00, 00);
        liste.add(ole);
        liste.add(cornelia);
        liste.add(markus);
        liste2.add(ole);
        liste2.add(cornelia);
        Movie film = new Movie("Yeag", tid, liste, dato);
        Movie film2 = new Movie("URHPFIAUH", tid, liste2 , dato);
        Person person = new Person("ole", 33, 'C', "aiuerhpiueh", "ipeuhafpiurehf");
        Person person2 = new Person("ole", 33, 'C', "aiuerhpiueh", "ipeuhafpiurehf");
        System.out.println(person.getId());
        
    }

   

    
}
