package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.Casting;
import util.GeneralUtilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 * @author Tyler Bratton
 */
public class CastingEditController implements Initializable {
  @FXML
  private Button cancelButton;
  @FXML
  private Button submitButton;
  @FXML
  private Button deleteButton;
  @FXML
  private ComboBox<String> moviePicker;
  @FXML
  private ComboBox<String> actorPicker;

  private Map<String, String> actorName = new HashMap<>();
  private Map<String, String> movieTitle = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var movies = HotelBox.dbConnection.getMovies();
    ObservableList<String> movieOptions = FXCollections.observableArrayList();
    movies.forEach(movie -> {
      var movieName = movie.getTitle();
      movieTitle.put(movieName, movie.getId());
      movieOptions.add(movieName);
    });

    moviePicker.setItems(movieOptions);

    var actors = HotelBox.dbConnection.getActors();
    ObservableList<String> actorOptions = FXCollections.observableArrayList();
    actors.forEach(actor -> {
      var name = actor.getName();
      actorName.put(name, actor.getId());
      actorOptions.add(name);
    });

    actorPicker.setItems(actorOptions);

    submitButton.setOnAction(event -> {
      var movie = movieTitle.get(moviePicker.getValue());
      var actor = actorName.get(actorPicker.getValue());
      System.out.println(movie + " : " + actor);

      if (HotelBox.dbConnection.getCasting(actor, movie).isPresent()) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Already Exists!");
        alert.setHeaderText(null);
        alert.setContentText("Casting entry already exists in database.");
        alert.showAndWait();
      } else {
        HotelBox.dbConnection.saveCasting(new Casting("", actor, movie));
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      }
    });

    deleteButton.setOnAction(event -> {
      var movie = movieTitle.get(moviePicker.getValue());
      var actor = actorName.get(actorPicker.getValue());
      System.out.println(movie + " : " + actor);

      var casting = HotelBox.dbConnection.getCasting(actor, movie);
      if (casting.isPresent()) {
        HotelBox.dbConnection.removeCasting(casting.get());
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      } else {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Does Not Exist!");
        alert.setHeaderText(null);
        alert.setContentText("Casting entry does not exist in database.");
        alert.showAndWait();
      }
    });

    cancelButton.setOnAction(event -> HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
