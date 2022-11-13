package mmt.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
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

    @GET
    public MovieList getMovieList(){
        try{
            MovieList movieList = myMovieConfig.loadMovieList();
            LOG.debug("MovieList successfully loaded: " + movieList.toString());
            return movieList;
        } catch (Exception e){
            LOG.debug("The service failed to load MovieList, returning a new MovieList.");
            return new MovieList();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void putMovieList(MovieList movieList){
        LOG.debug("MovieList successfully updated:" + movieList.toString());
        myMovieConfig.saveMovieList(movieList);
    }

   
    @GET
    @Path("/{movie}")
    public IMovie getMovie(@PathParam("movie") String movie) {
      IMovie movieFromMovieList = getMovieList().getMovie(movie);
      if (movieFromMovieList == null){
        LOG.debug("requested movie does not exist!");
      }
      else{
        LOG.debug("getting movie " + movie);
      }
      return getMovieList().getMovie(movie);
    }

    @DELETE
    @Path("/{movie}")
    public void deleteMovie(@PathParam("movie") String movie) {
      MovieList movieListFromFile = getMovieList();
      IMovie movieToDelete = movieListFromFile.getMovie(movie);

      if (movieToDelete == null){
        LOG.debug("the movie requested to delete does not exist!");
      }
      else{
        movieListFromFile.removeMovie(movieToDelete);
        putMovieList(movieListFromFile);
        LOG.debug("movie " + movie + " successfully deleted");
      }
    }

}