module mmt.fxui {
    requires com.fasterxml.jackson.databind;

    requires mmt.core;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;

    opens mmt.fxui to javafx.graphics, javafx.fxml;
}
