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
    ResultSet movies = HotelBox.dbConnection.searchStatement("MOVIES");
    String sqlStatement;

    try {
      while (movies.next()) {
        movie.put(movies.getInt("MOVIE_ID"), movies.getString("MOVIE_TITLE"));
      }

      // put top 5 search terms into their respective labels
      sqlStatement = "SELECT * FROM SEARCH_TERMS ORDER BY FREQUENCY DESC"
          + " LIMIT 5";
      ResultSet searches = HotelBox.dbConnection.searchStatement(sqlStatement,
          true);
      for (int i = 0; i < 5; i++) {
        searches.next();
        switch (i) {
          case 0:
            searchTermOne.setText(searches.getString("TERM") + " (" + searches
                .getInt("FREQUENCY") + ")");
            break;
          case 1:
            searchTermTwo.setText(searches.getString("TERM") + " (" + searches
                .getInt("FREQUENCY") + ")");
            break;
          case 2:
            searchTermThree.setText(searches.getString("TERM") + " (" + searches
                .getInt("FREQUENCY") + ")");
            break;
          case 3:
            searchTermFour.setText(searches.getString("TERM") + " (" + searches
                .getInt("FREQUENCY") + ")");
            break;
          case 4:
            searchTermFive.setText(searches.getString("TERM") + " (" + searches
                .getInt("FREQUENCY") + ")");
            break;
          default:
            break;
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      sqlStatement = "SELECT * FROM MOVIES ORDER BY TIMES_VIEWED DESC LIMIT 5";
      ResultSet lookedMovie = HotelBox.dbConnection.searchStatement(
          sqlStatement, true);
      for (int i = 1; i <= 5; i++) {
        lookedMovie.next();
        switch (i) {
          case 1:
            viewedMovieOne.setText(lookedMovie.getString("MOVIE_TITLE") + " ("
                + lookedMovie.getInt("TIMES_VIEWED") + ")");
            break;
          case 2:
            viewedMovieTwo.setText(lookedMovie.getString("MOVIE_TITLE") + " ("
                + lookedMovie.getInt("TIMES_VIEWED") + ")");
            break;
          case 3:
            viewedMovieThree.setText(lookedMovie.getString("MOVIE_TITLE") + " ("
                + lookedMovie.getInt("TIMES_VIEWED") + ")");
            break;
          case 4:
            viewedMovieFour.setText(lookedMovie.getString("MOVIE_TITLE") + " ("
                + lookedMovie.getInt("TIMES_VIEWED") + ")");
            break;
          case 5:
            viewedMovieFive.setText(lookedMovie.getString("MOVIE_TITLE") + " ("
                + lookedMovie.getInt("TIMES_VIEWED") + ")");
            break;
          default:
            break;
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      sqlStatement = "SELECT * FROM ACTORS ORDER BY TIMES_VIEWED DESC LIMIT 5";
      ResultSet viewedActors = HotelBox.dbConnection.searchStatement(
          sqlStatement, true);
      for (int i = 1; i <= 5; i++) {
        viewedActors.next();
        switch (i) {
          case 1:
            viewedActorOne.setText(viewedActors.getString("ACTOR_NAME") + " ("
                + viewedActors.getInt("TIMES_VIEWED") + ")");
            break;
          case 2:
            viewedActorTwo.setText(viewedActors.getString("ACTOR_NAME") + " ("
                + viewedActors.getInt("TIMES_VIEWED") + ")");
            break;
          case 3:
            viewedActorThree.setText(viewedActors.getString("ACTOR_NAME") + " ("
                + viewedActors.getInt("TIMES_VIEWED") + ")");
            break;
          case 4:
            viewedActorFour.setText(viewedActors.getString("ACTOR_NAME") + " ("
                + viewedActors.getInt("TIMES_VIEWED") + ")");
            break;
          case 5:
            viewedActorFive.setText(viewedActors.getString("ACTOR_NAME") + " ("
                + viewedActors.getInt("TIMES_VIEWED") + ")");
            break;
          default:
            break;
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      sqlStatement = "SELECT MOVIE_ID, COUNT(MOVIE_ID) as 'total' FROM"
          + " CUSTOMER_RENTALS GROUP BY MOVIE_ID ORDER BY COUNT(MOVIE_ID)"
          + " DESC LIMIT 5";
      ResultSet uniqueWatched = HotelBox.dbConnection.searchStatement(
          sqlStatement, true);
      for (int i = 1; i <= 5; i++) {
        uniqueWatched.next();
        int movieId = Integer.parseInt(uniqueWatched.getString("MOVIE_ID"));
        switch (i) {
          case 1:
            watchedMovieOne.setText(movie.get(movieId) + " (" + uniqueWatched
                .getInt("total") + ")");
            break;
          case 2:
            watchedMovieTwo.setText(movie.get(movieId) + " (" + uniqueWatched
                .getInt("total") + ")");
            break;
          case 3:
            watchedMovieThree.setText(movie.get(movieId) + " (" + uniqueWatched
                .getInt("total") + ")");
            break;
          case 4:
            watchedMovieFour.setText(movie.get(movieId) + " (" + uniqueWatched
                .getInt("total") + ")");
            break;
          case 5:
            watchedMovieFive.setText(movie.get(movieId) + " (" + uniqueWatched
                .getInt("total") + ")");
            break;
          default:
            break;
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      sqlStatement = "SELECT MOVIE_ID, COUNT(*) as 'number', SUM(RATING_NUM)"
          + " as 'total' FROM RATING GROUP BY MOVIE_ID ORDER BY (SUM"
          + "(RATING_NUM)/COUNT(*)) DESC LIMIT 5";
      ResultSet topRating = HotelBox.dbConnection.searchStatement(
          sqlStatement, true);
      topRating.first();
      Label[] topLabels = { ratedMovieOne, ratedMovieTwo, ratedMovieThree,
          ratedMovieFour, ratedMovieFive };
      findMovieRatings(topRating, topLabels);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      sqlStatement = "SELECT MOVIE_ID, COUNT(*) as 'number', SUM(RATING_NUM)"
          + " as 'total' FROM RATING GROUP BY MOVIE_ID ORDER BY (SUM"
          + "(RATING_NUM)/COUNT(*)) ASC LIMIT 5";
      ResultSet bottomRating = HotelBox.dbConnection.searchStatement(
          sqlStatement, true);
      bottomRating.first();
      Label[] bottomLabels = { bottomRatedOne, bottomRatedTwo,
          bottomRatedThree, bottomRatedFour, bottomRatedFive };
      findMovieRatings(bottomRating, bottomLabels);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      sqlStatement = "SELECT MOVIE_ID, COUNT(*) as 'number' FROM MOVIES";
      ResultSet counter = HotelBox.dbConnection.searchStatement(sqlStatement,
          true);
      counter.next();

      int numbers = counter.getInt("number");
      numMovies.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      sqlStatement = "SELECT CUSTOMER_ID, COUNT(*) as 'number' FROM CUSTOMER";
      ResultSet counter = HotelBox.dbConnection.searchStatement(sqlStatement,
          true);
      counter.next();

      int numbers = counter.getInt("number");
      numCustomers.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    try {
      // get ratings to put in their respective labels
      sqlStatement = "SELECT ACTOR_ID, COUNT(*) as 'number' FROM ACTORS";
      ResultSet counter = HotelBox.dbConnection.searchStatement(sqlStatement,
          true);
      counter.next();

      int numbers = counter.getInt("number");
      numActors.setText(Integer.toString(numbers));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void findMovieRatings(ResultSet ratings, Label[] labels) throws
      SQLException {
    for (Label label : labels) {
      int movieId = Integer.parseInt(ratings.getString("MOVIE_ID"));
      float avgRating = (float) (ratings.getInt("total"))
          / ratings.getInt("number");
      label.setText(String.format("%s (%.1f)", movie.get(movieId), avgRating));
      ratings.next();
    }
  }
}
