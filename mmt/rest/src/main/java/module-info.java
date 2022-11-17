module mmt.rest {
    requires transitive mmt.core;
    requires jakarta.ws.rs;

    requires java.net.http;
    requires org.glassfish.hk2.api;
    requires org.slf4j;

    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;
    exports mmt.restapi ;

    opens mmt.restapi to jersey.server;
}
