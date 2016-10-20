import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller for the MovieGrid.fxml.
 *
 * <p>Gets list of all movies from the database and then constructs buttons
 * that load an individual information page for each movie.
 *
 * <p>Currently displays all movies in the database on one page with a scroll
 * bar.
 *
 * @author Tyler Bratton tylerbratton96 @ gmail.com
 */
public class MovieGridController implements Initializable {
  // FlowPane object that allows us to modify the FlowPane defined in the
  // fxml dynamically.
  @FXML
  private FlowPane flowPane;
  // Desired width of the image to be used for the buttons
  private static final int TARGET_WIDTH = 150;
  // Calculates desired height based on the known aspect ratio of the images.
  private static final int TARGET_HEIGHT = (TARGET_WIDTH * 3) / 2;

  /**
   * Initializes the Move Grid page with data from the database.
   *
   * @param url    Not explicitly used in method.
   * @param bundle Not explicitly used in method.
   */
  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value
    HashMap<String, String> titleKeys = new HashMap<String, String>();
    // Initialize database connection
    DatabaseConnection con = new DatabaseConnection();
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    ResultSet rs = con.searchStatement("MOVIE_ID", "MOVIE_TITLE",
        "MOVIE_IMAGE", "MOVIES");

    try {
      // Moves the cursor to the last position to determine the number of
      // entries in the result set.
      rs.last();
      int numRows = rs.getRow();
      rs.first();

      // Creates a new button for each entry in the result set.
      for (int i = 0; i < numRows; i++) {
        final String movieTitle = rs.getString("MOVIE_TITLE");
        final String movieImage = rs.getString("MOVIE_IMAGE");
        final String movieId = rs.getString("MOVIE_ID");
        titleKeys.put(movieTitle, movieId);
        Button currentButton = new Button(movieTitle);
        // Sets the ID so that the stylesheet can be applied to the buttons.
        currentButton.setId("movieGridButton");
        // Sets the image to display above the text
        currentButton.setContentDisplay(ContentDisplay.TOP);
        // Sets size of the button, solves headaches related to text wrapping
        // of long movie titles. +50 to allow for longer titles
        currentButton.setPrefSize(TARGET_WIDTH + 50, TARGET_HEIGHT + 50);
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
            HotBoxNavigator.lastClickedMovie = titleKeys.get(currentTitle);
            // Once the movie page is made, this line will load it.
            // Ideally the initialize() method of that page will read
            // lastClickedMovie and use that string to load the correct data
            // for the clicked movie.
            HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
            System.out.println(currentTitle);
          }
        });

        // Sets the button graphic to the database image with specified
        // values for width and height.
        currentButton.setGraphic(ImageUtility.getImage(movieImage,
            TARGET_WIDTH, TARGET_HEIGHT));

        // Add the button to the flow pane.
        flowPane.getChildren().add(currentButton);
        // Advance the cursor to the next database entry.
        rs.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
