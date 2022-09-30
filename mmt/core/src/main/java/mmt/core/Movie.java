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
        if (checkIfNull(duration)){
            this.duration = duration;
        }
        if (checkIfNull(title)){
            this.title = title;
        }
        if (checkIfNull(releaseDate)){
            this.releaseDate = releaseDate;
        }
    }

    public Movie(String title, Time duration) {
        if (checkIfNull(duration)){
            this.duration = duration;
        }
        if (checkIfNull(title)){
            this.title = title;
        }
    }


    @Override
    public String getTitle() {
        return this.title;
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

    private Boolean checkIfNull(Object input){
        if(input == null){
            throw new IllegalArgumentException("Input value is null on one of the following fields: Title, duration, actors, release date");
        }
        return true;

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

    
        
}
