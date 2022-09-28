module mmt.fxui {
    requires transitive com.fasterxml.jackson.databind;
    
    requires mmt.core;
    
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens mmt.fxui to javafx.graphics, javafx.fxml;
}
