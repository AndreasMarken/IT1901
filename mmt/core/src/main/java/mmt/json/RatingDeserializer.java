package mmt.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import mmt.core.Rating;

public class RatingDeserializer extends JsonDeserializer<Rating> {

    /*
     * Format: 
     * {
     * "rating" :  ...
     * "comment" : "..."
     * }
     */

    @Override
    public Rating deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode treeNode = p.getCodec().readTree(p);
        return deserialize((JsonNode) treeNode);
    }
    
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
