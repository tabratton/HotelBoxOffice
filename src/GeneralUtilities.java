import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class GeneralUtilities {

  /**
   * Utility method to get an image from a specified URL.
   *
   * @param imageUrl The URL of the image to get.
   * @param width    The width that you want the image to be resized to.
   * @param height   The height that you want the image to be resized to.
   * @return An ImageView object that has the desired dimensions
   */
  public static ImageView getImage(String imageUrl, int width, int height) {
    try {
      URL url = new URL(imageUrl);
      BufferedImage bf = ImageIO.read(url);
      bf = Scalr.resize(bf, width, height);
      Image image = SwingFXUtils.toFXImage(bf, null);
      return new ImageView(image);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
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
   * @param title         The name of the column in the database the stores the
   *                      title of the button.
   * @param image         The name of the column in the database that stores the
   *                      image to be used.
   * @param id            The name of the column in the database that stores the
   *                      ID of the row.
   */
  public static void createButtons(ResultSet results,
                                   HashMap<String, String> keys,
                                   FlowPane localFlowPane, String pageToLoad,
                                   int targetWidth, int targetHeight,
                                   String title, String image, String id,
                                   String currentPage) {
    try {
      // Moves the cursor to the last position to determine the number of
      // entries in the result set.
      results.last();
      int numRows = results.getRow();
      results.first();

      // Creates a new button for each entry in the result set.
      for (int i = 0; i < numRows; i++) {
        final String buttonTitle = results.getString(title);
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
        currentButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Button button = (Button) event.getSource();
            String currentTitle = button.getText();
            HotBoxNavigator.lastClickedMovie = keys.get(currentTitle);
            HotBoxNavigator.lastPageLoaded = currentPage;
            // Once the movie page is made, this line will load it.
            // Ideally the initialize() method of that page will read
            // lastClickedMovie and use that string to load the correct data
            // for the clicked movie.
            HotBoxNavigator.loadPage(pageToLoad);
            System.out.println(currentTitle);
          }
        });

        // Sets the button graphic to the database image with specified
        // values for width and height.
        currentButton.setGraphic(GeneralUtilities.getImage(buttonImage,
            targetWidth, targetHeight));

        // Add the button to the flow pane.
        localFlowPane.getChildren().add(currentButton);
        // Advance the cursor to the next database entry.
        results.next();
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }
}