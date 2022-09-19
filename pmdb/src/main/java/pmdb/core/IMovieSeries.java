package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IMovieSeries {
    
    String getTitle();
    List <Actor> getActors();
    Date getReleaseDate();
    Time getDuration();
    void setRating(Rating rating);
    Rating getRating();
    void setOnTakeOfWatchlist(Boolean trueOrFalse);
    Boolean getWatchlist();    
}
