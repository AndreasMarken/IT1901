package mmt.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import mmt.core.Movie;
import mmt.core.MovieList;

public class MovieListDeserializer extends JsonDeserializer<MovieList> {

    private MovieDeserializer movieDeserializer = new MovieDeserializer();

    @Override
    public MovieList deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        MovieList movieList = new MovieList();
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode objectNode) {
            JsonNode moviesNode = objectNode.get("movies");
            if (moviesNode instanceof ArrayNode) {
                for (JsonNode movieNode : ((ArrayNode) moviesNode)) {
                    Movie movie = movieDeserializer.deserialize(movieNode);
                    if (movie != null) {
                        try {
                            movieList.addMovie(movie);
                        } catch (IllegalArgumentException e) {
                            //If A movie was attempted added multiple times, skip the movie
                        } 
                    }
                }
            }
        }
        return movieList;
    }   
    
}
