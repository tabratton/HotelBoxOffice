import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

  private HashMap<String, Integer> actorName = new HashMap<>();
  private HashMap<String, Integer> movieTitle = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String search = "SELECT movie_id, movie_title FROM movies";
    ResultSet movies = HotelBox.dbConnection.searchStatement(search);
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (movies.next()) {
        String movieName = movies.getString("movie_title");
        movieTitle.put(movieName, movies.getInt("movie_id"));
        options.add(movieName);
      }
      moviePicker.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    search = "SELECT actor_id, actor_name FROM actors";
    ResultSet actors = HotelBox.dbConnection.searchStatement(search);
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (actors.next()) {
        String name = actors.getString("actor_name");
        actorName.put(name, actors.getInt("actor_id"));
        options.add(name);
      }
      actorPicker.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    submitButton.setOnAction(event -> {
      int movie = movieTitle.get(moviePicker.getValue());
      int actor = actorName.get(actorPicker.getValue());
      String check = String.format("SELECT casting_id FROM casting WHERE"
          + " actor_id=%d AND movie_id=%d", actor, movie);
      ResultSet checkIfExists = HotelBox.dbConnection.searchStatement(check);
      System.out.println(movie + " : " + actor);
      try {
        if (checkIfExists.next()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Already Exists!");
          alert.setHeaderText(null);
          alert.setContentText("Casting entry already exists in database.");
          alert.showAndWait();
        } else {
          String upString = String.format("INSERT INTO casting (actor_id,"
              + " movie_id) VALUES (%s,%s)", actor, movie);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    });

    deleteButton.setOnAction(event -> {
      int movie = movieTitle.get(moviePicker.getValue());
      int actor = actorName.get(actorPicker.getValue());
      String check = String.format("SELECT casting_id FROM casting"
          + " WHERE actor_id=%d AND movie_id=%d", actor, movie);
      ResultSet checkIfExists = HotelBox.dbConnection.searchStatement(check);
      System.out.println(movie + " : " + actor);
      try {
        if (checkIfExists.next()) {
          String upString = String.format("DELETE FROM casting WHERE"
              + " actor_id=%d AND movie_id=%d", actor, movie);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Does Not Exist!");
          alert.setHeaderText(null);
          alert.setContentText("Casting entry does not exist in database.");
          alert.showAndWait();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    });

    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
