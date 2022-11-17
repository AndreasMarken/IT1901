package mmt.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import mmt.core.Actor;

/**
 * Class to deserialize (text to object) Actor objects.
 */
public class ActorDeserializer extends JsonDeserializer<Actor> {

    /**
     * Method to deserialize (text to object) Actor objects.
     * Format:
     * {
     * "name" : "..."
     * }
     *
     * @param parser JsonParser
     * @param ctxt DeserializationContext
     * @return Deserialized Actor object
     * @throws IOException Method could throw IOException
     * @throws JacksonException Method could throw JacksonException
     */
    @Override
    public Actor deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    /**
     * Method to deserialize (text to object) Actor objects.
     * Format:
     * {
     * "name" : "..."
     * }
     *
     * @param jsonNode JsonNode
     * @return Deserialized Actor object
     */
    public Actor deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            JsonNode nameNode = jsonNode.get("name");

            if (nameNode instanceof TextNode) {
                Actor actor = new Actor(nameNode.asText());
                return actor;
            }
        }
        return null;
    }
}
