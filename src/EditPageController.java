import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class EditPageController implements Initializable {

  @FXML
  private FlowPane adminResults;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    adminResults.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    adminResults.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    String loadpage = "";
    String nameColumn = "";
    String primaryKey = "";
    ResultSet editResults = HotelBox.dbConnection.searchStatement(
        HotBoxNavigator.editTable);
    switch (HotBoxNavigator.editTable) {
      case "CUSTOMER":
        // change to customer edit page
        loadpage = HotBoxNavigator.CUSTOMER_EDIT;
        nameColumn = "CUSTOMER_NAME";
        primaryKey = "CUSTOMER_ID";
        break;
      case "MOVIES":
        // change to movie edit page
        loadpage = HotBoxNavigator.MOVIE_EDIT;
        nameColumn = "MOVIE_TITLE";
        primaryKey = "MOVIE_ID";
        break;
      case "GENRE":
        // change to genre edit page
        loadpage = HotBoxNavigator.GENRE_EDIT;
        nameColumn = "GENRE_NAME";
        primaryKey = "GENRE_ID";
        break;
      case "ACTORS":
        // change to actor edit page
        loadpage = HotBoxNavigator.ACTOR_EDIT;
        nameColumn = "ACTOR_NAME";
        primaryKey = "ACTOR_ID";
        break;
      default:
        break;
    }
    GeneralUtilities.createEditResults(editResults, adminResults, loadpage,
        nameColumn, primaryKey);
  }
}
