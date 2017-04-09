import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value
    // HashMap<String, String> actorKeys = new HashMap<String, String>();
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    String lastMovie = HotBoxNavigator.lastClickedMovieStack.peek();

    String search = String.format("SELECT * FROM movies WHERE movie_id=%s",
        lastMovie);
    ResultSet rs = HotelBox.dbConnection.searchStatement(search);

    // search statement for listing casting for last clicked movie
    search = String.format("SELECT actors.actor_id, actors.actor_name FROM"
        + " actors INNER JOIN (SELECT casting.actor_id FROM casting INNER JOIN"
        + " MOVIES on casting.MOVIE_ID = movies.MOVIE_ID WHERE movies"
        + ".movie_id=%s) cast ON actors.actor_id=cast.actor_id", lastMovie);
    ResultSet actorList = HotelBox.dbConnection.searchStatement(search);
    try {
      // set variables with data from the database
      rs.first();
      final String title = rs.getString("movie_title");
      final String director = "Director: " + rs.getString("movie_director");
      final String description = "Description: " + rs.getString(
          "movie_description");
      String releaseDate = rs.getString("movie_release_date");
      final String movieImage = rs.getString("movie_image");

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
      movieImageButton.setGraphic(GeneralUtilities.getImage(movieImage,
          new Dimensions(Dimensions.DimensionTypes.LARGE), "MOVIES",
          lastMovie));
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

      actorList.last();
      int numRows = actorList.getRow();
      actorList.first();
      for (int i = 0; i < numRows; i++) {
        String name = actorList.getString("actor_name");
        String id = actorList.getString("actor_id");
        keys.put(name, id);
        Button currentActor = new Button(name);
        currentActor.setStyle("-fx-background-color: transparent");
        currentActor.setTextAlignment(TextAlignment.LEFT);
        currentActor.setOnAction(event -> {
          Button button = (Button) event.getSource();
          String currentTitle = button.getText();
          String currentId = keys.get(currentTitle);
          HotBoxNavigator.lastClickedActorStack.push(currentId);
          String update = String.format("UPDATE actors SET actors"
                  + ".times_viewed=actors.times_viewed+1 WHERE actors"
                  + ".actor_id=%s", currentId);
          HotelBox.dbConnection.updateStatement(update);
          HotBoxNavigator.lastLoadedPageStack.push(HotBoxNavigator.MOVIE_PAGE);
          HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_PAGE);
          System.out.println(currentTitle);
        });
        this.actorList.getItems().add(currentActor);
        actorList.next();
      }

      goBackButton.setOnAction(event -> {
        HotBoxNavigator.lastClickedMovieStack.pop();
        HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
      });

      double moviePrice = Double.parseDouble(rs.getString("movie_price"));
      playMovie.setText(String.format("%s for: $%.2f", playMovie.getText(),
          moviePrice));

      playMovie.setOnAction(event -> {
        // Once pressed to rent a movie the balance of the movie gets
        // added to the customer balance
        String update = String.format("UPDATE customer, movies SET customer."
            + " customer_balance=customer.customer_balance+movies.movie_price"
            + " WHERE customer.customer_id=%s AND movies.movie_id=%s",
            HotelBox.getCurrentUserId(), lastMovie);
        HotelBox.dbConnection.updateStatement(update);

        // insert new line in customer rentals
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        update = String.format("INSERT INTO customer_rentals (customer_id,"
                + " movie_id, rental_date) VALUES (%s,%s,'%s')",
            HotelBox.getCurrentUserId(), lastMovie, dateFormat.format(date));
        HotelBox.dbConnection.updateStatement(update);
        HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
      });

      double rating = getMovieRating(lastMovie);
      if (rating > 0.0) {
        movieRating.setText(String.format("Average user score: %.1f", rating));
      } else {
        movieRating.setText("This movie does not have any user scores yet.");
      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private double getMovieRating(String movie) {
    String search = String.format("SELECT SUM(rating_num)/COUNT(rating_num) avg"
        + " FROM rating WHERE movie_id=%s", movie);
    ResultSet ratings = HotelBox.dbConnection.searchStatement(search);
    double ratingAverage = -10.0;
    try {
      ratings.first();
      ratingAverage = ratings.getDouble("avg");
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return ratingAverage;
  }

  /**
   * Directly loads the rate page from the movie page without renting, only
   * allows it to be loaded if the movie has been rented previously.
   */
  public void rateMovie() {
    String search = String.format("SELECT customer_id FROM customer_rentals"
            + " WHERE customer_id=%s AND movie_id=%s",
        HotelBox.getCurrentUserId(),
        HotBoxNavigator.lastClickedMovieStack.peek());
    ResultSet customer = HotelBox.dbConnection.searchStatement(search);
    try {
      if (customer.next()) {
        HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Movie Not Rented");
        alert.setHeaderText(null);
        alert.setContentText("You must rent a movie before you can rate it.");
        alert.showAndWait();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}

