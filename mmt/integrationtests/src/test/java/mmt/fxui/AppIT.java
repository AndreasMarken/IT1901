package mmt.fxui;

import java.io.IOException;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppIT extends ApplicationTest  {
    
    private MyMovieTrackerController controller;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MyMovieTracker.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.show();
    }
  
}
