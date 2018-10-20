package hotelbox;

import controllers.HotBoxController;
import database.DatabaseConnection;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.GeneralUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Main class of the program, starts the GUI.
 *
 * @author Tyler Bratton
 */
public class HotelBox extends Application {

  // Initialize database connection
  public static DatabaseConnection dbConnection;
  // Allows controllers.MovieGridController to access the current window size.
  public static Stage testStage;
  public static Map<String, BufferedImage> movieImages = new HashMap<>();
  public static Map<String, BufferedImage> actorImages = new HashMap<>();
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
        "/assets/images/icon.png")));
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
    var loader = new FXMLLoader();

//    InputStream is = new FileInputStream(HotBoxNavigator.MAIN);
    Pane mainPane = loader.load(getClass().getResourceAsStream(HotBoxNavigator.MAIN));

    HotBoxController mainController = loader.getController();

    HotBoxNavigator.setMainController(mainController);
    HotBoxNavigator.loadPage(HotBoxNavigator.LOGIN_PAGE);

    return mainPane;
  }

  /**
   * Creates the main application scene.
   *
   * @param mainPane the main application layout.
   * @return the created scene.
   */
  private Scene createScene(Pane mainPane) {
    var scene = new Scene(mainPane);
    // Attaches the .css to the scene, all changes and rules in it should
    // propagate throughout the program.
    scene.getStylesheets().add("assets/HotBox.css");

    return scene;
  }

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    var dbUrl = dotenv.get("DB_URL");
    var dbUser = dotenv.get("DB_USER");
    var dbPass = dotenv.get("DB_PASS");
    dbConnection = new DatabaseConnection(dbUrl, dbUser, dbPass);
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