package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Movie implements AppInterface {


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
        for (Actor actor : actors) {
        }
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

   

    
}
