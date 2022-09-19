package pmdb.core;

import java.sql.Date;
import java.sql.Time;

public interface IMovieSeries {
    
    String getTitle();
    Date getReleaseDate();
    Time getDuration();
    void setRating(Rating rating);
    Rating getRating();
    void setOnTakeOfWatchlist(Boolean trueOrFalse);
    Boolean getWatchlist();    
}
