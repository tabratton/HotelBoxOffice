import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the MovieGrid.fxml
 * <p>Gets list of all movies from the database and then constructs buttons
 * that load an individual information page for each movie.
 * <p>Currently displays all movies in the database on one page with a scroll
 * bar.
 */
public class MovieGridController implements Initializable {
  // FlowPane object that allows us to modify the FlowPane defined in the
  // fxml dynamically.
  @FXML
  private FlowPane flowPane;
  // ScrollPane object that allows us to set certain things. Not needed with
  // .css file
//  @FXML
//  private ScrollPane scrollPane;
  public static String lastClickedMovie;

  public void initialize(URL url, ResourceBundle bundle) {
    // ArrayList to hold all the buttons. More than likely not necessary,
    // but it's good to be safe.
    ArrayList<Button> buttons = new ArrayList<>();
    // Initialize database connection
    DBConnection con = new DBConnection();
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
        // Creates a button with text that matches the movie title it is for
        buttons.add(new Button(String.format("%s", rs.getString("MOVIE_TITLE")
        )));
        // Creates a local reference to the current button so we can edit it
        // easier.
        Button currentButton = buttons.get(i);
        // Not needed with .css file
//        currentButton.setStyle("-fx-background-color: transparent;");
        // Sets the ID so that the stylesheet can be applied to the buttons.
        currentButton.setId("moviegridbutton");
        // Sets the image to display above the text
        currentButton.setContentDisplay(ContentDisplay.TOP);
        // Sets size of the button, solves headaches related to text wrapping
        // of long movie titles.
        currentButton.setPrefSize(150, 200);
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
            System.out.println(button.getText());
            lastClickedMovie = button.getText();
            // Once the movie page is made, this line will load it.
            // Ideally the initialize() method of that page will read
            // lastClickedMovie and use that string to load the correct data
            // for the clicked movie.
            //HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
          }
        });

        // Creates a new image from the imgur url in the database
        ImageView imageView = new ImageView(rs.getString("MOVIE_IMAGE"));
        // Sets the button graphic to the database image.
        currentButton.setGraphic(imageView);

        // Add the button to the flow pane.
        flowPane.getChildren().add(currentButton);
        // Advance the cursor to the next database entry.
        rs.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    // Not needed with .css file
//    scrollPane.setStyle("-fx-background-color:transparent;");
  }
}
