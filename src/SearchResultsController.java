import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Displays search results.
 *
 * @author Tyler Bratton
 */
public class SearchResultsController implements Initializable {

  @FXML
  private FlowPane movieFlowPane;
  @FXML
  private FlowPane actorFlowPane;
  // Desired width of the image to be used for the buttons.
  private static final int TARGET_WIDTH = 150;
  // Calculates desired height based on the known aspect ratio of the images.
  private static final int TARGET_HEIGHT = (TARGET_WIDTH * 3) / 2;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> movieKeys = new HashMap<String, String>();
  // HashMap to store GENRE_NAME as a key and GENRE_ID as a value.
  private HashMap<String, String> actorKeys = new HashMap<String, String>();


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();

    movieFlowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    movieFlowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty(
        ));
    actorFlowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    actorFlowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty(
        ));

    ResultSet movieResults = HotelBox.dbConnection.searchStatement("MOVIES",
        "MOVIE_TITLE", HotBoxNavigator.lastSearchTerm, true);
    ResultSet actorResults = HotelBox.dbConnection.searchStatement("ACTORS",
        "ACTOR_NAME", HotBoxNavigator.lastSearchTerm, true);

    GeneralUtilities.createButtons(movieResults, movieKeys, movieFlowPane,
        HotBoxNavigator.MOVIE_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
        "MOVIE_TITLE", "MOVIE_IMAGE", "MOVIE_ID", HotBoxNavigator
            .SEARCH_RESULTS);
    GeneralUtilities.createButtons(actorResults, actorKeys, actorFlowPane,
        HotBoxNavigator.ACTOR_PAGE, TARGET_WIDTH, TARGET_HEIGHT,
        "ACTOR_NAME", "ACTOR_IMAGE", "ACTOR_ID", HotBoxNavigator
            .SEARCH_RESULTS);
  }
}
