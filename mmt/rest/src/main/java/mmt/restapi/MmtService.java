package mmt.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;

@Path(MmtService.MMT_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MmtService {

    public static final String MMT_SERVICE_PATH = "mmt";
    private static final Logger LOG = LoggerFactory.getLogger(MmtService.class);

    @Context
    private MyMovieConfig myMovieConfig= new MyMovieConfig();

    @GET
    public MovieList getMovieList(){
        try{
            MovieList movieList = myMovieConfig.loadMovieList();
            LOG.debug("List of movies to get:" + movieList.toString());
            return movieList;
        } catch (Exception e){
            LOG.debug("The service failed to get MovieList, returning a new MovieList.");
            return new MovieList();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void putMovieList(MovieList movieList){
        LOG.debug("List of movie to put:" + movieList.toString());
        myMovieConfig.saveMovieList(movieList);
    }
}