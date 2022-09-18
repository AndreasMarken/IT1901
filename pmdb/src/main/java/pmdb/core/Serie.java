package pmdb.core;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Serie extends Movie{


    public Serie(String title, List<Actor> actors, Date releaseDate) {
        super(title, actors, releaseDate);
    }
   


    public static void main(String[] args) {
        Actor actor = new Actor("ole", 11);
        List <Actor> list = new ArrayList<>();
        list.add(actor);
        Date date = new Date(1900, 05, 02);
        Serie serie = new Serie("Hei",list ,date );

        System.out.println(serie.getTitle());
    }
    
}
