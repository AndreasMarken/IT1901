package mmt.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mmt.core.Actor;

public class ActorSerializer extends JsonSerializer<Actor> {

    /** Method to serialize (object to text) Actor objects
     * Format: 
     * {
     * "name" : "..."
     * }
     * 
     * @param actor Actor object to serialize
     * @param jsonGen JsonGenerator
     * @param serializerProvider SerializerProvider
     * @throws IOException Method could throw IOException
     */
    @Override
    public void serialize(Actor actor, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", actor.getName());
        jsonGen.writeEndObject();
    }
    
}
