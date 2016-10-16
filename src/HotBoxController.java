import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

  /**
   * Replaces the view displayed in the view holder with a new view.
   *
   * @param node the view node to be swapped in.
   */
  public void setPage(Node node) {
    hotBoxHolder.getChildren().setAll(node);
  }

}