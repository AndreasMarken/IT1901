package pmdb.core;

import java.sql.Date;
import java.util.List;

public class Movie implements AppInterface {


    private String title;
    private List <Actor> actors;
    private Date releaseDate;
    private Rating rating;
    private Boolean watchlist;

    public Movie(String title, List<Actor> actors, Date releaseDate) {
        this.title = title;
        this.actors = actors;
        this.releaseDate = releaseDate;
    }


    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return title;
    }

    @Override
    public List<Actor> getActors() {
        // TODO Auto-generated method stub
        return actors;
    }

  

    @Override
    public Date getReleaseDate() {
        // TODO Auto-generated method stub
        return releaseDate;
    }

    @Override
    public void setRating() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Rating getRating() {
        // TODO Auto-generated method stub
        return rating;
    }



    @Override
    public void setOnTakeOfWatchlist(Boolean trueOrFalse) {
        // TODO Auto-generated method stub
        watchlist = trueOrFalse;
    }

    @Override
    public Boolean getWatchlist(){
        // TODO Auto-generated method stub
        return watchlist;
    }



   

    
}
