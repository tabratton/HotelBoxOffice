import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    ResultSet rs = HotelBox.dbConnection.searchStatement("MOVIES", "MOVIE_ID",
        lastMovie);

    //search statement for listing casting for last clicked movie
    ResultSet actorList = HotelBox.dbConnection.searchStatement(
        String.format("SELECT * FROM MOVIES, CASTING, ACTORS WHERE CASTING"
            + ".MOVIE_ID = '%s' AND CASTING.MOVIE_ID = MOVIES.MOVIE_ID AND"
            + " CASTING.ACTOR_ID = ACTORS.ACTOR_ID", lastMovie), true);
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
      movieImageButton.setGraphic(GeneralUtilities.getImage(movieImage, 300,
          450, "MOVIES", lastMovie));
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
        String name = actorList.getString("ACTOR_NAME");
        String id = actorList.getString("ACTOR_ID");
        keys.put(name, id);
        Button currentActor = new Button(name);
        currentActor.setStyle("-fx-background-color: transparent");
        currentActor.setTextAlignment(TextAlignment.LEFT);
        currentActor.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Button button = (Button) event.getSource();
            String currentTitle = button.getText();
            String currentId = keys.get(currentTitle);
            //HotBoxNavigator.lastClickedActor = currentId;
            HotBoxNavigator.lastClickedActorStack.push(currentId);
            HotelBox.dbConnection.updateStatement("UPDATE ACTORS SET ACTORS"
                + ".TIMES_VIEWED = ACTORS.TIMES_VIEWED + 1 WHERE ACTORS"
                + ".ACTOR_ID = " + currentId);
            //HotBoxNavigator.lastPageLoaded = currentPage;
            HotBoxNavigator.lastLoadedPageStack.push(HotBoxNavigator
                .MOVIE_PAGE);
            // Once the movie page is made, this line will load it.
            // Ideally the initialize() method of that page will read
            // lastClickedMovie and use that string to load the correct data
            // for the clicked movie.
            HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_PAGE);
            System.out.println(currentTitle);
          }
        });
        this.actorList.getItems().add(currentActor);
        actorList.next();
      }

      goBackButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          HotBoxNavigator.lastClickedMovieStack.pop();
          HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
        }
      });

      double moviePrice = Double.parseDouble(rs.getString("MOVIE_PRICE"));
      playMovie.setText(String.format("%s for: $%.2f", playMovie.getText(),
          moviePrice));

      playMovie.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // Once pressed to rent a movie the balance of the movie gets
          // added to the customer balance
          String upString = String.format("UPDATE CUSTOMER, MOVIES SET"
                  + " CUSTOMER.CUSTOMER_BALANCE = CUSTOMER.CUSTOMER_BALANCE +"
                  + " MOVIES.MOVIE_PRICE WHERE CUSTOMER.CUSTOMER_ID = %s AND"
                  + " MOVIES.MOVIE_ID = %s", HotelBox.getCurrentUserId(),
              lastMovie);
          HotelBox.dbConnection.updateStatement(upString);

          // insert new line in customer rentals
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          Date date = new Date();
          upString = String.format("INSERT INTO CUSTOMER_RENTALS"
                  + " (CUSTOMER_ID, MOVIE_ID, RENTAL_DATE) VALUES (%s,%s,'%s')",
              HotelBox.getCurrentUserId(), lastMovie, dateFormat.format(date));
          HotelBox.dbConnection.updateStatement(upString);
          HotBoxNavigator.loadPage(HotBoxNavigator.RATE_PAGE);
        }
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
    String search = String.format("SELECT RATING_NUM FROM RATING WHERE"
        + " MOVIE_ID = %s", movie);
    ResultSet ratings = HotelBox.dbConnection.searchStatement(search, true);
    double ratingAverage = -10.0;
    try {
      if (ratings.next()) {
        ratings.last();
        int numRatings = ratings.getRow();
        ratings.first();
        int ratingsSum = 0;
        for (int i = 0; i < numRatings; i++) {
          ratingsSum += Integer.parseInt(ratings.getString("RATING_NUM"));
        }
        ratingAverage = ratingsSum / (numRatings * 1.0);
      }
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
    ResultSet customer = HotelBox.dbConnection.searchStatement(
        String.format("SELECT * FROM CUSTOMER_RENTALS WHERE CUSTOMER_ID = %s"
            + " AND MOVIE_ID = %s", HotelBox.getCurrentUserId(), HotBoxNavigator
            .lastClickedMovieStack.peek()), true);
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

