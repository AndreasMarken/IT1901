package mmt.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import mmt.core.IMovie;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that defines the supported HTTP requests.
 * For further information visit README in mmt/rest/
 */
@Path(MmtService.MMT_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MmtService {
    public static final String MMT_SERVICE_PATH = "mmt";
    private static final Logger LOG = LoggerFactory.getLogger(MmtService.class);

    @Context
    public MyMovieConfig myMovieConfig = new MyMovieConfig();

    /**
     * GET endpoint ".../mmt/"
     *
     * @return Movie list stored in storage if movieList was successfully retrieved from storage, null otherwise
     */
    @GET
    public MovieList getMovieList() {
        try {
            MovieList movieList = myMovieConfig.loadMovieList();
            LOG.debug("MovieList successfully loaded: " + movieList.toString());
            return movieList;
        } catch (Exception e) {
            LOG.debug("The service failed to load MovieList, returning a new MovieList.");
            return new MovieList();
        }
    }

    private boolean storeMovieList(MovieList movieList) {
        return myMovieConfig.saveMovieList(movieList);
    }

    /**
     * PUT endpoint ".../mmt/{oldMovieID}".
     *
     * @param oldMovieID the ID of the movie to be updated
     * @param movie the updated version of the movie corresponding to oldMovieID
     * @return true if the movie was successfully updated in storage, false otherwise
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{oldMovieID}")
    public Boolean putMovie(@PathParam("oldMovieID") String oldMovieID, Movie movie) {
        IMovie updatedMovie = movie;
        MovieList movieListFromStorage = getMovieList();
        IMovie oldMovie = movieListFromStorage
            .getMovies()
            .stream()
            .filter(m -> m.getID().equals(oldMovieID))
            .findAny()
            .orElse(null);
        movieListFromStorage.removeMovie(oldMovie);
        movieListFromStorage.addMovie(updatedMovie);
        boolean isStored = storeMovieList(movieListFromStorage);

        if (isStored) {
            LOG.debug("old movie " + oldMovie.getTitle() + " updated and stored. Updated movie:" + movie.getTitle());
        } else {
            LOG.debug("failed to store update of old movie " + oldMovie.getTitle());
        }
        return isStored;
    }

    /**
     * POST endpoint ".../mmt/".
     *
     * @param movie the movie to be added to the movieList in storage
     * @return true if the movie was successfully stored in storage, false otherwise
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean addMovie(Movie movie) {
        IMovie movieToAdd = movie;
        MovieList movieListFromStorage = getMovieList();
        movieListFromStorage.addMovie(movieToAdd);

        boolean isStored = storeMovieList(movieListFromStorage);
        if (isStored) {
            LOG.debug("Movie " + movie.getTitle() + " successfully stored");
        } else {
            LOG.debug("Failed to store movie: " + movie.getTitle());
        }
        return isStored;
    }

    /**
     * DELETE endpoint ".../mmt/{movieID}".
     *
     * @param movieID the ID of the movie in storage to be deleted
     * @return true if the movie with id movieID was successfully deleted in storage, false otherwise
     */
    @DELETE
    @Path("/{movieID}")
    public boolean deleteMovie(@PathParam("movieID") String movieID) {
        MovieList movieListFromStorage = getMovieList();
        IMovie movieToDelete = movieListFromStorage
            .getMovies()
            .stream()
            .filter(m -> m.getID().equals(movieID))
            .findAny()
            .orElse(null);

        if (movieToDelete == null) {
            LOG.debug("the movie requested to delete does not exist!");
            return false;
        } else {
            movieListFromStorage.removeMovie(movieToDelete);
            boolean isStored = storeMovieList(movieListFromStorage);
            if (isStored) {
                LOG.debug("Movie " + movieToDelete.getTitle() + " successfully deleted from storage");
            } else {
                LOG.debug("Failed to delete movie from storage: " + movieToDelete.getTitle());
            }
            return isStored;
        }
    }
}
