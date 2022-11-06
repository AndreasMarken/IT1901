package mmt.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmt.core.MovieList;



public class MyMovieConfig{

    private ObjectMapper oMapper;
    private Path initPath;
    private Path path;

    public MyMovieConfig(){
        this.oMapper = createOMapper();
        this.initPath = Paths.get(System.getProperty("user.home"), "it1901", "mmt", "serverfiles");
    }

     public void makefile(Path path){
        try {
            Files.createDirectories(this.initPath);
            Files.createFile(path);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("File already exists");
        }
     }

    public void setFilePath(String name){
        this.path = this.initPath.resolve(name);
        makefile(this.path);
    }

    public Path getPath(){
        return this.path;
    }

   public File getFile(){
       return path.toFile();
   }

   public MovieList loadMovieList() throws IOException {
    try (Reader fileReader = new FileReader(path.toFile(), StandardCharsets.UTF_8)) {
      return oMapper.readValue(fileReader, MovieList.class);
    } catch (FileNotFoundException e) {
      return new MovieList();
    }
  }

  public void saveMovieList(MovieList movieList) {
    if (this.path == null) {
      throw new IllegalStateException("Filepath is not set.");
    }
    try (FileWriter fileWriter = new FileWriter(path.toFile(), StandardCharsets.UTF_8)) {
      oMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

   //public void saveMovieList(MovieList movieList){
   //    if (this.path == null){
   //        throw new IllegalStateException("Filepath is not set.");
   //    }
   //    System.out.println("Kommer");
   //    try (Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)){
   //        oMapper.writeValue(getFile(), movieList);     
   //    } catch (Exception e) {
   //        System.out.println("Something went wrong when trying to save movies");
   //    }
   //}

    //public MovieList loadMovieList(){
    //    System.out.println(this.path + "dette er pathen");
    //    try (Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)) { 
    //        System.out.println("Load movielist in mymovieconfig");
    //        return oMapper.readValue(getFile(), MovieList.class);
    //    } catch (Exception e) {
    //        System.out.println("Something went wrong when trying to load file MyMovieCOnfig");
    //        System.out.println(e.getMessage() + "er dette feilmeldingen?");
    //        return new MovieList();
    //        
    //    }
    //}
//
    public ObjectMapper createOMapper() {
        return new ObjectMapper().registerModule(new MovieModule());
    }
}
