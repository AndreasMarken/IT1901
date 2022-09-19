module app {
    requires javafx.controls;
    requires javafx.fxml;

    opens pmdb.fxui to javafx.graphics, javafx.fxml;

}
