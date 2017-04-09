import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
public class StatsPageController implements Initializable {

  @FXML
  private Label watchedMovieOne;
  @FXML
  private Label watchedMovieTwo;
  @FXML
  private Label watchedMovieThree;
  @FXML
  private Label watchedMovieFour;
  @FXML
  private Label watchedMovieFive;
  @FXML
  private Label searchTermOne;
  @FXML
  private Label searchTermTwo;
  @FXML
  private Label searchTermThree;
  @FXML
  private Label searchTermFour;
  @FXML
  private Label searchTermFive;
  @FXML
  private Label viewedMovieOne;
  @FXML
  private Label viewedMovieTwo;
  @FXML
  private Label viewedMovieThree;
  @FXML
  private Label viewedMovieFour;
  @FXML
  private Label viewedMovieFive;
  @FXML
  private Label ratedMovieOne;
  @FXML
  private Label ratedMovieTwo;
  @FXML
  private Label ratedMovieThree;
  @FXML
  private Label ratedMovieFour;
  @FXML
  private Label ratedMovieFive;
  @FXML
  private Label bottomRatedOne;
  @FXML
  private Label bottomRatedTwo;
  @FXML
  private Label bottomRatedThree;
  @FXML
  private Label bottomRatedFour;
  @FXML
  private Label bottomRatedFive;
  @FXML
  private Label viewedActorOne;
  @FXML
  private Label viewedActorTwo;
  @FXML
  private Label viewedActorThree;
  @FXML
  private Label viewedActorFour;
  @FXML
  private Label viewedActorFive;
  @FXML
  private Label numMovies;
  @FXML
  private Label numActors;
  @FXML
  private Label numCustomers;

  private final Map<Integer, String> movie = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String search = "SELECT movie_id, movie_title FROM movies";
    ResultSet movies = HotelBox.dbConnection.searchStatement(search);
    try {
      while (movies.next()) {
        movie.put(movies.getInt("movie_id"), movies.getString("movie_title"));
      }

      // put top 5 search terms into their respective labels
      search = "SELECT * FROM search_terms ORDER BY frequency DESC LIMIT 5";
      ResultSet searches = HotelBox.dbConnection.searchStatement(search);
      Label[] labels = { searchTermOne, searchTermTwo, searchTermThree,
          searchTermFour, searchTermFive };
      for (Label label : labels) {
        searches.next();
        label.setText(String.format("%s (%d)", searches.getString("term"),
            searches.getInt("frequency")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      search = "SELECT movie_title, times_viewed FROM movies ORDER BY"
          + " times_viewed DESC LIMIT 5";
      ResultSet lookedMovie = HotelBox.dbConnection.searchStatement(search);
      Label[] labels = { viewedMovieOne, viewedMovieTwo, viewedMovieThree,
          viewedMovieFour, viewedMovieFive };
      for (Label label : labels) {
        lookedMovie.next();
        label.setText(String.format("%s (%d)",
            lookedMovie.getString("movie_title"),
            lookedMovie.getInt("times_viewed")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      search = "SELECT actor_name, times_viewed FROM actors ORDER BY"
          + " times_viewed DESC LIMIT 5";
      ResultSet viewedActors = HotelBox.dbConnection.searchStatement(search);
      Label[] labels = { viewedActorOne, viewedActorTwo, viewedActorThree,
          viewedActorFour, viewedActorFive };
      for (Label label : labels) {
        viewedActors.next();
        label.setText(String.format("%s (%d)",
            viewedActors.getString("actor_name"),
            viewedActors.getInt("times_viewed")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      search = "SELECT movie_id, COUNT(movie_id) total FROM customer_rentals"
          + " GROUP BY movie_id ORDER BY COUNT(movie_id) DESC LIMIT 5";
      ResultSet uniqueWatched = HotelBox.dbConnection.searchStatement(search);
      Label[] labels = { watchedMovieOne, watchedMovieTwo, watchedMovieThree,
          watchedMovieFour, watchedMovieFive };
      for (Label label : labels) {
        uniqueWatched.next();
        int movieId = Integer.parseInt(uniqueWatched.getString("MOVIE_ID"));
        label.setText(String.format("%s (%d)", movie.get(movieId),
            uniqueWatched.getInt("total")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      search = "SELECT movie_id, SUM(rating_num)/COUNT(movie_id) avg FROM"
          + " rating GROUP BY movie_id ORDER BY (SUM(rating_num)/COUNT(*))"
          + " DESC LIMIT 5";
      ResultSet topRating = HotelBox.dbConnection.searchStatement(search);
      Label[] topLabels = { ratedMovieOne, ratedMovieTwo, ratedMovieThree,
          ratedMovieFour, ratedMovieFive };
      for (Label label : topLabels) {
        topRating.next();
        label.setText(String.format("%s (%.1f)",
            movie.get(Integer.parseInt(topRating.getString("movie_id"))),
            topRating.getDouble("avg")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      search = "SELECT movie_id, SUM(rating_num)/COUNT(movie_id) avg FROM"
          + " rating GROUP BY movie_id ORDER BY (SUM(rating_num)/COUNT(*))"
          + " ASC LIMIT 5";
      ResultSet bottomRating = HotelBox.dbConnection.searchStatement(search);
      Label[] bottomLabels = { bottomRatedOne, bottomRatedTwo, bottomRatedThree,
          bottomRatedFour, bottomRatedFive };
      for (Label label : bottomLabels) {
        bottomRating.next();
        label.setText(String.format("%s (%.1f)",
            movie.get(Integer.parseInt(bottomRating.getString("movie_id"))),
            bottomRating.getDouble("avg")));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      search = "SELECT COUNT(*) number FROM movies";
      ResultSet counter = HotelBox.dbConnection.searchStatement(search);
      counter.next();

      int numbers = counter.getInt("number");
      numMovies.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      search = "SELECT COUNT(*) number FROM customer";
      ResultSet counter = HotelBox.dbConnection.searchStatement(search);
      counter.next();

      int numbers = counter.getInt("number");
      numCustomers.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      search = "SELECT COUNT(*) number FROM actors";
      ResultSet counter = HotelBox.dbConnection.searchStatement(search);
      counter.next();

      int numbers = counter.getInt("number");
      numActors.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
