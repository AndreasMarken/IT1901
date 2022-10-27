package mmt.json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import mmt.core.MovieList;



public class MyMovieConfig{

    private ObjectMapper oMapper;
    private Path path = null;

    public MyMovieConfig(){
        this.oMapper = createOMapper();
    }

    public void setFilePath(String name){
        this.path = Paths.get(System.getProperty("user.home", name));
    }

    public File getFile(){
        return path.toFile();
    }

    public void saveMovieList(MovieList movieList){
        try (Reader reader = new FileReader(getFile(), StandardCharsets.UTF_8)){
            oMapper.writeValue(getFile(), movieList);     
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to save movies");
        }
    }

    public MovieList loadMovieList(){
        try (Writer writer = new FileWriter(getFile(), StandardCharsets.UTF_8)) {
            return oMapper.readValue(getFile(), MovieList.class);
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to load files");
            return null;
            
        }
    }

    public ObjectMapper createOMapper() {
        return new ObjectMapper().registerModule(new MovieModule());
    }
}
