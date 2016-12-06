import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class GenreEditController implements Initializable {

  @FXML
  private TextField genreName;
  @FXML
  private Button submitButton;
  @FXML
  private Button cancelButton;

  private String id;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    if (HotBoxNavigator.editRecord != null) {
      ResultSet rs = HotelBox.dbConnection.searchStatement("GENRE",
          "GENRE_ID", HotBoxNavigator.editRecord);
      try {
        rs.first();
        id = rs.getString("GENRE_ID");
        genreName.setText(rs.getString("GENRE_NAME"));
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          String genre = genreName.getText();
          String upString = String.format("UPDATE GENRE SET GENRE_NAME = '%s'"
              + " WHERE GENRE_ID = %s", genre, id);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
        }
      });
    } else {
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          String genre = genreName.getText();
          String upString = String.format("INSERT INTO GENRE (GENRE_NAME)"
              + " VALUES ('%s')", genre);
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
