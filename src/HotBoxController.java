import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * HotelBox controller class for the entire layout.
 */
public class HotBoxController {

  /** Holder of a switchable view. */
  @FXML
  private StackPane hotBoxHolder;
  @FXML
  private Label headerLabel;
  @FXML
  private TextField searchBox;

  /**
   * Replaces the view displayed in the view holder with a new view.
   *
   * @param node the view node to be swapped in.
   */
  public void setPage(Node node) {
    hotBoxHolder.getChildren().setAll(node);
  }

  public void loadSearchResults() {
    HotBoxNavigator.lastSearchTerm = searchBox.getText();
    HotBoxNavigator.loadPage(HotBoxNavigator.SEARCH_RESULTS);
  }

  public void loadMovieGrid() {
    resetSearchBox();
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);
  }

  public void loadActorList() {
    resetSearchBox();
    HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_LIST);
  }

  public void loadAdminPage() {
    resetSearchBox();
    //HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
    //For now just load customer edit since it's the only admin tool we have.
    HotBoxNavigator.loadPage(HotBoxNavigator.CUSTOMER_EDIT);
  }

  private void resetSearchBox() {
    searchBox.setText("");
  }
}