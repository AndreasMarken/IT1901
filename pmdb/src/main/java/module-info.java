module app {
    requires transitive com.fasterxml.jackson.databind;
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pmdb.fxui to javafx.graphics, javafx.fxml;

}
