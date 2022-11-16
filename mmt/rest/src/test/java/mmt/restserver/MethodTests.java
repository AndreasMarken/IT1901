package mmt.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;

import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.fxui.RemoteMmtAccess;
import mmt.json.MyMovieConfig;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.Time;

public class MethodTests extends JerseyTest {

    private ObjectMapper oMapper;

    private static RemoteMmtAccess access = new RemoteMmtAccess("http://localhost:8080/mmt/"); 
    private Time duration; 
    private Date releasDate;
    private Movie avengers, dune;
    private static MovieList list, origList, list2;

    private final static String movieListWithThreeMovies = "{\"movies\":[{\"title\":\"James Bond\",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}";


    public MethodTests(){
        //MethodTests.access = 
        //this.duration = new Time(2, 22, 25);
        this.duration = Time.valueOf("02:22:25");
        //this.releasDate = new Date(2010, 8, 7);
        this.releasDate = Date.valueOf("2010-08-07");
        this.avengers = new Movie("Avengers", duration, releasDate);
        this.dune = new Movie("Dune", duration, releasDate);
        this.oMapper = MyMovieConfig.createOMapper();
    }


    @Override
    protected ResourceConfig configure() {
        final MmtConfig config = new MmtConfig();
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        return config;
    }

    @BeforeAll
    public static void setOrigList(){
        try {
            origList = access.loadMovieList();    
            System.out.println("kommer vi hit");  
        } catch (Exception e) {
            System.out.println(e.getMessage() + "dette er medlingen");
            
        }
    } 

    @AfterAll
    public static void restoreOrigList(){
        try {
            for (IMovie movie : list) {
                access.deleteMovie((Movie) movie);
            }
            for (IMovie movie : origList) {
                access.addMovie((Movie) movie);
            } 
            list2 = access.loadMovieList();   
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(origList + "dette er origlist");
        assertEquals(origList.toString(), list2.toString());
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp(); 
        try {
            if (!access.loadMovieList().getMovies().isEmpty()){
                for (IMovie movie : access.loadMovieList()) {
                    access.deleteMovie((Movie) movie);
            }        
            }
            for (IMovie movie : getTestMovieList()) {
                access.addMovie((Movie) movie);
            } 
        } catch (Exception e) {
            // TODO: handle exception
        } 
    }
    
    @Test
    public void testLoadMovieList(){
        try {
            list = access.loadMovieList();
        } catch (Exception e) {
            Assertions.fail();
        }
        assertNotNull(list);
        assertEquals(List.of("James Bond", "Lange flate ballær", "iodhosa").toString(), list.toString());
    }

    
    @Test
    public void testAddMovie(){
        try {
            access.addMovie(avengers);
            list = access.loadMovieList();
            access.deleteMovie(avengers);
        } catch (Exception e) {
            Assertions.fail(); 
        }
        assertEquals(List.of("James Bond", "Lange flate ballær", "iodhosa", "Avengers").toString(), list.toString());
    }
    
    @Test
    public void testDeleteMovie(){
        try {
            access.addMovie(avengers);
            access.addMovie(dune);
            access.deleteMovie(avengers);
            list = access.loadMovieList();
        } catch (Exception e) {
            Assertions.fail();
        }
        assertEquals(List.of("James Bond", "Lange flate ballær", "iodhosa", "Dune").toString(), list.toString());
    }

    @Test
    public void testUpdateMovie(){
        try {
            access.addMovie(avengers);
            access.updateMovie(dune, avengers.getID());
            list = access.loadMovieList();
        } catch (Exception e) {
            Assertions.fail();
        }
        assertEquals(List.of("James Bond", "Lange flate ballær", "iodhosa","Dune").toString(), list.toString());
      
    }  


    private MovieList getTestMovieList(){
        try {
            MovieList list = oMapper.readValue(movieListWithThreeMovies, MovieList.class);
            return list;
        } catch (Exception e) {
            System.out.println("Failed to get movielist");
            //return new MovieList();
            return null;
        }
    }
}
