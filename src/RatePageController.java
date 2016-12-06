import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
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
 * @author Gabriel Guillen and Tyler Bratton
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
  private boolean alreadyRated;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    final ToggleGroup group = new ToggleGroup();
    one.setToggleGroup(group);
    two.setToggleGroup(group);
    three.setToggleGroup(group);
    four.setToggleGroup(group);
    five.setToggleGroup(group);

    group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable,
                          Toggle oldValue, Toggle newValue) {
        RadioButton button = (RadioButton) group.getSelectedToggle();
        currentRating = Character.toString(button.getText().charAt(0));
        System.out.println(currentRating);
      }
    });
    three.setSelected(true);
  }

  /**
   * Updates the database with the user rating. Inserts a new rating if one
   * does not exist, and modifies existing rating if movie already rated.
   */
  public void submitRating() {
    setAlreadyRated();

    String upString;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    if (alreadyRated) {
      upString = String.format("UPDATE RATING SET RATING_NUM = %s,"
              + " RATING_DATE = '%s' WHERE CUSTOMER_ID = %s AND MOVIE_ID = %s",
          currentRating, dateFormat.format(date), HotelBox.getCurrentUserId(),
          HotBoxNavigator.lastClickedMovieStack.peek());
    } else {
      upString = String.format("INSERT INTO RATING (CUSTOMER_ID, MOVIE_ID,"
              + " RATING_NUM, RATING_DATE) VALUES (%s, %s, %s,'%s')",
          HotelBox.getCurrentUserId(), HotBoxNavigator.lastClickedMovieStack
              .peek(), currentRating, dateFormat.format(date));
    }
    HotelBox.dbConnection.updateStatement(upString);

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Submission Successful");
    alert.setHeaderText(null);
    alert.setContentText("Thank you for your rating!");
    alert.showAndWait();
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
  }

  private void setAlreadyRated() {
    ResultSet ratings = HotelBox.dbConnection.searchStatement(
        String.format("SELECT * FROM RATING WHERE CUSTOMER_ID = %s AND"
                + " MOVIE_ID = %s", HotelBox.getCurrentUserId(), HotBoxNavigator
                .lastClickedMovieStack.peek()), true);
    try {
      alreadyRated = ratings.next();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}

