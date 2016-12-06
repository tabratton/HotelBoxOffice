import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class ActorEditController implements Initializable {

  @FXML
  private Button cancelButton;
  @FXML
  private Button submitButton;
  @FXML
  private TextField actorName;
  @FXML
  private TextField actorImage;
  @FXML
  private TextArea actorBio;
  @FXML
  private TextField timesViewed;
  private String id;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    if (HotBoxNavigator.editRecord != null) {
      ResultSet rs = HotelBox.dbConnection.searchStatement("ACTORS",
          "ACTOR_ID", HotBoxNavigator.editRecord);
      try {
        rs.first();
        actorName.setText(rs.getString("ACTOR_NAME"));
        id = rs.getString("ACTOR_ID");
        actorImage.setText(rs.getString("ACTOR_IMAGE"));
        actorBio.setText(rs.getString("ACTOR_BIO"));
        timesViewed.setText(rs.getString("TIMES_VIEWED"));
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          String name = actorName.getText();
          String image = actorImage.getText();
          String bio = actorBio.getText().replace("'", "''");
          String viewed = timesViewed.getText();
          String upString = String.format("UPDATE ACTORS SET ACTOR_NAME = '%s',"
              + " ACTOR_IMAGE = '%s', ACTOR_BIO = '%s', TIMES_VIEWED = %s"
              + " WHERE ACTOR_ID = %s", name, image, bio, viewed, id);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
        }
      });
    } else {
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          String name = actorName.getText();
          String image = actorImage.getText();
          String bio = actorBio.getText().replace("'", "''");
          String viewed = timesViewed.getText();
          String upString = String.format("INSERT INTO ACTORS (ACTOR_NAME,"
              + " ACTOR_IMAGE, ACTOR_BIO, TIMES_VIEWED) VALUES ('%s','%s',"
              + "'%s',%s)", name, image, bio, viewed);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
        }
      });
    }
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      }
    });
  }
}
    

