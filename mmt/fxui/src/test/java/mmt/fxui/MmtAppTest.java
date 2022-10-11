package mmt.fxui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;

import org.testfx.util.WaitForAsyncUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class MmtAppTest extends ApplicationTest{

    private MyMovieTrackerController controller;
    private Parent parent;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MyMovieTracker.fxml"));
        parent = fxmlLoader.load();
        stage.setResizable(false);
        this.controller = fxmlLoader.getController();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public Parent getParent(){
        return parent;
    }

   
    @Test
    public void testTodoSettings() {
        clickOn("#addNewMovie");
        WaitForAsyncUtils.waitForFxEvents();
  }


}
