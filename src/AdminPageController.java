import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
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
  private Button editActor;
  @FXML
  private Button createActor;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();

    // stats button loads stats page
    statisticsButton.setOnAction(event -> HotBoxNavigator.loadPage(HotBoxNavigator.STATS_PAGE));

    // edit customer button
    editCustomer.setOnAction(event -> {
      HotBoxNavigator.editTable = "CUSTOMER";
      HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
    });

    // edit movie button
    editMovie.setOnAction(event -> {
      HotBoxNavigator.editTable = "MOVIES";
      HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
    });

    // edit genre button
    editGenre.setOnAction(event -> {
      HotBoxNavigator.editTable = "GENRE";
      HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
    });

    // edit actor button
    editActor.setOnAction(event -> {
      HotBoxNavigator.editTable = "ACTORS";
      HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
    });

    // edit customer button
    createCustomer.setOnAction(event -> {
      HotBoxNavigator.editTable = null;
      HotBoxNavigator.editRecord = null;
      HotBoxNavigator.loadPage(HotBoxNavigator.CUSTOMER_EDIT);
    });

    // edit customer button
    createCasting.setOnAction(event -> {
      HotBoxNavigator.editTable = null;
      HotBoxNavigator.editRecord = null;
      HotBoxNavigator.loadPage(HotBoxNavigator.CASTING_EDIT);
    });

    createGenre.setOnAction(event -> {
      HotBoxNavigator.editTable = null;
      HotBoxNavigator.editRecord = null;
      HotBoxNavigator.loadPage(HotBoxNavigator.GENRE_EDIT);
    });

    createMovie.setOnAction(event -> {
      HotBoxNavigator.editTable = null;
      HotBoxNavigator.editRecord = null;
      HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_EDIT);
    });

    createActor.setOnAction(event -> {
      HotBoxNavigator.editTable = null;
      HotBoxNavigator.editRecord = null;
      HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_EDIT);
    });
  }

}
