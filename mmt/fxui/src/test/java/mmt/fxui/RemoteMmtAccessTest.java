package mmt.fxui;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import mmt.core.Movie;
import mmt.core.MovieList;
import mmt.json.MyMovieConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoteMmtAccessTest {
    private ObjectMapper oMapper;

    private WireMockConfiguration config;
    private WireMockServer wireMockServer;
    private RemoteMmtAccess mmtAccess;
    private Movie avengers, newMovie;

    private MovieList movieList;

    private static final String movieListWithThreeMovies =
        "{\"movies\":[{\"title\":\"James Bond\",\"releaseDate\":\"2022-09-09\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120001\"},{\"title\":\"Lange flate ballær\",\"releaseDate\":\"2022-09-05\",\"duration\":\"02:03:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120003\"},{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"e65b957e-6415-11ed-81ce-0242ac120002\"}]}";
    private static final String movie =
        "{\"title\":\"iodhosa\",\"releaseDate\":\"2022-09-02\",\"duration\":\"02:02:00\",\"rating\":null,\"watchlist\":false,\"cast\":[null],\"ID\":\"id1\"}";
    private static final String movie2 =
        "{\"title\":\"James\",\"releaseDate\":\"2002-01-02\",\"duration\":\"01:50:00\",\"rating\":{\"rating\":9,\"comment\":\"Very good.\"},\"watchlist\":false,\"cast\":[null],\"ID\":\"id2\"}";

    public RemoteMmtAccessTest() {
        this.avengers = new Movie("Avengers", Time.valueOf("02:22:25"), Date.valueOf("2010-08-07"));
        this.oMapper = MyMovieConfig.createOMapper();
        try {
            this.newMovie = oMapper.readValue(movie, Movie.class);
        } catch (Exception e) {}
    }

    @BeforeEach
    public void startWireMockServerAndSetup() throws URISyntaxException {
        config = WireMockConfiguration.wireMockConfig().port(8089);
        wireMockServer = new WireMockServer(config.portNumber());
        wireMockServer.start();
        WireMock.configureFor("localhost", config.portNumber());
        mmtAccess = new RemoteMmtAccess("http://localhost:" + wireMockServer.port() + "/mmt/");
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void testLoad() {
        stubFor(
            get(urlEqualTo("/mmt/"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(movieListWithThreeMovies)
                )
        );

        try {
            this.movieList = mmtAccess.loadMovieList();
        } catch (Exception e) {}
        assertNotNull(this.movieList);
        assertEquals(List.of("James Bond", "Lange flate ballær", "iodhosa").toString(), this.movieList.toString());
    }

    @Test
    public void testAdd() {
        stubFor(
            post(urlEqualTo("/mmt/"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true"))
        );

        assertTrue(mmtAccess.addMovie(avengers));
    }

    @Test
    public void testDelete() {
        stubFor(
            delete(urlEqualTo("/mmt/id1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true"))
        );

        assertTrue(mmtAccess.deleteMovie(newMovie));
    }

    @Test
    public void testDeleteFail() {
        stubFor(
            delete(urlEqualTo("/mmt/id2"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(movie2))
        );

        assertFalse(mmtAccess.deleteMovie(newMovie));
    }

    @Test
    public void testUpdate() {
        stubFor(
            put(urlEqualTo("/mmt/id1"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true"))
        );

        assertTrue(mmtAccess.updateMovie(avengers, "id1"));
    }

    @Test
    public void testUpdateFail() {
        stubFor(
            put(urlEqualTo("/mmt/id2"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(movie2))
        );

        assertFalse(mmtAccess.updateMovie(avengers, "id1"));
    }
}
