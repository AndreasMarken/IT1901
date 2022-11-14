package mmt.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import mmt.core.Movie;
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

    private boolean storeMovieList(MovieList movieList){
        return myMovieConfig.saveMovieList(movieList);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{oldMovieID}")
    public Boolean putMovie(@PathParam("oldMovieID") String oldMovieID, Movie movie){
        IMovie updatedMovie = movie;
        MovieList movieListFromStorage = getMovieList();
        IMovie oldMovie = movieListFromStorage.getMovies().stream().filter(m -> m.getID().equals(oldMovieID)).findAny().orElse(null);
        movieListFromStorage.removeMovie(oldMovie);
        movieListFromStorage.addMovie(updatedMovie);   
        boolean isStored = storeMovieList(movieListFromStorage);
        
        if(isStored){
            LOG.debug("old movie " + oldMovie.getTitle() + " updated and stored. Updated movie:" + movie.getTitle());  
        }
        else{
            LOG.debug("failed to store update of old movie " + oldMovie.getTitle());
        }
        return isStored;
    }

   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addMovie(Movie movie) {
        IMovie movieToAdd = movie;
        MovieList movieListFromStorage = getMovieList();
        movieListFromStorage.addMovie(movieToAdd);
        
        boolean isStored = storeMovieList(movieListFromStorage);        
        if(isStored){
            LOG.debug("Movie " + movie.getTitle() + " successfully stored");  
        }
        else{
            LOG.debug("Failed to store movie: " + movie.getTitle());
        }
        return isStored;    
    }

    //TODO never used?
    // @GET
    // @Path("/{movie}")
    // public IMovie getMovie(@PathParam("movie") String movie) {
    //   IMovie movieFromMovieList = getMovieList().getMovie(movie);
    //   if (movieFromMovieList == null){
    //     LOG.debug("requested movie does not exist!");
    //   }
    //   else{
    //     LOG.debug("getting movie " + movie);
    //   }
    //   return getMovieList().getMovie(movie);
    // }

    @DELETE
    @Path("/{movieID}")
    public boolean deleteMovie(@PathParam("movieID") String movieID) {
      MovieList movieListFromStorage = getMovieList();
      IMovie movieToDelete = movieListFromStorage.getMovies().stream().filter(m -> m.getID().equals(movieID)).findAny().orElse(null);

      if (movieToDelete == null){
        LOG.debug("the movie requested to delete does not exist!");
        return false;
      }
      else{
        movieListFromStorage.removeMovie(movieToDelete);
        boolean isStored = storeMovieList(movieListFromStorage);        
        if(isStored){
            LOG.debug("Movie " + movieToDelete.getTitle() + " successfully deleted from storage");  
        }
        else{
            LOG.debug("Failed to delete movie from storage: " + movieToDelete.getTitle());
        }
        return isStored;  
      }
    }

}