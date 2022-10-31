package mmt.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import mmt.core.IMovie;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;

@Path(MmtService.MMT_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MmtService {

    public static final String MMT_SERVICE_PATH = "mmt";
    private static final Logger LOG = LoggerFactory.getLogger(MmtService.class);

    @Context
    private MyMovieConfig myMovieConfig= new MyMovieConfig();

    /*
     * Dette er hva slags info som vises når man går på localhos/(m.m)/mmt
     * Så på en måte det nederste rotnivået av serveren vår
     * Og den skal da vise listen med filmer
     * 
     * Denne metoden henter filmene som vises på serveren
     */
    @GET
    public MovieList getMovieList(){
        MovieList movieList = myMovieConfig.loadMovieList();
        LOG.debug("List of movies to get:" + movieList);
        return movieList;
    }

    @GET
    @Path("/{movieTitle}")
    public IMovie getMovieByTitle(@PathParam("movieTitle") String movieTitle) {
        LOG.debug("getMovieByTitle({})", movieTitle);
        return getMovieList().getMovie(movieTitle);
    }

    /*
     * Dette er da PUT requesten til REST API-et. 
     * 
     * Dette setter en movielist til serveren.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void putMovieList(MovieList movieList){
        LOG.debug("List of movie to put:" + movieList);
        myMovieConfig.saveMovieList(movieList);
    }
}