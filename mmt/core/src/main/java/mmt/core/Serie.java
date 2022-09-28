package mmt.core;

import java.sql.Date;
import java.sql.Time;

public class Serie extends Movie{

    private int seasons;
    private int episodes;

    public Serie(String title, Time duration, Date releaseDate,int seasons, int episodes) {
        super(title, duration, releaseDate);
        if (checkInputValue(seasons)){
            this.seasons = seasons;
        }
        if (checkInputValue(episodes)){
            this.episodes = episodes;
        }
    }

    public int getSeasons() {
        return seasons;
    }

    public int getEpisodes() {
        return episodes;
    }

    private Boolean checkInputValue(int input){
        if (input == 0){
            throw new IllegalArgumentException("Cannot have inputvalue = 0 in the fields seasons or episodes");
        }
        return true;
    }
    
}
