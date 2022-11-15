package mmt.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.fxui.RemoteMmtAccess;
import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.Time;

public class MethodTests extends JerseyTest {

    private ObjectMapper oMapper;
    private MovieList movieList;
    private RemoteMmtAccess access;
    private MyMovieConfig config;
    private Path path;
    private Time duration; 
    private Date releasDate;
    private Movie avengers, dune;

    public MethodTests(){
        this.access = new RemoteMmtAccess("http://localhost:8080/mmt/");
        this.config  = new MyMovieConfig();
        this.path = Paths.get(System.getProperty("user.home"), "it1901", "mmt", "serverfiles", "testserver-movielist.json");
        this.duration = new Time(2, 22, 25);
        this. releasDate = new Date(2010, 8, 7);
        this.avengers = new Movie("Avengers", duration, releasDate);
        this.dune = new Movie("Dune", duration, releasDate);
    }

    @Override
    protected ResourceConfig configure() {
        final MmtConfig config = new MmtConfig();
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        return config;
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        oMapper = MyMovieConfig.createOMapper();   
    }
    
    @Test
    public void testLoadMovieList(){
        System.out.println("kommer den hit?");
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        assertEquals(200, response.getStatus());
        System.out.println(response+ "dette er responsen");
        try {
            MovieList list = access.loadMovieList();
            assertNotNull(list);
            Assertions.assertEquals(List.of("James Bond", "Harry Potter", "Spider Man"), access.loadMovieList());
            System.out.println(access.loadMovieList() );
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage() + "dette er meldingen");
        }
    }

    
    @Test
    public void testAddMovie(){
        Response response = target(MmtService.MMT_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
        assertEquals(200, response.getStatus());
        try {
            access.addMovie(avengers);
            Assertions.assertEquals(List.of("James Bond", "Harry Potter", "Spider Man", "Avengers"), access.loadMovieList());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    @Test
    public void testDeleteMovie(){
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        assertEquals(200, response.getStatus());
        try {
            access.deleteMovie(avengers);
            Assertions.assertEquals(List.of("James Bond", "Harry Potter", "Spider Man"), access.loadMovieList());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void testUpdateMovie(){
        Response response = target(MmtService.MMT_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
        assertEquals(200, response.getStatus());
        try {
            access.addMovie(avengers);
            access.updateMovie(avengers.getID(), dune);
            Assertions.assertEquals("James Bond", "Harry Potter", "Spider Man","Dune", access.loadMovieList());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private MovieList getTestMovieList(){
        try {
            this.movieList = oMapper.readValue(new File("src/test/resources/mmt/restserver/test-mmtserver.json"), MovieList.class);
            return movieList;
        } catch (Exception e) {
            System.out.println("Failed to get movielist");
            return null;
        }
    }
}
