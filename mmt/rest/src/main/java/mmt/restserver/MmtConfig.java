package mmt.restserver;

import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * MmtConfig extends ResourceConfig.
 */
public class MmtConfig extends ResourceConfig {
    private MyMovieConfig myMovieConfig;

    /**
     * Constructor for MmtConfig.
     */
    public MmtConfig() {
        myMovieConfig = new MyMovieConfig();
        myMovieConfig.setFilePath("server-movielist.json");
        register(MmtService.class);
        register(JacksonFeature.class);
        register(MmtModuleObjectMapperProvider.class);
        register(
            new AbstractBinder() {

                @Override
                protected void configure() {
                    bind(MmtConfig.this.myMovieConfig);
                }
            }
        );
    }
}
