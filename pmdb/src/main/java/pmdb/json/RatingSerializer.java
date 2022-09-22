package pmdb.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pmdb.core.Rating;

public class RatingSerializer extends JsonSerializer<Rating> {

     /*
     * Format: 
     * {
     * "rating" :  ...
     * "comment" : "..."
     * }
     */

    @Override
    public void serialize(Rating rating, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeNumberField("rating", rating.getRating());
        jsonGen.writeStringField("comment", rating.getComment());
        jsonGen.writeEndObject();
    }
}
