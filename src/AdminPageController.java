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
  @FXML
  private Button castingButton;
  @FXML
  private Button editCasting;
  

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
    
    // edit customer button
    editCustomer.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = "CUSTOMER";
           HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
           
       }
    });
    // edit movie button
    editMovie.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = "MOVIES";
           HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
           
       }
    });
    // edit genre button
    editGenre.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = "GENRE";
           HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
           
       }
    });
    
    // note to self: 
    // why didn't you do add / edit actor???
    // working on it :-P
  }

}
