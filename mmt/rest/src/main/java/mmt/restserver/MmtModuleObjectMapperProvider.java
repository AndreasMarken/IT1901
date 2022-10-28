package mmt.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.ext.ContextResolver;
import mmt.json.MovieModule;

public class MmtModuleObjectMapperProvider implements ContextResolver<ObjectMapper>{

    private ObjectMapper oMapper;


    public MmtModuleObjectMapperProvider (){
        this.oMapper = new ObjectMapper().registerModule(new MovieModule());
    }

    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        // TODO Auto-generated method stub
        return oMapper;
    }
}


