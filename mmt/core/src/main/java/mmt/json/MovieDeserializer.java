package mmt.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import mmt.core.Actor;
import mmt.core.Movie;
import mmt.core.Rating;

/**
 * Class to deserialize (text to object) Movie objects.
 */
public class MovieDeserializer extends JsonDeserializer<Movie> {
    private RatingDeserializer ratingDeserializer = new RatingDeserializer();
    private ActorDeserializer actorDeserializer = new ActorDeserializer();

    /**
     * Method to deserialize (text to object) Movie objects.
     * Format:
     * {
     * "title": "title"
     * "releaseDate": "yyyy-mm-dd"
     * "duration": "hh:mm:ss"
     * "rating": [...]
     * "watchlist": true/false
     * }
     *
     * @param parser JsonParser
     * @param ctxt DeserializationContext
     * @return Deserialized Movie object
     * @throws IOException Method could throw IOException
     * @throws JacksonException Method could throw JacksonException
     */
    @Override
    public Movie deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Method to deserialize (text to object) Movie objects.
     * Format:
     * {
     * "title": "title"
     * "releaseDate": "yyyy-mm-dd"
     * "duration": "hh:mm:ss"
     * "rating": [...]
     * "watchlist": true/false
     * "cast": [...]
     * }
     *
     * @param jsonNode JsonNode
     * @return Deserialized Movie object
     */
    public Movie deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            JsonNode titleNode = jsonNode.get("title");
            JsonNode releaseDateNode = jsonNode.get("releaseDate");
            JsonNode durationNode = jsonNode.get("duration");
            JsonNode ratingNode = jsonNode.get("rating");
            JsonNode watchListNode = jsonNode.get("watchlist");
            JsonNode castNode = jsonNode.get("cast");
            JsonNode idNode = jsonNode.get("ID");
            Boolean nodesAreValidInstances =
                (
                    titleNode instanceof TextNode &&
                    releaseDateNode instanceof TextNode &&
                    durationNode instanceof TextNode &&
                    watchListNode instanceof BooleanNode &&
                    idNode instanceof TextNode
                );

            if (nodesAreValidInstances) {
                String title = titleNode.asText();
                String releaseDate = releaseDateNode.asText();
                String duration = durationNode.asText();
                String id = idNode.asText();
                boolean watchlist = watchListNode.asBoolean();
                Date date = Date.valueOf(
                    Integer.parseInt((String) releaseDate.substring(0, 4)) +
                    "-" +
                    Integer.parseInt((String) releaseDate.substring(5, 7)) +
                    "-" +
                    Integer.parseInt((String) releaseDate.substring(8, 10))
                );
                Time time = Time.valueOf(
                    Integer.parseInt((String) duration.substring(0, 2)) +
                    ":" +
                    Integer.parseInt((String) duration.substring(3, 5)) +
                    ":" +
                    "" +
                    Integer.parseInt((String) duration.substring(6, 8))
                );
                Movie movie = new Movie(title, time, date, id);
                movie.setOnTakeOfWatchlist(watchlist);

                if (ratingNode instanceof ObjectNode) {
                    Rating rating = ratingDeserializer.deserialize((ObjectNode) ratingNode);
                    movie.setRating(rating);
                }
                if (castNode instanceof ArrayNode) {
                    for (JsonNode actorNode : ((ArrayNode) castNode)) {
                        Actor actor = actorDeserializer.deserialize(actorNode);
                        if (actor != null) {
                            try {
                                movie.addActor(actor);
                            } catch (IllegalArgumentException e) {
                                //If A movie was attempted added multiple times, skip the movie
                            }
                        }
                    }
                }
                return movie;
            }
        }
        return null;
    }
}
