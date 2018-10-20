package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Actor;
import util.GeneralUtilities;

import java.net.URL;
import java.time.ZonedDateTime;
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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var actor = HotelBox.dbConnection.getActor(HotBoxNavigator.editRecord).orElse(new Actor());
    if (HotBoxNavigator.editRecord != null) {
      actorName.setText(actor.getName());
      actorImage.setText(actor.getImage());
      actorBio.setText(actor.getBio());
      timesViewed.setText(actor.getViewed().toString());
    }

    submitButton.setOnAction(event -> {
      HotelBox.dbConnection.saveActor(new Actor(
          actor.getId(),
          actorName.getText(),
          actorImage.getText(),
          actorBio.getText(),
          Integer.parseInt(timesViewed.getText()),
          ZonedDateTime.now()
      ));
      GeneralUtilities.showSuccessMessage();
      HotBoxNavigator.loadPage(HotBoxNavigator.editRecord != null ? HotBoxNavigator.EDIT_PAGE :
          HotBoxNavigator.ADMIN_PAGE);
    });

    cancelButton.setOnAction(event -> HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
    

