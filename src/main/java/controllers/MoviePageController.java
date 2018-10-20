package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Movie;
import models.Rental;
import util.Dimensions;
import util.GeneralUtilities;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;


/**
 * FXML Controller class.
 *
 * @author Gabriel Guillen
 */
public class MoviePageController implements Initializable {

  @FXML
  private ListView<Text> listView;

  @FXML
  private Button playMovie;

  @FXML
  private Label movieTitle;

  @FXML
  private Button movieImageButton;

  @FXML
  private Button goBackButton;

  @FXML
  private ListView<Button> actorList;

  @FXML
  private Label movieRating;

  private HashMap<String, String> keys = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    var lastMovie = HotBoxNavigator.lastClickedMovieStack.peek();

    var movie = HotelBox.dbConnection.getMovie(lastMovie).orElse(new Movie());

    // search statement for listing casting for last clicked movie
    var actors = HotelBox.dbConnection.getActorsByMovieId(lastMovie);

    final var title = movie.getTitle();
    final var director = String.format("Director: %s", movie.getDirector());
    final var description = String.format("Description: %s", movie.getDescription());
    var releaseDate = String.format("Release Date: %s",
        movie.getReleaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    final var movieImage = movie.getImage();

    //sets title for the page
    movieTitle.setWrapText(true);
    movieTitle.setTextAlignment(TextAlignment.CENTER);
    movieTitle.setText(title);

    //sets image for the play movie button
    movieImageButton.setGraphic(
        GeneralUtilities.getImage(movieImage, new Dimensions(Dimensions.DimensionTypes.LARGE), "movies", lastMovie));
    movieImageButton.setStyle("-fx-background-color: transparent;");

    // Sets the text that will be each item of the list, and sets the text
    // wrapping property so that the scroll bar is not needed.
    var text = new Text(director);
    text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
    listView.getItems().add(text);
    text = new Text(description);
    text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
    listView.getItems().add(text);
    text = new Text(releaseDate);
    text.wrappingWidthProperty().bind(listView.widthProperty().subtract(30));
    listView.getItems().add(text);

    actors.forEach(actor -> {
      var name = actor.getName();
      var id = actor.getId();
      keys.put(name, id);
      var currentActor = new Button(name);
      currentActor.setStyle("-fx-background-color: transparent");
      currentActor.setTextAlignment(TextAlignment.LEFT);
      currentActor.setOnAction(event -> {
        var button = (Button) event.getSource();
        var currentTitle = button.getText();
        var currentId = keys.get(currentTitle);
        HotBoxNavigator.lastClickedActorStack.push(currentId);
        HotelBox.dbConnection.saveActor(actor);
        HotBoxNavigator.lastLoadedPageStack.push(HotBoxNavigator.MOVIE_PAGE);
        HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_PAGE);
        System.out.println(currentTitle);
      });

      this.actorList.getItems().add(currentActor);
    });

    goBackButton.setOnAction(event -> {
      HotBoxNavigator.lastClickedMovieStack.pop();
      HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
    });

    var moviePrice = movie.getPrice();
    playMovie.setText(String.format("Rent Movie for: $%.2f", moviePrice));

    playMovie.setOnAction(event -> {
      // Once pressed to rent a movie the balance of the movie gets
      // added to the customer balance
      HotelBox.dbConnection.addToBalance(HotelBox.getCurrentUserId(), moviePrice);

      // insert new line in customer rentals
      HotelBox.dbConnection.saveRental(new Rental(
          "",
          HotelBox.getCurrentUserId(),
          lastMovie,
          LocalDate.now(),
          moviePrice
      ));

      HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
    });

    var rating = HotelBox.dbConnection.getRatingForMovie(lastMovie);
    if (rating > 0.0) {
      movieRating.setText(String.format("Average user score: %.1f", rating));
    } else {
      movieRating.setText("This movie does not have any user scores yet.");
    }
  }

  /**
   * Directly loads the rate page from the movie page without renting, only
   * allows it to be loaded if the movie has been rented previously.
   */
  public void rateMovie() {
    if (HotelBox.dbConnection.checkRentalExists(
        HotelBox.getCurrentUserId(),
        HotBoxNavigator.lastClickedMovieStack.peek())
    ) {
      HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
    } else {
      var alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Movie Not Rented");
      alert.setHeaderText(null);
      alert.setContentText("You must rent a movie before you can rate it.");
      alert.showAndWait();
    }
  }
}

