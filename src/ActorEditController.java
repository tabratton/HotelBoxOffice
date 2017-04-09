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
      String search = String.format("SELECT * FROM actors WHERE actor_id=%s",
          HotBoxNavigator.editRecord);
      ResultSet rs = HotelBox.dbConnection.searchStatement(search);
      try {
        rs.first();
        actorName.setText(rs.getString("actor_name"));
        id = rs.getString("actor_id");
        actorImage.setText(rs.getString("actor_image"));
        actorBio.setText(rs.getString("actor_bio"));
        timesViewed.setText(rs.getString("times_viewed"));
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
      submitButton.setOnAction(event -> {
        String name = actorName.getText();
        String image = actorImage.getText();
        String bio = actorBio.getText().replace("'", "''");
        String viewed = timesViewed.getText();
        String update = String.format("UPDATE actors SET actor_name='%s',"
            + " actor_image='%s', actor_bio='%s', times_viewed=%s"
            + " WHERE actor_id=%s", name, image, bio, viewed, id);
        HotelBox.dbConnection.updateStatement(update);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
      });
    } else {
      submitButton.setOnAction(event -> {
        String name = actorName.getText();
        String image = actorImage.getText();
        String bio = actorBio.getText().replace("'", "''");
        String viewed = timesViewed.getText();
        String update = String.format("INSERT INTO actors (actor_name,"
            + " actor_image, actor_bio, times_viewed) VALUES ('%s','%s',"
            + "'%s',%s)", name, image, bio, viewed);
        HotelBox.dbConnection.updateStatement(update);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      });
    }
    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
    

