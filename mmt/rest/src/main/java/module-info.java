module mmt.rest {
    requires mmt.core;
    requires jakarta.ws.rs;

    requires org.glassfish.hk2.api;
    requires org.slf4j;
    
    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;
}