package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Genre;
import models.Movie;
import util.GeneralUtilities;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var genres = HotelBox.dbConnection.getGenres();

    ObservableList<String> options = FXCollections.observableArrayList();
    genres.forEach(genre -> {
      var genreName = genre.getGenreName();
      options.add(genreName);
    });

    movieGenre.setItems(options);

    var movie = HotelBox.dbConnection.getMovie(HotBoxNavigator.editRecord).orElse(new Movie());
    var id = movie.getId();

    if (HotBoxNavigator.editRecord != null) {
      movieTitle.setText(movie.getTitle());
      movieDirector.setText(movie.getDirector());
      movieDescription.setText(movie.getDescription());
      movieDate.setText(movie.getReleaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
      movieImage.setText(movie.getImage());
      moviePrice.setText(String.format("%.2f", movie.getPrice()));
      movieViewed.setText(movie.getViewed().toString());
      movieGenre.getSelectionModel().select(
          genres.indexOf(
              genres.stream()
                  .filter(g -> g.getGenreId().equals(movie.getGenreId()))
                  .findAny().orElse(new Genre())
          )
      );
    }

    submitButton.setOnAction(event -> {
      var title = movieTitle.getText();
      var director = movieTitle.getText();
      var description = movieDescription.getText();
      var date = movieDate.getText();
      var image = movieImage.getText();
      var price = moviePrice.getText();
      var viewed = movieViewed.getText();

      HotelBox.dbConnection.saveMovie(new Movie(
          id,
          title,
          director,
          description,
          LocalDate.parse(date),
          image,
          genres.get(genres.indexOf(
              genres.stream()
                  .filter(g -> g.getGenreName().equals(movieGenre.getValue()))
                  .findAny().orElse(new Genre())
          )).getGenreId(),
          new BigDecimal(price),
          Integer.valueOf(viewed),
          ZonedDateTime.now()
      ));

      GeneralUtilities.showSuccessMessage();
      HotBoxNavigator.loadPage(HotBoxNavigator.editRecord != null ? HotBoxNavigator.EDIT_PAGE :
          HotBoxNavigator.ADMIN_PAGE);
    });

    cancelButton.setOnAction(event -> HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
