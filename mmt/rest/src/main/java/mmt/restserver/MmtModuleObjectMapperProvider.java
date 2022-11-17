package mmt.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import mmt.json.MovieModule;

/**
 * MmtModuleObjectMapperProvider implements ContextResolver.
 * Contains an ObjectMapper.
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MmtModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private ObjectMapper omapper;

    /**
     * Constructor for MmtModuleObjectMapperProvider.
     */
    public MmtModuleObjectMapperProvider() {
        this.omapper = new ObjectMapper().registerModule(new MovieModule());
    }

    /**
     * Returns the ObjectMapper of the class.
     *
     * @return ObjectMapper
     */
    @Override
    public ObjectMapper getContext(Class<?> arg0) {
        return omapper;
    }
}
