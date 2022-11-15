package mmt.restserver;

import java.nio.file.Paths;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;

/**
 * MmtConfig extends ResourceConfig.
 */
public class MmtConfig extends ResourceConfig{
    private MyMovieConfig myMovieConfig;

    /**
    * Constructor for MmtConfig.
    */
    public MmtConfig() {
        myMovieConfig = new MyMovieConfig();
        MyMovieConfig.filepath = Paths.get(System.getProperty("user.home"), "it1901", "mmt", "serverfiles", "server-movielist.json");
        register(MmtService.class);
        register(JacksonFeature.class);
        register(MmtModuleObjectMapperProvider.class);
        register(new AbstractBinder() {
            @Override
            protected void configure(){
                bind(MmtConfig.this.myMovieConfig);
            }
        });
    }
}
