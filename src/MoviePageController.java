import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * FXML Controller class.
 *
 * @author gabrielguillen
 */
public class MoviePageController implements Initializable {

  @FXML
  //private ListView<String> listView;
  private ListView<Text> listView;

  @FXML
  private Button playMovie;

  @FXML
  private Label movieTitle;

  @FXML
  private Button movieImageButton;

  @FXML
  private GridPane innerGridPane;
  
  @FXML
  private Button goBackButton;

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
      String releaseDate = rs.getString("MOVIE_RELEASE_DATE");
      final String movieImage = rs.getString("MOVIE_IMAGE");

      //sets title for the page
      movieTitle.setText(title);

      //Converts date stored in database to a date in the format of "April 2,
      // 2011"
      SimpleDateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
      DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
      try {
        Date date = isoDate.parse(releaseDate);
        releaseDate = "Release Date: " + df.format(date);
      } catch (ParseException ex) {
        System.out.println(ex.getMessage());
      }
      //sets image for the play movie button
      movieImageButton.setGraphic(ImageUtility.getImage(movieImage, 300, 450));
      movieImageButton.setStyle("-fx-background-color: transparent;");

      // Sets the text that will be each item of the list, and sets the text
      // wrapping property so that the scroll bar is not needed.
      Text text = new Text(director);
      text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
      listView.getItems().add(text);
      text = new Text(description);
      text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
      listView.getItems().add(text);
      text = new Text(releaseDate);
      text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
      listView.getItems().add(text);
      
      
          goBackButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            goBackButton = (Button) event.getSource();
           
            // Once the Button is clicked it goes back to the MainGrid
            HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);
          
            }
           });
      // Commented out and not removed in case we want to change back to this
      // code.
      //Things for the ListView
      //ObservableList<String> data = FXCollections.observableArrayList(director,
      //description, releaseDate);
      //listView.setItems(data);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}

