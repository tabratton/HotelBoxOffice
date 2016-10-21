import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author gabrielguillen
 */
public class MoviePageController implements Initializable {

  @FXML
  private ListView<String> listView;

  @FXML
  private Button playMovie;

  @FXML
  private Label movieTitle;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value
    HashMap<String, String> actorKeys = new HashMap<String, String>();
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    ResultSet rs = HotelBox.dbConnection.searchStatement("MOVIES", "MOVIE_ID",
        HotBoxNavigator.lastClickedMovie);

    try {
      //set variables with data from the database
      rs.first();
      final String title = rs.getString("MOVIE_TITLE");
      final String director = "Director: " + rs.getString("MOVIE_DIRECTOR");
      final String description = "Description: " + rs.getString("MOVIE_"
          + "DESCRIPTION");
      final String releaseDate = "Release Date: " + rs.getString("MOVIE_"
          + "RELEASE_DATE");
      final String movieImage = rs.getString("MOVIE_IMAGE");

      //sets title for the page
      movieTitle.setText(title);

      //sets image for the play movie button
      playMovie.setGraphic(ImageUtility.getImage(movieImage, 420, 500));

      //Things for the ListView
      ObservableList<String> data = FXCollections.observableArrayList(director,
          description, releaseDate);
      listView.setItems(data);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
