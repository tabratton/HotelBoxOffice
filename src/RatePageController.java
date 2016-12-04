import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Gabriel Guillen & Tyler Bratton
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

  /**
   * Initializes the controller class.
   */
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
  }

  private void setAlreadyRated() {
    ResultSet ratings = HotelBox.dbConnection.searchStatement(String.format
        ("SELECT * FROM RATING WHERE CUSTOMER_ID = %s AND MOVIE_ID = %s",
            HotelBox.getCurrentUserId(), HotBoxNavigator
                .lastClickedMovieStack.peek()), true);
    try {
      alreadyRated = ratings.next();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

}

