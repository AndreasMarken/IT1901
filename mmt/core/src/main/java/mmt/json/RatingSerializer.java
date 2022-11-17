package mmt.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import mmt.core.Rating;

/**
 * Class to serialize (object to text) Rating objects.
 */
public class RatingSerializer extends JsonSerializer<Rating> {

    /**
     * Method to serialize (object to text) Rating objects.
     * Format:
     * {
     * "rating" :  ...
     * "comment" : "..."
     * }
     *
     * @param rating Rating object to serialize
     * @param jsonGen JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException Method could throw IOException
     */
    @Override
    public void serialize(Rating rating, JsonGenerator jsonGen, SerializerProvider serializerProvider)
        throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeNumberField("rating", rating.getRating());
        jsonGen.writeStringField("comment", rating.getComment());
        jsonGen.writeEndObject();
    }
}
