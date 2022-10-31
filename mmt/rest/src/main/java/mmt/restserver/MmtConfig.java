package mmt.restserver;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.core.util.JacksonFeature;

import mmt.json.MyMovieConfig;
import mmt.restapi.MmtService;

public class MmtConfig extends ResourceConfig{
    private MyMovieConfig myMovieConfig;

    public MmtConfig() {
        myMovieConfig = new MyMovieConfig();
        myMovieConfig.setFilePath("server-movielist.json");
        register(MmtService.class);
        register(MmtModuleObjectMapperProvider.class);
        register(JacksonFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure(){
                bind(MmtConfig.this.myMovieConfig);
            }
        });
    }
}
