import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Provides various general utility methods, such as resizing images, create
 * image buttons, and downloading images.
 *
 * @author Tyler Bratton
 * @author Chad Goodwin
 */
public class GeneralUtilities {

  private static String OS = System.getProperty("os.name").toLowerCase();
  private static String directoryPath = isWindows() ? System.getenv("APPDATA")
      + File.separator + "HotelBoxOffice" + File.separator : System
      .getProperty("user.home") + File.separator + ".HotelBoxOffice"
      + File.separator;

  // TODO: Standardize image sizes. Possibly cache a large and regular size.
  // TODO: Resize on download

  /**
   * Utility method to get an image from a specified URL.
   *
   * @param imageUrl The URL of the image to get.
   * @param width    The width that you want the image to be resized to.
   * @param height   The height that you want the image to be resized to.
   * @return An ImageView object that has the desired dimensions
   */
  public static ImageView getImage(String imageUrl, int width, int height,
                                   String table, String id) {
    String fullPath = getImagePath(imageUrl, table);
    File imageFile = new File(fullPath);
    BufferedImage bf;
    if (!imageFile.exists()) {
      downloadImage(fullPath, imageUrl, table, id);
    }
    if (table.equals("MOVIES")) {
      bf = HotelBox.movieImages.get(id);
    } else {
      bf = HotelBox.actorImages.get(id);
    }
    bf = Scalr.resize(bf, width, height);
    Image image = SwingFXUtils.toFXImage(bf, null);
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
   * @param targetWidth   The width of the image on the button.
   * @param targetHeight  The height of the image on the button.
   * @param table         The table that contains the data you are using.
   */
  public static void createButtons(ResultSet results,
                                   HashMap<String, String> keys,
                                   FlowPane localFlowPane, String pageToLoad,
                                   int targetWidth, int targetHeight,
                                   String table, String currentPage) {

    final boolean isMovies = table.equals("MOVIES");
    final String nameColumn = isMovies ? "movie_title" : "actor_name";
    final String image = isMovies ? "movie_image" : "actor_image";
    final String id = isMovies ? "movie_id" : "actor_id";

    try {
      // Moves the cursor to the last position to determine the number of
      // entries in the result set.
      results.last();
      int numRows = results.getRow();
      results.first();

      // Creates a new button for each entry in the result set.
      for (int i = 0; i < numRows; i++) {
        final String buttonTitle = results.getString(nameColumn);
        final String buttonImage = results.getString(image);
        final String buttonId = results.getString(id);
        keys.put(buttonTitle, buttonId);
        Button currentButton = new Button(buttonTitle);
        // Sets the ID so that the stylesheet can be applied to the buttons.
        currentButton.setId("gridButton");
        // Sets the image to display above the text
        currentButton.setContentDisplay(ContentDisplay.TOP);
        // Sets size of the button, solves headaches related to text wrapping
        // of long movie titles. +50 to allow for longer titles
        currentButton.setPrefSize(targetWidth + 50, targetHeight + 50);
        // No Ellipses for us.
        currentButton.setWrapText(true);
        // Aligns the contents of the button to be centered on the top.
        currentButton.setAlignment(Pos.TOP_CENTER);
        // Text is centered when it wraps.
        currentButton.setTextAlignment(TextAlignment.CENTER);
        // Defines an anonymous event handler for the button.
        currentButton.setOnAction(event -> {
          Button button = (Button) event.getSource();
          String currentTitle = button.getText();
          if (currentTitle.contains("\n")) {
            int cutOff = currentTitle.indexOf("\n");
            currentTitle = currentTitle.substring(0, cutOff);
          }
          String currentId = keys.get(currentTitle);
          String columBeginning = table.substring(0, table.length() - 1);
          String update = String.format("UPDATE %s SET %s.times_viewed=%s"
                  + ".times_viewed+1 WHERE %s.%s_id=%s", table, table, table,
              table, columBeginning, currentId);
          HotelBox.dbConnection.updateStatement(update);
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
        currentButton.setGraphic(GeneralUtilities.getImage(buttonImage,
            targetWidth, targetHeight, table, buttonId));

        // Add the button to the flow pane.
        localFlowPane.getChildren().add(currentButton);
        // Advance the cursor to the next database entry.
        results.next();
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }

  /**
   * Calls downloadEntireSet on with the two ResultSets with Movie and Actor
   * images in them.
   */
  public static void initializeLocalCache() {
    String search = "SELECT movie_id, movie_image FROM movies";
    ResultSet movieImageSet = HotelBox.dbConnection.searchStatement(search);
    search = "SELECT actor_id, actor_image FROM actors";
    ResultSet actorImageSet = HotelBox.dbConnection.searchStatement(search);
    downloadEntireSet(movieImageSet, "movie");
    downloadEntireSet(actorImageSet, "actor");
  }

  private static void downloadEntireSet(ResultSet rs, String table) {
    try {
      rs.last();
      int rows = rs.getRow();
      rs.first();

      for (int i = 0; i < rows; i++) {
        String image = rs.getString(table + "_image");
        String path = getImagePath(image, table + "S");
        downloadImage(path, image, table + "s", rs.getString(table + "_id"));
        rs.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static void downloadImage(String fullPath, String imageLink,
                                    String table, String id) {
    File image = new File(fullPath);
    File parentDirectory = image.getParentFile();

    try {
      if (!parentDirectory.exists() && !parentDirectory.mkdirs()) {
        throw new IllegalStateException("Couldn't create dir: "
            + parentDirectory);
      }
    } catch (IllegalStateException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      if (!image.exists()) {
        URL url = new URL(imageLink);
        BufferedImage bf = ImageIO.read(url);
        addToMap(table, id, bf);
        ImageIO.write(bf, "png", image);
        System.out.println(fullPath + " created!");
      } else {
        BufferedImage bf = ImageIO.read(image);
        addToMap(table, id, bf);
      }
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static void addToMap(String table, String id, BufferedImage bf) {
    if (table.equals("movies")) {
      HotelBox.movieImages.put(id, bf);
    } else {
      HotelBox.actorImages.put(id, bf);
    }
  }

  private static String getImagePath(String image, String table) {
    return String.format("%s%s_%s.png", directoryPath, table,
        image.substring(20, 27));
  }

  private static boolean isWindows() {
    return (OS.contains("win"));
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
  public static void createEditResults(ResultSet results,
                                       FlowPane localFlowPane,
                                       String pageToLoad,
                                       String nameColumn, String primaryKey) {
    try {
      while (results.next()) {
        Label nameLabel = new Label();
        nameLabel.setText(results.getString(nameColumn));
        nameLabel.setMinWidth(400);
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        String recordToEdit = results.getString(primaryKey);
        editButton.setOnAction(event -> {
          HotBoxNavigator.editRecord = recordToEdit;
          HotBoxNavigator.loadPage(pageToLoad);
        });
        deleteButton.setOnAction(event -> {
          String delete = String.format("DELETE FROM %s WHERE %s=%s",
              HotBoxNavigator.editTable, primaryKey, recordToEdit);
          HotelBox.dbConnection.deleteStatement(delete);
          HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
        });
        localFlowPane.getChildren().add(nameLabel);
        localFlowPane.getChildren().add(editButton);
        localFlowPane.getChildren().add(deleteButton);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Show a message about database being updated successfully.
   */
  public static void showSuccessMessage() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success!");
    alert.setHeaderText(null);
    alert.setContentText("Database updated successfully!");
    alert.showAndWait();
  }
}
