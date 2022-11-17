package mmt.restserver;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MmtServerTest extends JerseyTest {
    private ObjectMapper oMapper;

    private static final String initMovie =
        "{\"title\":\"Bond\",\"releaseDate\":\"2002-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false,\"cast\":[null],\"ID\":\"id1\"}";
    private static final String movieToBePut =
        "{\"title\":\"James\",\"releaseDate\":\"2002-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false,\"cast\":[null],\"ID\":\"id2\"}";

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
    public void testApiGetMethod() {
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        assertEquals(200, response.getStatus());

        try {
            MovieList movieList = oMapper.readValue(response.readEntity(String.class), MovieList.class);
            assertNotNull(movieList);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testApiPostMethod() {
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .post(Entity.entity(initMovie, MediaType.APPLICATION_JSON));
        assertEquals(200, response.getStatus());
        try {
            boolean post = oMapper.readValue(response.readEntity(String.class), Boolean.class);
            assertTrue(post);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testApiDeleteMethod() {
        Response response = target(MmtService.MMT_SERVICE_PATH)
            .path("/" + "id1")
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .delete();

        assertEquals(200, response.getStatus());

        try {
            boolean movieList = oMapper.readValue(response.readEntity(String.class), Boolean.class);
            assertTrue(movieList);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }

        Response response2 = target(MmtService.MMT_SERVICE_PATH)
            .path("/" + "nonexistingID")
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .delete();

        assertEquals(200, response.getStatus());

        try {
            Boolean movieList = oMapper.readValue(response2.readEntity(String.class), Boolean.class);
            assertFalse(movieList);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testApiPutMethod() {
        target(MmtService.MMT_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .post(Entity.entity(initMovie, MediaType.APPLICATION_JSON));

        Response response = target(MmtService.MMT_SERVICE_PATH)
            .path("/" + "id1")
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .put(Entity.entity(movieToBePut, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());

        try {
            boolean put = oMapper.readValue(response.readEntity(String.class), Boolean.class);
            assertTrue(put);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }

        target(MmtService.MMT_SERVICE_PATH)
            .path("/" + "id2")
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .delete();
    }
}
