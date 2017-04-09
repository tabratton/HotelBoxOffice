import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Gabriel Guillen
 * @author Tyler Bratton
 */
public class RatePageController implements Initializable {

  @FXML
  private RadioButton one;
  @FXML
  private RadioButton two;
  @FXML
  private RadioButton three;
  @FXML
  private RadioButton four;
  @FXML
  private RadioButton five;

  private String currentRating;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    final ToggleGroup group = new ToggleGroup();
    one.setToggleGroup(group);
    two.setToggleGroup(group);
    three.setToggleGroup(group);
    four.setToggleGroup(group);
    five.setToggleGroup(group);

    group.selectedToggleProperty().addListener(
        (observable, oldValue, newValue) -> {
          RadioButton button = (RadioButton) group.getSelectedToggle();
          currentRating = Character.toString(button.getText().charAt(0));
          System.out.println(currentRating);
        });
    three.setSelected(true);
  }

  /**
   * Updates the database with the user rating. Inserts a new rating if one
   * does not exist, and modifies existing rating if movie already rated.
   */
  public void submitRating() {
    String update;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    if (isAlreadyRated()) {
      update = String.format("UPDATE rating SET rating_num=%s,"
              + " rating_date='%s' WHERE customer_id=%s AND movie_id=%s",
          currentRating, dateFormat.format(date), HotelBox.getCurrentUserId(),
          HotBoxNavigator.lastClickedMovieStack.peek());
    } else {
      update = String.format("INSERT INTO rating (customer_id, movie_id,"
              + " rating_num, rating_date) VALUES (%s, %s, %s,'%s')",
          HotelBox.getCurrentUserId(),
          HotBoxNavigator.lastClickedMovieStack.peek(), currentRating,
          dateFormat.format(date));
    }
    HotelBox.dbConnection.updateStatement(update);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Submission Successful");
    alert.setHeaderText(null);
    alert.setContentText("Thank you for your rating!");
    alert.showAndWait();
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
  }

  private boolean isAlreadyRated() {
    String search = String.format("SELECT rating_id FROM rating WHERE"
        + " customer_id=%s AND movie_id=%s", HotelBox.getCurrentUserId(),
        HotBoxNavigator.lastClickedMovieStack.peek());
    ResultSet ratings = HotelBox.dbConnection.searchStatement(search);
    try {
      return ratings.next();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return false;
  }
}

