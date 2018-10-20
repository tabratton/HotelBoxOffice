package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Genre;
import util.GeneralUtilities;

import java.net.URL;
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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var genre = HotelBox.dbConnection.getGenre(HotBoxNavigator.editRecord).orElse(new Genre());
    var id = genre.getGenreId();

    if (HotBoxNavigator.editRecord != null) {
      genreName.setText(genre.getGenreName());
    }

    submitButton.setOnAction(event -> {
      var name = genreName.getText();
      HotelBox.dbConnection.saveGenre(new Genre(id, name));
      GeneralUtilities.showSuccessMessage();
      HotBoxNavigator.loadPage(HotBoxNavigator.editRecord != null ? HotBoxNavigator.EDIT_PAGE :
          HotBoxNavigator.ADMIN_PAGE);
    });

    cancelButton.setOnAction(event -> HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
