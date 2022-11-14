package mmt.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import mmt.json.MovieModule;
import jakarta.ws.rs.core.MediaType;

/**
* Provides a objectmapper to MmtConfig
*
*/


@Provider 
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MmtModuleObjectMapperProvider implements ContextResolver<ObjectMapper>{

    private ObjectMapper oMapper;


    public MmtModuleObjectMapperProvider (){
        this.oMapper = new ObjectMapper().registerModule(new MovieModule());
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return oMapper;
    }
}
