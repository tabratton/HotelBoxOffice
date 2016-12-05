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
  private Button createCasting;
  @FXML
  private Button editCasting;
  @FXML
  private Button editActor;
  @FXML
  private Button createActor;
  

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
    // edit customer button
    editCasting.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = "CASTING";
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
    // edit actor button
    editActor.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = "ACTORS";
           HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
           
       }
    });
    
    // edit customer button
    createCustomer.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = null;
           HotBoxNavigator.editRecord = null;
           HotBoxNavigator.loadPage(HotBoxNavigator.CUSTOMER_EDIT);
           
       }
    });
    
    // edit customer button
    createCasting.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = null;
           HotBoxNavigator.editRecord = null;
           HotBoxNavigator.loadPage(HotBoxNavigator.CASTING_EDIT);
           
       }
    });
    
    createGenre.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = null;
           HotBoxNavigator.editRecord = null;
           HotBoxNavigator.loadPage(HotBoxNavigator.GENRE_EDIT);
           
       }
    });
    
    createMovie.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = null;
           HotBoxNavigator.editRecord = null;
           HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_EDIT);
           
       }
    });
    
    createActor.setOnAction(new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent event) {
           HotBoxNavigator.editTable = null;
           HotBoxNavigator.editRecord = null;
           HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_EDIT);
           
       }
    });
  }

}
