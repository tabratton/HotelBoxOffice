package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import util.Dimensions;
import util.GeneralUtilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Displays search results.
 *
 * @author Tyler Bratton
 */
public class SearchResultsController implements Initializable {

  @FXML
  private FlowPane movieFlowPane;
  @FXML
  private FlowPane actorFlowPane;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private Map<String, String> movieKeys = new HashMap<>();
  // HashMap to store GENRE_NAME as a key and GENRE_ID as a value.
  private Map<String, String> actorKeys = new HashMap<>();


  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();

    movieFlowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    movieFlowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    actorFlowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    actorFlowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());

    var movieResults = HotelBox.dbConnection.findMovieByTerm(HotBoxNavigator.lastSearchTerm);
    var actorResults = HotelBox.dbConnection.findActorByTerm(HotBoxNavigator.lastSearchTerm);

    movieResults.forEach(movie ->
        GeneralUtilities.createButton(
            movie.getTitle(),
            movie.getImage(),
            movie.getId(),
            movieKeys,
            movieFlowPane,
            HotBoxNavigator.MOVIE_PAGE,
            new Dimensions(Dimensions.DimensionTypes.MEDIUM),
            "movies",
            HotBoxNavigator.SEARCH_RESULTS)
    );

    actorResults.forEach(actor ->
        GeneralUtilities.createButton(
            actor.getName(),
            actor.getImage(),
            actor.getId(),
            actorKeys,
            actorFlowPane,
            HotBoxNavigator.ACTOR_PAGE,
            new Dimensions(Dimensions.DimensionTypes.MEDIUM),
            "actors",
            HotBoxNavigator.SEARCH_RESULTS)
    );
  }
}
