import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
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

    ResultSet genres = HotelBox.dbConnection.searchStatement("GENRE");
    try {
      ObservableList<String> options = FXCollections.observableArrayList();
      while (genres.next()) {
        String genreName = genres.getString("GENRE_NAME");
        options.add(genreName);
        numGenres++;
      }
      movieGenre.setItems(options);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    if (HotBoxNavigator.editRecord != null) {
      ResultSet rs = HotelBox.dbConnection.searchStatement("MOVIES",
          "MOVIE_ID", HotBoxNavigator.editRecord);

      try {
        rs.first();

        id = rs.getString("MOVIE_ID");
        movieTitle.setText(rs.getString("MOVIE_TITLE"));
        movieDirector.setText(rs.getString("MOVIE_DIRECTOR"));
        movieDescription.setText(rs.getString("MOVIE_DESCRIPTION"));
        movieDate.setText(rs.getString("MOVIE_RELEASE_DATE"));
        movieImage.setText(rs.getString("MOVIE_IMAGE"));

        Double balance = Double.parseDouble(rs.getString("MOVIE_PRICE"));
        moviePrice.setText(String.format("%.2f", balance));
        movieViewed.setText(Integer.toString(rs.getInt("TIMES_VIEWED")));
        movieGenre.getSelectionModel().select(rs.getInt("GENRE_ID") - 1);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
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

            String upString = String.format("UPDATE MOVIES SET MOVIE_TITLE"
                    + " = '%s', MOVIE_DIRECTOR = '%s', MOVIE_DESCRIPTION"
                    + " = '%s', MOVIE_RELEASE_DATE = '%s', MOVIE_IMAGE ="
                    + " '%s', MOVIE_PRICE = %s, TIMES_VIEWED = %s,"
                    + " GENRE_ID = %s WHERE MOVIE_ID = %s", title, director,
                description, date, image, price, viewed, genre, id);
            HotelBox.dbConnection.updateStatement(upString);
            GeneralUtilities.showSuccessMessage();
            HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
          }
        });
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    } else {
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
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
          String upString = String.format("INSERT INTO MOVIES (MOVIE_TITLE,"
                  + " MOVIE_DIRECTOR, MOVIE_DESCRIPTION, MOVIE_RELEASE_DATE,"
                  + " MOVIE_IMAGE, GENRE_ID, MOVIE_PRICE, TIMES_VIEWED)"
                  + " VALUES ('%s', '%s', '%s', '%s', '%s', %s, %s, %s)",
              title, director, description, date, image, genre, price, viewed);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
        }
      });
    }

    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      }
    });
  }
}
