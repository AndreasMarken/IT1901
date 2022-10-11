package mmt.core;

import java.sql.Date;
import java.sql.Time;

public class Movie implements IMovie {


    private String title;
    private Date releaseDate;
    private Time duration;
    private IRating rating;
    private Boolean watchlist = false;
    
    public Movie(String title, Time duration, Date releaseDate) {
            this.duration = duration;
            this.title = title;
            this.releaseDate = releaseDate;   
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
            this.title = title;

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
    public void setRating(IRating rating) {
        this.rating = rating;
    }

    @Override
    public IRating getRating() {
        return this.rating;
    }

    @Override
    public int getRatingNumber() {
        if (rating == null){
            return 0;
        }
        return rating.getRating();
    }

    @Override
    public void setOnTakeOfWatchlist(Boolean trueOrFalse) {
        this.watchlist = trueOrFalse;
    }

    @Override
    public Boolean getWatchlist(){
        return this.watchlist;
    }

    @Override
    public String toString() {
        return "Movie title: " + getTitle() + "\n"
        + "Duration: " + getDuration().getHours() + " hours " + getDuration().getMinutes() + " minutes" + "\n"
        + "Release date: " + getReleaseDate() + "\n"
        + "Rating: " + getRating() + "\n"
        + "Watchlist: " + getWatchlist() + "\n"
        ;
    }

    @Override
    public void setDuration(Time duration) {
            this.duration = duration;
    }

    @Override
    public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
    }

    
        
}

