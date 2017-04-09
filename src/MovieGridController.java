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
 * @author Tyler Bratton
 */
public class MovieGridController implements Initializable {
  // FlowPane object that allows us to modify the FlowPane defined in the
  // fxml dynamically.
  @FXML
  private FlowPane flowPane;
  @FXML
  private ChoiceBox<String> choiceBox;
  @FXML
  private Button recentMovies;
  @FXML
  private Button popularMovies;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<>();
  // HashMap to store GENRE_NAME as a key and GENRE_ID as a value.
  private HashMap<String, String> genreKeys = new HashMap<>();
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
    String search = "SELECT * FROM genre ORDER BY genre_name";
    ResultSet genres = HotelBox.dbConnection.searchStatement(search);
    // Create the choice box
    createChoiceBox(genres);

    //Get data for recent movies.
    search = "SELECT movie_id, movie_title, movie_image FROM movies ORDER BY"
        + " movies.movie_release_date DESC LIMIT 10";
    ResultSet newReleases = HotelBox.dbConnection.searchStatement(search);
    // Create button
    createNewReleasesButton(newReleases);

    //Get data for recent movies.
    search = "SELECT movie_id, movie_title, movie_image FROM movies ORDER BY"
        + " movies.times_viewed DESC LIMIT 10";
    ResultSet mostPopular = HotelBox.dbConnection.searchStatement(search);

    createMostPopularButton(mostPopular);

    // If the new releases view was what the user was on before clicking a
    // movie button, return them back to it when they click go back.
    if (newReleasesLastLoaded) {
      GeneralUtilities.createButtons(newReleases, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.MEDIUM),"MOVIES",
          HotBoxNavigator.MOVIE_GRID);
    } else if (mostPopularLastLoaded) {
      GeneralUtilities.createButtons(mostPopular, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.MEDIUM),"MOVIES",
          HotBoxNavigator.MOVIE_GRID);
    } else {
      // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
      // table
      ResultSet rs;
      // If the genre isn't "All" load the correct one
      if (currentSelectedGenreId != 0) {
        search = String.format("SELECT movie_id, movie_title, movie_image"
                + " FROM movies WHERE genre_id=%d", currentSelectedGenreId);
        rs = HotelBox.dbConnection.searchStatement(search);
      } else {
        search = "SELECT movie_id, movie_title, movie_image FROM movies";
        rs = HotelBox.dbConnection.searchStatement(search);
      }
      GeneralUtilities.createButtons(rs, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.MEDIUM), "MOVIES",
          HotBoxNavigator.MOVIE_GRID);
    }
  }

  private void createChoiceBox(ResultSet genres) {
    genreKeys.put("All", "0");
    choiceBox.setTooltip(new Tooltip("Filter by genre"));
    // Add default All selection
    choiceBox.getItems().add("All");
    // Stores the genre names in the order they appear in the choice box
    ArrayList<String> genreList = new ArrayList<>();
    genreList.add("All");
    try {
      genres.last();
      int numRows = genres.getRow();
      genres.first();

      // Add all genres to the choice box.
      for (int i = 0; i < numRows; i++) {
        String genreName = genres.getString("genre_name");
        String genreId = genres.getString("genre_id");
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
        (observable, oldValue, newValue) -> {
          // Get current selection
          int currentSelection = newValue.intValue();
          // Find the genre name in the array list.
          String currentGenre = genreList.get(currentSelection);
          // Use genre title as key to find original genre ID.
          String currentId = genreKeys.get(currentGenre);
          // Saves the id of the genre that was just selected.
          currentSelectedGenreId = Integer.parseInt(currentId);
          // Saves the name of the genre that was just selected.
          currentSelectedGenreName = currentGenre;

          ResultSet newSet;
          // If the genre wasn't "All" load the correct one.
          if (currentSelection != 0) {
            String search = String.format("SELECT movie_id, movie_title,"
                + " movie_image FROM movies WHERE genre_id=%s", currentId);
            newSet = HotelBox.dbConnection.searchStatement(search);
          } else {
            String search = "SELECT movie_id, movie_title, movie_image FROM"
                + " movies";
            newSet = HotelBox.dbConnection.searchStatement(search);
          }
          // Clear the display of movies
          removeAllButtons();
          // Create new grid with current selection of genre
          GeneralUtilities.createButtons(newSet, titleKeys, flowPane,
              HotBoxNavigator.MOVIE_PAGE,
              new Dimensions(Dimensions.DimensionTypes.MEDIUM), "MOVIES",
              HotBoxNavigator.MOVIE_GRID);
          newReleasesLastLoaded = false;
          mostPopularLastLoaded = false;
        });
  }

  private void removeAllButtons() {
    // Remove all buttons from pane so they can be redrawn.
    flowPane.getChildren().clear();
  }

  private void createNewReleasesButton(ResultSet newReleases) {
    recentMovies.setTooltip(new Tooltip("Filter by new releases"));
    recentMovies.setOnAction(event -> {
      changeMovieDisplay(newReleases);
      newReleasesLastLoaded = true;
    });
  }

  private void createMostPopularButton(ResultSet mostPopular) {
    popularMovies.setTooltip(new Tooltip("Filter by most popular"));
    popularMovies.setOnAction(event -> {
      changeMovieDisplay(mostPopular);
      mostPopularLastLoaded = true;
    });
  }

  private void changeMovieDisplay(ResultSet rs) {
    // Reset choice box back to all
    choiceBox.getSelectionModel().select("All");
    // Clear the display of movies
    removeAllButtons();
    // Create new grid with current selection of genre
    GeneralUtilities.createButtons(rs, titleKeys, flowPane,
        HotBoxNavigator.MOVIE_PAGE,
        new Dimensions(Dimensions.DimensionTypes.MEDIUM), "MOVIES",
        HotBoxNavigator.MOVIE_GRID);
  }
}
