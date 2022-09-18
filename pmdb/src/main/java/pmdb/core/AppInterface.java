package pmdb.core;

import java.sql.Date;
import java.util.List;

public interface AppInterface {
    
    String getTitle();
    List <Actor> getActors();
    Date getReleaseDate();
    void setRating();
    Rating getRating();
    void setOnTakeOfWatchlist(Boolean trueOrFalse);
    Boolean getWatchlist();
    

    
}
