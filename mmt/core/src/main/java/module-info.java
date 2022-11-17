module mmt.core {
    requires transitive com.fasterxml.jackson.databind;

    requires transitive java.sql;

    opens mmt.json ;

    exports mmt.core ;
    exports mmt.json ;
}
