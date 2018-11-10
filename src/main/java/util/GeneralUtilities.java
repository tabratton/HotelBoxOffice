package util;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import models.Actor;
import models.Customer;
import models.Movie;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.kosprov.jargon2.api.Jargon2.*;

/**
 * Provides various general utility methods, such as resizing images, create
 * image buttons, and downloading images.
 *
 * @author Tyler Bratton
 * @author Chad Goodwin
 */
public class GeneralUtilities {

  private static String OS = System.getProperty("os.name").toLowerCase();
  private static String directoryPath = isWindows() ? String.format("%s%sHotelBoxOffice%s",
      System.getenv("APPDATA"), File.separator, File.separator)
      : String.format("%s%s.HotelBoxOffice", System.getProperty("user.home"), File.separator);

  /**
   * Utility method to get an image from a specified URL.
   *
   * @param imageUrl   The URL of the image to get.
   * @param dimensions A util.Dimensions object with the desired dimensions of the
   *                   image.
   * @return An ImageView object that has the desired dimensions
   */
  public static ImageView getImage(String imageUrl, Dimensions dimensions, String table, String id) {
    var fullPath = getImagePath(imageUrl, table, dimensions);
    var imageFile = new File(fullPath);
    BufferedImage bf;
    if (!imageFile.exists()) {
      downloadImage(fullPath, imageUrl, table, id, dimensions);
    }
    if (table.equals("movies")) {
      bf = HotelBox.movieImages.get(dimensions + id);
    } else {
      bf = HotelBox.actorImages.get(dimensions + id);
    }

    bf = Scalr.resize(bf, dimensions.getWidth(), dimensions.getHeight());
    var image = SwingFXUtils.toFXImage(bf, null);
    return new ImageView(image);
  }

  /**
   * Generates buttons in the style of the movie grid and adds them to a
   * flow pane.
   *
   * @param results       The result set to be used to get data.
   * @param keys          The HashMap used to store key values.
   * @param localFlowPane The flow pane to add the buttons to.
   * @param pageToLoad    The page to load when a button is clicked.
   * @param dimensions    A util.Dimensions object with the desired dimensions of
   *                      the image.
   * @param table         The table that contains the data you are using.
   */
  public static void createButton(String title,
                                  String image,
                                  String id,
                                  Map<String, String> keys,
                                  FlowPane localFlowPane,
                                  String pageToLoad,
                                  Dimensions dimensions,
                                  String table,
                                  String currentPage) {

    final var isMovies = table.equals("movies");

    // Create a new button.
    keys.put(title, id);
    var currentButton = new Button(title);
    // Sets the ID so that the stylesheet can be applied to the buttons.
    currentButton.setId("gridButton");
    // Sets the image to display above the text
    currentButton.setContentDisplay(ContentDisplay.TOP);
    // Sets size of the button, solves headaches related to text wrapping
    // of long movie titles. +50 to allow for longer titles
    currentButton.setPrefSize(dimensions.getWidth() + 50, dimensions.getHeight() + 50);
    // No Ellipses for us.
    currentButton.setWrapText(true);
    // Aligns the contents of the button to be centered on the top.
    currentButton.setAlignment(Pos.TOP_CENTER);
    // Text is centered when it wraps.
    currentButton.setTextAlignment(TextAlignment.CENTER);
    // Defines an anonymous event handler for the button.
    currentButton.setOnAction(event -> {
      var button = (Button) event.getSource();
      var currentTitle = button.getText();
      if (currentTitle.contains("\n")) {
        currentTitle = currentTitle.substring(0, currentTitle.indexOf("\n"));
      }
      var currentId = keys.get(currentTitle);
      HotelBox.dbConnection.updateTimesViewed(table, currentId);
      if (isMovies) {
        HotBoxNavigator.lastClickedMovieStack.push(currentId);
      } else {
        HotBoxNavigator.lastClickedActorStack.push(currentId);
      }
      HotBoxNavigator.lastLoadedPageStack.push(currentPage);
      HotBoxNavigator.loadPage(pageToLoad);
      System.out.println(currentTitle);
    });

    // Sets the button graphic to the database image with specified
    // values for width and height.
    currentButton.setGraphic(GeneralUtilities.getImage(image, dimensions, table, id));

    // Add the button to the flow pane.
    localFlowPane.getChildren().add(currentButton);
  }

  /**
   * Calls downloadEntireSet on with the two ResultSets with Movie and Actor
   * images in them.
   */
  public static void initializeLocalCache() {
    var movieImageSet = HotelBox.dbConnection.getMovies();
    var actorImageSet = HotelBox.dbConnection.getActors();
    downloadAllMovieImages(movieImageSet);
    downloadAllActorImages(actorImageSet);
  }

  private static void downloadAllMovieImages(List<Movie> movies) {
    var small = new Dimensions(Dimensions.DimensionTypes.SMALL);
    var medium = new Dimensions(Dimensions.DimensionTypes.MEDIUM);
    var large = new Dimensions(Dimensions.DimensionTypes.LARGE);
    Dimensions[] dimensions = {small, medium, large};

    movies.forEach(movie -> {
      for (Dimensions dimension : dimensions) {
        var image = movie.getImage();
        var path = getImagePath(image, "movies", dimension);
        downloadImage(path, image, "movies", movie.getId(), dimension);
      }
    });
  }

