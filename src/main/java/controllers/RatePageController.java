package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import models.Rating;

import java.net.URL;
import java.time.ZonedDateTime;
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
    final var group = new ToggleGroup();
    one.setToggleGroup(group);
    two.setToggleGroup(group);
    three.setToggleGroup(group);
    four.setToggleGroup(group);
    five.setToggleGroup(group);

    RadioButton[] ratings = {one, two, three, four, five};

    group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      var button = (RadioButton) group.getSelectedToggle();
      currentRating = Character.toString(button.getText().charAt(0));
      System.out.println(currentRating);
    });

    var rating = HotelBox.dbConnection.getRating(HotelBox.getCurrentUserId(),
        HotBoxNavigator.lastClickedMovieStack.peek());

    rating.ifPresentOrElse(r -> ratings[r.getScore() - 1].setSelected(true), () -> ratings[2].setSelected(true));
  }

  /**
   * Updates the database with the user rating. Inserts a new rating if one
   * does not exist, and modifies existing rating if movie already rated.
   */
  public void submitRating() {
    HotelBox.dbConnection.saveRating(new Rating(
        "",
        HotelBox.getCurrentUserId(),
        HotBoxNavigator.lastClickedMovieStack.peek(),
        Integer.parseInt(currentRating),
        ZonedDateTime.now()
    ));

    var alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Submission Successful");
    alert.setHeaderText(null);
    alert.setContentText("Thank you for your rating!");
    alert.showAndWait();
    HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
  }
}

