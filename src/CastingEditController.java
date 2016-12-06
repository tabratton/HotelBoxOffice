import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
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

  private String id;
  private Map actorName = new HashMap();
  private Map movieTitle = new HashMap();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    ResultSet movies = HotelBox.dbConnection.searchStatement("MOVIES");
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (movies.next()) {
        movieTitle.put(movies.getString("MOVIE_TITLE"),
            movies.getInt("MOVIE_ID"));
        String movieName = movies.getString("MOVIE_TITLE");
        options.add(movieName);
      }
      moviePicker.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    ResultSet actors = HotelBox.dbConnection.searchStatement("ACTORS");
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (actors.next()) {
        actorName.put(actors.getString("ACTOR_NAME"),
            actors.getInt("ACTOR_ID"));
        String actorName = actors.getString("ACTOR_NAME");
        options.add(actorName);
      }
      actorPicker.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    submitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int movie = (int) movieTitle.get(moviePicker.getValue());
        int actor = (int) actorName.get(actorPicker.getValue());
        ResultSet checkIfExists = HotelBox.dbConnection.searchStatement(
            String.format("SELECT * FROM CASTING WHERE ACTOR_ID = %d AND"
                + " MOVIE_ID = %d", actor, movie), true);
        System.out.println(movie + " : " + actor);
        try {
          if (checkIfExists.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Already Exists!");
            alert.setHeaderText(null);
            alert.setContentText("Casting entry already exists in database.");
            alert.showAndWait();
          } else {
            String upString = String.format("INSERT INTO CASTING (ACTOR_ID,"
                + " MOVIE_ID) VALUES (%s,%s)", actor, movie);
            HotelBox.dbConnection.updateStatement(upString);
            GeneralUtilities.showSuccessMessage();
            HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
          }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      }
    });

    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int movie = (int) movieTitle.get(moviePicker.getValue());
        int actor = (int) actorName.get(actorPicker.getValue());
        ResultSet checkIfExists = HotelBox.dbConnection.searchStatement(
            String.format("SELECT * FROM CASTING WHERE ACTOR_ID = %d AND"
                + " MOVIE_ID = %d", actor, movie), true);
        System.out.println(movie + " : " + actor);
        try {
          if (checkIfExists.next()) {
            String upString = String.format("DELETE FROM CASTING WHERE"
                + " ACTOR_ID = %d AND MOVIE_ID = %d", actor, movie);
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
      }
    });

    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      }
    });
  }

}
