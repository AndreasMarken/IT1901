module app {
    requires transitive com.fasterxml.jackson.databind;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens mmt.fxui to javafx.graphics, javafx.fxml;

}
