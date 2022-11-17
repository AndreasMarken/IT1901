package mmt.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import mmt.core.Rating;

/**
 * Class to deserialize (text to object) Rating objects.
 */
public class RatingDeserializer extends JsonDeserializer<Rating> {

    /**
     * Method to deserialize (text to object) Rating objects.
     * Format:
     * {
     * "rating" :  ...
     * "comment" : "..."
     * }
     *
     * @param parser JsonParser
     * @param ctxt DeserializationContext
     * @return Deserialized Rating object
     * @throws IOException Method could throw IOException
     * @throws JacksonException Method could throw JacksonException
     */
    @Override
    public Rating deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Method to deserialize (text to object) Rating objects.
     * Format:
     * {
     * "rating" :  ...
     * "comment" : "..."
     * }
     *
     * @param jsonNode JsonNode
     * @return Deserialized Rating object
     */
    public Rating deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            JsonNode ratingNode = objectNode.get("rating");

            Rating rating;

            if (ratingNode instanceof IntNode) {
                rating = new Rating(((IntNode) ratingNode).asInt());
                JsonNode commentNode = objectNode.get("comment");

                if (commentNode instanceof TextNode) {
                    rating.setComment(((TextNode) commentNode).asText());
                }
                return rating;
            }
        }
        return null;
    }
}
