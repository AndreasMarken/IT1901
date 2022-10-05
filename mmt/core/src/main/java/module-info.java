module mmt.core {
    requires transitive com.fasterxml.jackson.databind;

    requires transitive java.sql;
    
    exports mmt.core;
    exports mmt.json;
}
