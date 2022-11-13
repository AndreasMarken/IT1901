package mmt.fxui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MmtAppIT extends ApplicationTest  {
    
    private MyMovieTrackerController controller;
    private EditMovieController editController;

    @BeforeAll
    public static void headless() {
      MmtApp.headless();
    }

    @Override
    public void start(final Stage stage) throws Exception {
      final FXMLLoader loader = new FXMLLoader(getClass().getResource("MyMovieTracker.fxml"));
      final Parent root = loader.load();
      this.controller = loader.getController();
      stage.setScene(new Scene(root));
      stage.show();
    }


    @BeforeEach
    public void setUp(){
      try {
        String port = System.getProperty("mmt.port");
        System.out.println(System.getProperty("mmt.port"));
        assertNotNull(port, "mmt.port currently not set or available");
        URI baseUri = new URI("http://localhost:" + port + "/mmt/");
        //System.out.println(baseUri);
      //this.controller.setTodoModelAccess(new RemoteTodoModelAccess(baseUri));

      } catch (Exception e) {
        fail(e.getMessage());
      }
    }

    @Test
    public void testController() {
    assertNotNull(this.controller);
  }
  
}
