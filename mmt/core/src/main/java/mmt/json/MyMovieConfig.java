package mmt.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmt.core.MovieList;

/**
 * MyMovieConfig: Class to load and save movies when connected to server.
 */
public class MyMovieConfig{

    private ObjectMapper oMapper;
    private Path initPath;
    private Path path;


    /**
    * Constructor setting the objectmapper and the initpath.
    */
    public MyMovieConfig(){
        this.oMapper = createOMapper();
        this.initPath = Paths.get(System.getProperty("user.home"), "it1901", "mmt", "serverfiles");
    }

     public void makefile(Path path){
        try {
            Files.createDirectories(this.initPath);
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("File already exists");
        }
     }

    /**
    * Creates folder and file from the given path.
    *
    * @param path path to folder
    */
    public void makefile(Path path){
       try {
           Files.createDirectories(this.initPath);
           Files.createFile(path);
       } catch (IOException e) {
           System.out.println("File already exists");
       }
    }

    /**
    * Sets the filename.
    *
    * @param filename name of file
    */
    public void setFilePath(String filename){
        this.path = this.initPath.resolve(filename);
        makefile(this.path);
    }

    /**
     * Get the path to save MovieList.
     *
     * @return the path
     */
    public Path getPath(){
        return this.path;
    }

    /**
     * Gets the file on this.path.
     *
     * @return the File
     */
    public File getFile(){
      return path.toFile();
    }


  /**
   * Loads movielist from json file. If the json file does not exist it returns an empty movieList.
   *
   * @return movielist.
   * @throws IOException when reading from json file fails.
   */ 
   public MovieList loadMovieList() throws IOException {
    try (Reader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
      return oMapper.readValue(fileReader, MovieList.class);
    } catch (FileNotFoundException e) {
      System.out.println("Something went wrong trying to load MovieList, returning new MovieList.");
      return new MovieList();
    }
  }

   /**
   * Saves the movielist to json file.
   *
   * @param movieList the movielist object to be saved.
   */
  public void saveMovieList(MovieList movieList) {
    if (this.path == null) {
      throw new IllegalStateException("Filepath is not set.");
    }
    try (FileWriter fileWriter = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) {
      oMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
      return true;
    } catch (Exception e) {
      System.out.println("Something went wrong trying to save MovieList: " + e);
      return false;
    }
  }
    /**
     * Creates a new ObjectMapper.
     *
     * @return ObjectMapper
     */
    public static ObjectMapper createOMapper() {
        return new ObjectMapper().registerModule(new MovieModule());
    }
}
