package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IMovieSeries {
    
    String getTitle();
    Date getReleaseDate();
    Time getDuration();
    void setRating(Rating rating);
    List<Rating> getRating();
    void setOnTakeOfWatchlist(Boolean trueOrFalse);
    Boolean getWatchlist();    
}
