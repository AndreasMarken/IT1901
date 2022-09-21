package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Movie implements IMovieSeries {


    private String title;
    private Date releaseDate;
    private Time duration;
    private List<Rating> rating = new ArrayList<>();
    private Boolean watchlist;
    
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
    public void setRating(Rating rating) {
        this.rating.add(rating);
    }

    @Override
    public List<Rating> getRating() {
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

    private Boolean checkIfNull(Object input){
        if(input == null){
            throw new IllegalArgumentException("Input value is null on one of the following fields: Title, duration, actors, release date");
        }
        return true;

    }

    @Override
    public String toString() {
        return "Movie [duration=" + duration + ", rating=" + rating + ", releaseDate="
                + releaseDate + ", title=" + title + ", watchlist=" + watchlist + "]";
    }    
}
