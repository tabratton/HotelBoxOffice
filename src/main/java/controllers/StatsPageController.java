package controllers;

import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
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

  private final Map<String, String> movieMap = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var movies = HotelBox.dbConnection.getMovies();

    movies.forEach(movie -> movieMap.put(movie.getId(), movie.getTitle()));

    // put top 5 search terms into their respective labels
    var searches = HotelBox.dbConnection.getSearchTerms(true);
    Label[] searchLabels = {searchTermOne, searchTermTwo, searchTermThree,
        searchTermFour, searchTermFive};
    for (var i = 0; i < searchLabels.length && i < searches.size(); i++) {
      searchLabels[i].setText(String.format("%s (%d)", searches.get(i).getTerm(), searches.get(i).getFreq()));
    }

    var lookedMovie = HotelBox.dbConnection.getMoviesOrderViewed();
    Label[] lookedLabels = {viewedMovieOne, viewedMovieTwo, viewedMovieThree,
        viewedMovieFour, viewedMovieFive};
    for (var i = 0; i < lookedLabels.length && i < lookedMovie.size(); i++) {
      lookedLabels[i].setText(String.format("%s (%d)", lookedMovie.get(i).getTitle(), lookedMovie.get(i).getViewed()));
    }

    var viewedActors = HotelBox.dbConnection.getActorsOrderViewed();
    Label[] actorLabels = {viewedActorOne, viewedActorTwo, viewedActorThree,
        viewedActorFour, viewedActorFive};
    for (var i = 0; i < actorLabels.length && i < viewedActors.size(); i++) {
      actorLabels[i].setText(String.format("%s (%d)", viewedActors.get(i).getName(), viewedActors.get(i).getViewed()));
    }

    var uniqueWatched = HotelBox.dbConnection.getTopRented();
    Label[] watchedLabels = {watchedMovieOne, watchedMovieTwo, watchedMovieThree,
        watchedMovieFour, watchedMovieFive};
    for (var i = 0; i < watchedLabels.length && i < uniqueWatched.size(); i++) {
      watchedLabels[i].setText(String.format("%s (%d)", movieMap.get(uniqueWatched.get(i).getMovieId()),
          uniqueWatched.get(i).getTotal()));
    }

    // get ratings to put in their respective labels
    var topRatings = HotelBox.dbConnection.getTopFiveMovies();
    Label[] topLabels = {ratedMovieOne, ratedMovieTwo, ratedMovieThree,
        ratedMovieFour, ratedMovieFive};
    for (var i = 0; i < topLabels.length && i < topRatings.size(); i++) {
      topLabels[i].setText(String.format("%s (%.1f)",
          movieMap.get(topRatings.get(i).getMovieId()),
          topRatings.get(i).getAverage()));
    }

    // get ratings to put in their respective labels
    var bottomRatings = HotelBox.dbConnection.getBottomFiveMovies();
    Label[] bottomLabels = {bottomRatedOne, bottomRatedTwo, bottomRatedThree,
        bottomRatedFour, bottomRatedFive};
    for (var i = 0; i < bottomLabels.length && i < bottomRatings.size(); i++) {
      bottomLabels[i].setText(String.format("%s (%.1f)",
          movieMap.get(bottomRatings.get(i).getMovieId()),
          bottomRatings.get(i).getAverage()));
    }

    // get ratings to put in their respective labels
    numMovies.setText(Integer.toString(HotelBox.dbConnection.getCount("movies")));

    // get ratings to put in their respective labels
    numCustomers.setText(Integer.toString(HotelBox.dbConnection.getCount("customers")));

    // get ratings to put in their respective labels
    numActors.setText(Integer.toString(HotelBox.dbConnection.getCount("actors")));
  }
}
