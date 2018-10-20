package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import util.GeneralUtilities;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class EditPageController implements Initializable {

  @FXML
  private FlowPane adminResults;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    adminResults.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    adminResults.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    switch (HotBoxNavigator.editTable) {
      case "customers":
        // change to customer edit page
        var loadpage = HotBoxNavigator.CUSTOMER_EDIT;
        var primaryKey = "customer_id";
        var customers = HotelBox.dbConnection.getCustomers();
        customers.forEach(customer ->
            GeneralUtilities.createEditResults(
                customer.getCustomerName(),
                customer.getCustomerId(),
                adminResults,
                loadpage,
                primaryKey
            )
        );
        break;
      case "movies":
        // change to movie edit page
        loadpage = HotBoxNavigator.MOVIE_EDIT;
        primaryKey = "movie_id";
        var movies = HotelBox.dbConnection.getMovies();
        movies.forEach(movie ->
            GeneralUtilities.createEditResults(
                movie.getTitle(),
                movie.getId(),
                adminResults,
                loadpage,
                primaryKey
            )
        );
        break;
      case "genres":
        // change to genre edit page
        loadpage = HotBoxNavigator.GENRE_EDIT;
        primaryKey = "genre_id";
        var genres = HotelBox.dbConnection.getGenres();
        genres.forEach(genre ->
            GeneralUtilities.createEditResults(
                genre.getGenreName(),
                genre.getGenreId(),
                adminResults,
                loadpage,
                primaryKey
            )
        );
        break;
      case "actors":
        // change to actor edit page
        loadpage = HotBoxNavigator.ACTOR_EDIT;
        primaryKey = "actor_id";
        var actors = HotelBox.dbConnection.getActors();
        actors.forEach(actor ->
            GeneralUtilities.createEditResults(
                actor.getName(),
                actor.getId(),
                adminResults,
                loadpage,
                primaryKey
            )
        );
        break;
      default:
        break;
    }
  }
}
