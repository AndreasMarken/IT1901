package pmdb.core;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Serie extends Movie{

    private int seasons;
    private int episodes;

    public Serie(String title, Time duration, List<Actor> actors, Date releaseDate,int seasons, int episodes) {
        super(title, duration, actors, releaseDate);
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public int getSeasons() {
        return seasons;
    }

    public int getEpisodes() {
        return episodes;
    }





    public static void main(String[] args) {
        Actor actor = new Actor("ole", 11);
        List <Actor> list = new ArrayList<>();
        list.add(actor);
    }
    
}
