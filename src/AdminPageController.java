import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author chad
 */
public class AdminPageController implements Initializable {

  /**
   * Initializes the controller class.
   */
  @FXML
  private Button editCustomer;
  @FXML
  private Button createCustomer;
  @FXML
  private Button createMovie;
  @FXML
  private Button editMovie;
  @FXML
  private Button createGenre;
  @FXML
  private Button editGenre;
  @FXML
  private Button statisticsButton;
  @FXML
  private Button editRatings;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();

    // stats button loads stats page
    statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        HotBoxNavigator.loadPage(HotBoxNavigator.STATS_PAGE);
      }
    });
    
    // 
  }

}
