module mmt.core {
    requires transitive com.fasterxml.jackson.databind;

    requires java.sql;
    
    exports mmt.core;
    exports mmt.json;
}
