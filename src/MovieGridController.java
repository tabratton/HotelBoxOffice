import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
  @FXML
  private ChoiceBox choiceBox;
  // Desired width of the image to be used for the buttons.
  private static final int TARGET_WIDTH = 150;
  // Calculates desired height based on the known aspect ratio of the images.
  private static final int TARGET_HEIGHT = (TARGET_WIDTH * 3) / 2;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<String, String>();
  // HashMap to store GENRE_NAME as a key and GENRE_ID as a value.
  private HashMap<String, String> genreKeys = new HashMap<String, String>();
  // Stores the current/last selected genre in the movie grid page.
  private static String currentSelectedGenreName = "All";
  // Stores the id of the current/last selected genre.
  private static int currentSelectedGenreId = 0;

  /**
   * Initializes the Move Grid page with data from the database.
   *
   * @param url    Not explicitly used in method.
   * @param bundle Not explicitly used in method.
   */
  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    // Magic to set the preferred width and height to the current window size.
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    // Gets the data for the GENRES table.
    ResultSet databaseGenres = HotelBox.dbConnection.searchStatement("GENRE "
        + "ORDER BY GENRE_NAME");
    // Create the choice box
    createChoiceBox(databaseGenres);
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    ResultSet rs;

    // If the genre isn't "All" load the correct one
    if (currentSelectedGenreId != 0) {
      rs = HotelBox.dbConnection.searchStatement("MOVIES",
          "GENRE_ID", "" + currentSelectedGenreId);
    } else {
      rs = HotelBox.dbConnection.searchStatement("MOVIE_ID",
          "MOVIE_TITLE", "MOVIE_IMAGE", "MOVIES");
    }
    createButtons(rs);
  }

  private void createChoiceBox(ResultSet genres) {
    genreKeys.put("All", "0");
    choiceBox.setTooltip(new Tooltip("Filter by genre"));
    // Add default All selection
    choiceBox.getItems().add("All");
    // Stores the genre names in the order they appear in the choice box
    ArrayList<String> genreList = new ArrayList<String>();
    genreList.add("All");
    try {
      genres.last();
      int numRows = genres.getRow();
      genres.first();

      // Add all genres to the choice box.
      for (int i = 0; i < numRows; i++) {
        String genreName = genres.getString("GENRE_NAME");
        String genreId = genres.getString("GENRE_ID");
        genreList.add(genreName);
        // Associate genre name and genre id
        genreKeys.put(genreName, genreId);
        choiceBox.getItems().add(genreName);
        genres.next();
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    // Make the first selection (All) the default.
    choiceBox.getSelectionModel().select(genreList.indexOf(
        currentSelectedGenreName));
    choiceBox.getSelectionModel().selectedIndexProperty().addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue observable,
                              Number oldValue, Number newValue) {
            // Get current selection
            int currentSelection = newValue.intValue();
            // Find the genre name in the array list.
            String currentGenre = genreList.get(currentSelection);
            // Use genre title as key to find original genre ID.
            String currentId = genreKeys.get(currentGenre);
            // Saves the id of the genre that was just selected.
            currentSelectedGenreId = Integer.parseInt(
                currentId);
            // Saves the name of the genre that was just selected.
            currentSelectedGenreName = currentGenre;

            ResultSet newSet;
            // If the genre wasn't "All" load the correct one.
            if (currentSelection != 0) {
              newSet = HotelBox.dbConnection.searchStatement(
                  "MOVIES", "GENRE_ID", currentId);
            } else {
              newSet = HotelBox.dbConnection.searchStatement("MOVIE_ID",
                  "MOVIE_TITLE", "MOVIE_IMAGE", "MOVIES");
            }
            // Clear the display of movies
            removeAllButtons();
            // Create new grid with current selection of genre
            createButtons(newSet);
          }
        });
  }

  private void createButtons(ResultSet rs) {
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
      System.err.println(ex.getMessage());
    }
  }

  private void removeAllButtons() {
    // Remove all buttons from pane so they can be redrawn.
    flowPane.getChildren().clear();
  }
}
