import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Vilma Un Jan
 */
public class ActorListController implements Initializable {

  @FXML
  private FlowPane flowPane;
  // Desired width of the image to be used for the buttons.
  private static final int TARGET_WIDTH = 150;
  // Calculates desired height based on the known aspect ratio of the images.
  private static final int TARGET_HEIGHT = (TARGET_WIDTH * 3) / 2;
  // HashMap to store ACTOR_NAME as a key and ACTOR_ID as a value.
  private HashMap<String, String> nameKeys = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    String search = "SELECT actor_id, actor_name, actor_image FROM actors";
    ResultSet actorsList = HotelBox.dbConnection.searchStatement(search);
    GeneralUtilities.createButtons(actorsList, nameKeys, flowPane,
        HotBoxNavigator.ACTOR_PAGE, TARGET_WIDTH, TARGET_HEIGHT, "ACTORS",
        HotBoxNavigator.ACTOR_LIST);
  }
}
