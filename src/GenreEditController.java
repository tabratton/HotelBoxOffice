import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
      String search = String.format("SELECT * FROM genre WHERE genre_id=%s",
          HotBoxNavigator.editRecord);
      ResultSet rs = HotelBox.dbConnection.searchStatement(search);
      try {
        rs.first();
        id = rs.getString("genre_id");
        genreName.setText(rs.getString("genre_name"));
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
      submitButton.setOnAction(event -> {
        String genre = genreName.getText();
        String upString = String.format("UPDATE genre SET genre_name='%s'"
            + " WHERE genre_id=%s", genre, id);
        HotelBox.dbConnection.updateStatement(upString);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
      });
    } else {
      submitButton.setOnAction(event -> {
        String genre = genreName.getText();
        String upString = String.format("INSERT INTO genre (genre_name) VALUES"
            + " ('%s')", genre);
        HotelBox.dbConnection.updateStatement(upString);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      });
    }

    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
