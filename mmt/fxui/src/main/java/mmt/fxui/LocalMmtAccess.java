package mmt.fxui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MovieModule;

/**
 * RemoteMmtAccess implements IAccess.
 * Handles local saving/loading.
 */
public class LocalMmtAccess implements IAccess {
    private boolean testingMode = false;
    private ObjectMapper mapper;

    public LocalMmtAccess() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public MovieList loadMovieList() throws IOException {
        mapper.registerModule(new MovieModule());
        MovieList temporaryMovieList = loadMovieListFromFile();
        return temporaryMovieList;
    }

    @Override
    public void saveMovieList(MovieList movieList) throws IOException {
        if (testingMode) {
            mapper.writeValue(getSaveFilePath("movieTest.json").toFile(), movieList);
        } else {
            mapper.writeValue(getSaveFilePath("movie.json").toFile(), movieList);
        }
    }

    /**
     * Loads movies form the given file.
     *
     * @return MovieList: An object that contains a list of movies.
     * @throws IOException If the movies cannot be loaded from the file.
     */
    protected MovieList loadMovieListFromFile() throws IOException {
        //If the filepath does not exist, it will be generated.
        Files.createDirectories(getSaveFolderPath());
        try {
            if (testingMode) {
                Files.createFile(getSaveFilePath("movieTest.json"));
            } else {
                Files.createFile(getSaveFilePath("movie.json"));
            }
        } catch (FileAlreadyExistsException e) {
            //If the file already exist, FileAlreadyExistException will be thrown.
            //Do nothing if the file already exists
        }
        
        try {
            if (testingMode) {
                return mapper.readValue(getSaveFilePath("movieTest.json").toFile(), MovieList.class);
            }
            MovieList list = mapper.readValue(getSaveFilePath("movie.json").toFile(), MovieList.class);
            return list;
        } catch (MismatchedInputException e) {
            return new MovieList();
            //If there is no information stored in the file, return a new instance of a movielist.
        }
    }

    public void setTestMode(boolean testingMode) throws IOException {
        this.testingMode = testingMode;
    }

    /**
     * Gets the path to file.
     *
     * @param fileName the file you want the path to
     * @return path to file
     */
    private Path getSaveFilePath(String fileName) {
        return getSaveFolderPath().resolve(fileName);
    }

    /**
     * Gets path to folder.
     *
     * @return path to folder
     */
    private Path getSaveFolderPath() {
        return Path.of(System.getProperty("user.home"), "it1901", "mmt", "saveFiles");
    }

    @Override
    public boolean addMovie(Movie movie) {
        try {
            MovieList ml = loadMovieList();
            ml.addMovie(movie);
            saveMovieList(ml);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteMovie(Movie movie) {
        try {
            MovieList ml = loadMovieList();
            String movieID = movie.getID();
            Movie movieToBeRemoved = (Movie) ml
                .getMovies()
                .stream()
                .filter(m -> m.getID().equals(movieID))
                .findAny()
                .orElse(null);
            System.out.println(ml.toString());
            ml.removeMovie(movieToBeRemoved);
            System.out.println(ml.toString());
            saveMovieList(ml);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updateMovie(Movie movie, String oldMovieID) {
        try {
            MovieList ml = loadMovieList();
            Movie oldMovie = (Movie) ml
                .getMovies()
                .stream()
                .filter(m -> m.getID().equals(oldMovieID))
                .findAny()
                .orElse(null);
            ml.removeMovie(oldMovie);
            ml.addMovie(movie);
            saveMovieList(ml);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
