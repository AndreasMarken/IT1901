package mmt.restserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;

public class MmtServerTest extends JerseyTest{
    
    private ObjectMapper oMapper;


    @Override
    protected ResourceConfig configure() {
        final MmtConfig config = new MmtConfig();
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        return config;
    }

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        oMapper = MyMovieConfig.createOMapper();

    }

    @Test
    public void testApiGetMethod(){
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        System.out.println(response);
        assertEquals(200, response.getStatus());

        try {
            MovieList movieList = oMapper.readValue(response.readEntity(String.class), MovieList.class);
            assertNotNull(movieList);
            
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }
}
