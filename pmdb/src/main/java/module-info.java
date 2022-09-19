module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pmdb.fxui to javafx.graphics, javafx.fxml;

}
