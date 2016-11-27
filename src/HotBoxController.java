import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * HotelBox controller class for the entire layout.
 *
 * @author Tyler Bratton
 */
public class HotBoxController implements Initializable {

  /** Holder of a switchable view. */
  @FXML
  private StackPane hotBoxHolder;
  @FXML
  private TextField searchBox;
  @FXML
  private AnchorPane menuBar;

  public void initialize(URL url, ResourceBundle resourceBundle) {
    menuBar.visibleProperty().bind(HotelBox.getIsLoggedIn());
  }

  /**
   * Replaces the view displayed in the view holder with a new view.
   *
   * @param node the view node to be swapped in.
   */
  public void setPage(Node node) {
    hotBoxHolder.getChildren().setAll(node);
  }

  /**
   * Load the search results page.
   */
  public void loadSearchResults() {
    String currentSearchText = searchBox.getText().toLowerCase().trim();
    if (currentSearchText.length() == 0 || currentSearchText.matches("\\s+")) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Empty Search");
      alert.setHeaderText(null);
      alert.setContentText("You must enter something besides spaces in your"
          + " search.");
      alert.showAndWait();
      searchBox.setText("");
    } else {
      HotBoxNavigator.lastSearchTerm = currentSearchText;
      addSearchTerm(currentSearchText);
      HotBoxNavigator.loadPage(HotBoxNavigator.SEARCH_RESULTS);
    }
  }

  /**
   * Load the movie grid page.
   */
  public void loadMovieGrid() {
    resetSearchBox();
    MovieGridController.loadedByNavigationBarButton = true;
    MovieGridController.newReleasesLastLoaded = false;
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);
  }

  /**
   * Load the actor list page.
   */
  public void loadActorList() {
    resetSearchBox();
    HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_LIST);
  }

  /**
   * Load the admin page.
   */
  public void loadAdminPage() {
    resetSearchBox();
    //HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
    //For now just load customer edit since it's the only admin tool we have.
    HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
  }

  public void logoutPressed() {
    HotelBox.setIsLoggedIn(false);
    HotelBox.setIsAdmin(false);
    HotelBox.setCurrentUserId("-1");
    HotBoxNavigator.loadPage(HotBoxNavigator.LOGIN_PAGE);
  }

  private void resetSearchBox() {
    searchBox.setText("");
  }

  private void addSearchTerm(String term) {
    String command = String.format("INSERT INTO SEARCH_TERMS (TERM)"
        + " VALUES ('%s') ON DUPLICATE KEY UPDATE FREQUENCY ="
        + " FREQUENCY + 1", term);
    HotelBox.dbConnection.updateStatement(command);
  }
}