import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelBox extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Hotel Box Office");

    stage.setScene(createScene(loadMainPane()));

    stage.show();
  }

  /**
   * Loads the main fxml layout.
   * Sets up the view switching HotelNavigator.
   * Loads the first view into the fxml layout.
   *
   * @return the loaded pane.
   * @throws IOException if the pane could not be loaded.
   */
  private Pane loadMainPane() throws IOException {
    FXMLLoader loader = new FXMLLoader();

    Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream
        (HotBoxNavigator.MAIN));

    HotBoxController mainController = loader.getController();

    HotBoxNavigator.setMainController(mainController);
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);

    return mainPane;
  }

  /**
   * Creates the main application scene.
   *
   * @param mainPane the main application layout.
   * @return         the created scene.
   */
  private Scene createScene(Pane mainPane) {
    Scene scene = new Scene(mainPane);
    // Attaches the .css to the scene, all changes and rules in it should
    // propagate throughout the program.
    scene.getStylesheets().add("HotBox.css");

    return scene;
  }

  public static void main(String[] args) {
    launch(args);
  }
}