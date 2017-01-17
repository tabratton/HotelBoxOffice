import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;

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
 * @author Tyler Bratton
 */
public class MovieGridController implements Initializable {
  // FlowPane object that allows us to modify the FlowPane defined in the
  // fxml dynamically.
  @FXML
  private FlowPane flowPane;
  @FXML
  private ChoiceBox choiceBox;
  @FXML
  private Button recentMovies;
  @FXML
  private Button popularMovies;
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
  // Determines if the page was loaded by the navigation bar.
  public static boolean loadedByNavigationBarButton;
  public static boolean newReleasesLastLoaded;
  public static boolean mostPopularLastLoaded;

  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    HotBoxNavigator.clearStacks();

    // Magic to set the preferred width and height to the current window size.
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());

    if (loadedByNavigationBarButton) {
      currentSelectedGenreName = "All";
      currentSelectedGenreId = 0;
      loadedByNavigationBarButton = false;
    }

    // Gets the data for the GENRES table.
    ResultSet databaseGenres = HotelBox.dbConnection.searchStatement("GENRE "
        + "ORDER BY GENRE_NAME");
    // Create the choice box
    createChoiceBox(databaseGenres);

    //Get data for recent movies.
    ResultSet newReleases = HotelBox.dbConnection.searchStatement(
        "SELECT * FROM MOVIES ORDER BY MOVIES.MOVIE_RELEASE_DATE"
            + " DESC LIMIT 10", true);
    // Create button
    createNewReleasesButton(newReleases);

    //Get data for recent movies.
    ResultSet mostPopular = HotelBox.dbConnection.searchStatement(
        "SELECT * FROM MOVIES ORDER BY MOVIES.TIMES_VIEWED DESC LIMIT 10",
        true);

    createMostPopularButton(mostPopular);

    // If the new releases view was what the user was on before clicking a
    // movie button, return them back to it when they click go back.
    if (newReleasesLastLoaded) {
      GeneralUtilities.createButtons(newReleases, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
          "MOVIES", HotBoxNavigator.MOVIE_GRID);
    } else if (mostPopularLastLoaded) {
      GeneralUtilities.createButtons(mostPopular, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
          "MOVIES", HotBoxNavigator.MOVIE_GRID);
    } else {
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
      GeneralUtilities.createButtons(rs, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT, "MOVIES",
          HotBoxNavigator.MOVIE_GRID);
    }
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
            GeneralUtilities.createButtons(newSet, titleKeys, flowPane,
                HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
                "MOVIES", HotBoxNavigator.MOVIE_GRID);
            newReleasesLastLoaded = false;
            mostPopularLastLoaded = false;
          }
        });
  }

  private void removeAllButtons() {
    // Remove all buttons from pane so they can be redrawn.
    flowPane.getChildren().clear();
  }

  private void createNewReleasesButton(ResultSet newReleases) {
    recentMovies.setTooltip(new Tooltip("Filter by new releases"));
    recentMovies.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // Clear the display of movies
        removeAllButtons();
        // Create new grid with current selection of genre
        GeneralUtilities.createButtons(newReleases, titleKeys, flowPane,
            HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
            "MOVIES", HotBoxNavigator.MOVIE_GRID);
        newReleasesLastLoaded = true;
      }
    });
  }

  private void createMostPopularButton(ResultSet mostPopular) {
    popularMovies.setTooltip(new Tooltip("Filter by most popular"));
    popularMovies.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // Clear the display of movies
        removeAllButtons();
        // Create new grid with current selection of genre
        GeneralUtilities.createButtons(mostPopular, titleKeys, flowPane,
            HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
            "MOVIES", HotBoxNavigator.MOVIE_GRID);
        mostPopularLastLoaded = true;
      }
    });
  }
}
