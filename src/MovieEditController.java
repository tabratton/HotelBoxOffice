import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class MovieEditController implements Initializable {

  @FXML
  private TextField movieTitle;
  @FXML
  private TextField movieDirector;
  @FXML
  private TextArea movieDescription;
  @FXML
  private TextField movieDate;
  @FXML
  private TextField movieImage;
  @FXML
  private ChoiceBox<String> movieGenre;
  @FXML
  private TextField moviePrice;
  @FXML
  private TextField movieViewed;
  @FXML
  private Button cancelButton;
  @FXML
  private Button submitButton;
  private String id;
  private int numGenres = 0;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String search = "SELECT genre_name FROM genre";
    ResultSet genres = HotelBox.dbConnection.searchStatement(search);
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (genres.next()) {
        String genreName = genres.getString("genre_name");
        options.add(genreName);
        numGenres++;
      }
      movieGenre.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    if (HotBoxNavigator.editRecord != null) {
      search = String.format("SELECT * FROM movies WHERE movie_id=%s",
          HotBoxNavigator.editRecord);
      ResultSet rs = HotelBox.dbConnection.searchStatement(search);
      try {
        rs.first();

        id = rs.getString("movie_id");
        movieTitle.setText(rs.getString("movie_title"));
        movieDirector.setText(rs.getString("movie_director"));
        movieDescription.setText(rs.getString("movie_description"));
        movieDate.setText(rs.getString("movie_release_date"));
        movieImage.setText(rs.getString("movie_image"));

        Double balance = Double.parseDouble(rs.getString("movie_price"));
        moviePrice.setText(String.format("%.2f", balance));
        movieViewed.setText(Integer.toString(rs.getInt("times_viewed")));
        movieGenre.getSelectionModel().select(rs.getInt("genre_id") - 1);

        submitButton.setOnAction(event -> {
          String title = movieTitle.getText();
          String director = movieTitle.getText();
          String description = movieDescription.getText().replace("'", "''");
          String date = movieDate.getText();
          String image = movieImage.getText();
          String price = moviePrice.getText();
          String viewed = movieViewed.getText();
          int genre = 1;
          for (int i = 1; i <= numGenres; i++) {
            if (movieGenre.getSelectionModel().isSelected(i)) {
              genre = i + 1;
            }
          }

          String upString = String.format("UPDATE movies SET movie_title='%s',"
                  + " movie_director='%s', movie_description='%s',"
                  + " movie_release_date='%s', movie_image='%s',"
                  + " movie_price=%s, times_viewed=%s, genre_id=%s WHERE"
                  + " movie_id=%s", title, director, description, date, image,
              price, viewed, genre, id);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
        });
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    } else {
      submitButton.setOnAction(event -> {
        String title = movieTitle.getText();
        String director = movieTitle.getText();
        String description = movieDescription.getText().replace("'", "''");
        String date = movieDate.getText();
        String image = movieImage.getText();
        String price = moviePrice.getText();
        String viewed = movieViewed.getText();
        int genre = 1;
        for (int i = 1; i <= numGenres; i++) {
          if (movieGenre.getSelectionModel().isSelected(i)) {
            genre = i + 1;
          }
        }
        String upString = String.format("INSERT INTO movies (movie_title,"
                + " movie_director, movie_description, movie_release_date,"
                + " movie_image, genre_id, movie_price, times_viewed)"
                + " VALUES ('%s', '%s', '%s', '%s', '%s', %s, %s, %s)",
            title, director, description, date, image, genre, price, viewed);
        HotelBox.dbConnection.updateStatement(upString);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      });
    }

    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
