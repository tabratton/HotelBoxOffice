import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;



/**
 * FXML Controller class.
 *
 * @author Gabriel Guillen
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
  private Button goBackButton;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value
    // HashMap<String, String> actorKeys = new HashMap<String, String>();
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    ResultSet rs = HotelBox.dbConnection.searchStatement("MOVIES", "MOVIE_ID",
        HotBoxNavigator.lastClickedMovie);
    ResultSet cs = HotelBox.dbConnection.searchStatement("CASTING","CASTING_ID",
        HotBoxNavigator.lastClickedMovie);
    ResultSet bs = HotelBox.dbConnection.searchStatement("CUSTOMER",
        "CUSTOMER_ID", HotelBox.getCurrentUserId());
    Connection con = HotelBox.dbConnection.getCon();
    try {
      //set variables with data from the database
      cs.first();
      bs.first();
      rs.first();
      final String title = rs.getString("MOVIE_TITLE");
      final String director = "Director: " + rs.getString("MOVIE_DIRECTOR");
      final String description = "Description: " + rs.getString("MOVIE_"
          + "DESCRIPTION");
      String releaseDate = rs.getString("MOVIE_RELEASE_DATE");
      final String movieImage = rs.getString("MOVIE_IMAGE");

      
      //sets title for the page
      movieTitle.setWrapText(true);
      movieTitle.setTextAlignment(TextAlignment.CENTER);
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
      movieImageButton.setGraphic(GeneralUtilities.getImage(movieImage, 300, 450));
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
      


      //set variables with data from the database
      Double balance = Double.parseDouble(bs.getString("CUSTOMER_BALANCE"));
      Double price = Double.parseDouble(rs.getString("MOVIE_PRICE"));
      Double newBalanceIfRented = balance + price;
      String newBalanceString = String.format("%.2f", newBalanceIfRented);
      
      
      goBackButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // Once the Button is clicked it goes back to the MainGrid
          HotBoxNavigator.loadPage(HotBoxNavigator.lastPageLoaded);

        }
           
      });
      
      //tried to set it to increase balance but failing 
      // is also trying to go RATE_Page failing too.
      playMovie.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // Once pressed to rent a movie the balance of the movie gets
          // subtracted from the customer balance
          String upString = String.format("UPDATE CUSTOMER SET " +
                  "CUSTOMER_BALANCE = %s WHERE CUSTOMER_ID = %s",
              newBalanceString, HotelBox.getCurrentUserId());
          HotelBox.dbConnection.updateStatement(upString);
          HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
        }
      });
      
      // Commented out and not removed in case we want to change back to this
      // code.
      //Things for the ListView
      //ObservableList<String> data = FXCollections.observableArrayList
      // (director,
      //description, releaseDate);
      //listView.setItems(data);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}

