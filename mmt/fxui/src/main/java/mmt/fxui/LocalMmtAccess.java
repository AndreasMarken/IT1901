package mmt.fxui;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import mmt.core.MovieList;
import mmt.json.MovieModule;

public class LocalMmtAccess implements IAccess {

    private boolean testingMode = false;
    private ObjectMapper mapper;

    public LocalMmtAccess(){
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
        
        //this.movieList = mapper.readValue(new File("movie.json"), MovieList.class);
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


    private Path getSaveFilePath(String fileName) {
        return getSaveFolderPath().resolve(fileName);
    }

    private Path getSaveFolderPath() {
        return Path.of(System.getProperty("user.home"), "it1901", "mmt", "saveFiles");
    }
}
