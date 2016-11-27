import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the program, starts the GUI.
 *
 * @author Tyler Bratton
 */
public class HotelBox extends Application {

  // Initialize database connection
  public static DatabaseConnection dbConnection = new DatabaseConnection();
  // Allows MovieGridController to access the current window size.
  public static Stage testStage;
  private static String currentUserId = "-1";
  private static BooleanProperty isAdmin = new SimpleBooleanProperty(false);
  private static BooleanProperty isLoggedIn = new SimpleBooleanProperty(false);

  @Override
  public void start(Stage stage) throws Exception {
    GeneralUtilities.initializeLocalCache();
    stage.setTitle("Hotel Box Office");
    testStage = stage;
    stage.setScene(createScene(loadMainPane()));
    stage.getIcons().add(new Image(HotelBox.class.getResourceAsStream(
        "/images/icon.png")));
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

    Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(
        HotBoxNavigator.MAIN));

    HotBoxController mainController = loader.getController();

    HotBoxNavigator.setMainController(mainController);
    HotBoxNavigator.loadPage(HotBoxNavigator.LOGIN_PAGE);

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

  public static void setCurrentUserId(String id) {
    currentUserId = id;
  }

  public static String getCurrentUserId() {
    return currentUserId;
  }

  public static void setIsAdmin(boolean admin) {
    isAdmin.set(admin);
  }

  public static BooleanProperty getIsAdmin() {
    return isAdmin;
  }

  public static void setIsLoggedIn(boolean admin) {
    isLoggedIn.set(admin);
  }

  public static BooleanProperty getIsLoggedIn() {
    return isLoggedIn;
  }
}