  private static void downloadAllActorImages(List<Actor> actors) {
    var small = new Dimensions(Dimensions.DimensionTypes.SMALL);
    var medium = new Dimensions(Dimensions.DimensionTypes.MEDIUM);
    var large = new Dimensions(Dimensions.DimensionTypes.LARGE);
    Dimensions[] dimensions = {small, medium, large};

    actors.forEach(actor -> {
      for (Dimensions dimension : dimensions) {
        var image = actor.getImage();
        var path = getImagePath(image, "actors", dimension);
        downloadImage(path, image, "actors", actor.getId(), dimension);
      }
    });
  }

  private static void downloadImage(String fullPath, String imageLink, String table, String id,
                                    Dimensions dimensions) {
    var image = new File(fullPath);
    var parentDirectory = image.getParentFile();

    try {
      if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
        throw new IllegalStateException("Couldn't create dir: " + parentDirectory);
      }
    } catch (IllegalStateException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      if (!image.exists()) {
        var url = new URL(imageLink);
        var bf = ImageIO.read(url);
        bf = Scalr.resize(bf, dimensions.getWidth(), dimensions.getHeight());
        addToMap(table, id, bf, dimensions);
        ImageIO.write(bf, "png", image);
        System.out.println(fullPath + " created!");
      } else {
        var bf = ImageIO.read(image);
        addToMap(table, id, bf, dimensions);
      }
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static void addToMap(String table, String id, BufferedImage bf, Dimensions dimensions) {
    if (table.equals("movies")) {
      HotelBox.movieImages.put(dimensions + id, bf);
    } else {
      HotelBox.actorImages.put(dimensions + id, bf);
    }
  }

  private static String getImagePath(String image, String table, Dimensions dimensions) {
    return String.format("%s%s_%s_%s.png", directoryPath, dimensions, table, image.substring(20, 27));
  }

  private static boolean isWindows() {
    return OS.contains("win");
  }

  /**
   * Creates the list of edit results for the specified result set.
   *
   * @param results       The result set to use.
   * @param localFlowPane The flow pane tos tore the results in.
   * @param pageToLoad    The page that will be loaded upon clicking edit.
   * @param nameColumn    The name of the column you are finding data for.
   * @param primaryKey    The primary key of the result set.
   */
  public static void createEditResults(String labelText, String id, FlowPane localFlowPane, String pageToLoad,
                                       String primaryKey) {

    var nameLabel = new Label();
    nameLabel.setText(labelText);
    nameLabel.setMinWidth(400);
    var editButton = new Button("Edit");
    var deleteButton = new Button("Delete");
    editButton.setOnAction(event -> {
      HotBoxNavigator.editRecord = id;
      HotBoxNavigator.loadPage(pageToLoad);
    });
    deleteButton.setOnAction(event -> {
      var delete = String.format("DELETE FROM %s WHERE %s='%s'", HotBoxNavigator.editTable, primaryKey, id);
      HotelBox.dbConnection.deleteStatement(delete);
      HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
    });
    localFlowPane.getChildren().add(nameLabel);
    localFlowPane.getChildren().add(editButton);
    localFlowPane.getChildren().add(deleteButton);
  }

  /**
   * Show a message about database being updated successfully.
   */
  public static void showSuccessMessage() {
    var alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success!");
    alert.setHeaderText(null);
    alert.setContentText("Database updated successfully!");
    alert.showAndWait();
  }

  public static String encodePassword(String pass) {
    var dotenv = Dotenv.load();
    var secret = toByteArray(dotenv.get("HASH_SECRET").getBytes()).clearSource();


    try (var toHash = toByteArray(pass.getBytes()).clearSource()) {
      Hasher hasher = jargon2Hasher()
          .secret(secret)
          .type(Type.ARGON2d) // Data-dependent hashing
          .memoryCost(65536)  // 64MB memory cost
          .timeCost(3)        // 3 passes through memory
          .parallelism(4)     // use 4 lanes and 4 threads
          .saltLength(16)     // 16 random bytes salt
          .hashLength(16);    // 16 bytes output hash

      // Set the password and calculate the encoded hash
      return hasher.password(toHash).encodedHash();
    } catch (Exception ex) {
      throw new RuntimeException("Encoding error");
    }
  }

  public static boolean verifyPassword(String user, String pass) {
    var dotenv = Dotenv.load();
    try (var secret = toByteArray(dotenv.get("HASH_SECRET").getBytes()).clearSource()) {
      Verifier verifier = jargon2Verifier().secret(secret).threads(Integer.parseInt(dotenv.get("HASH_THREADS")));
      var dbPass = HotelBox.dbConnection.getCustomerByName(user).orElse(new Customer()).getPwd();
      return verifier.hash(dbPass).password(pass.getBytes()).verifyEncoded();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return false;
  }
}
