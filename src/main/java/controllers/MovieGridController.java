package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import models.Genre;
import models.Movie;
import util.Dimensions;
import util.GeneralUtilities;

import java.net.URL;
import java.util.*;


/**
 * Controller for the MovieGrid.fxml.
 *
 * <p>Gets list of all movies from the database and then constructs buttons
 * that load an individual information page for each movie.
 *
 * @author Tyler Bratton
 */
public class MovieGridController implements Initializable {
  // FlowPane object that allows us to modify the FlowPane defined in the
  // fxml dynamically.
  @FXML
  private FlowPane flowPane;
  @FXML
  private ChoiceBox<String> choiceBox;
  @FXML
  private Button recentMovies;
  @FXML
  private Button popularMovies;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private Map<String, String> titleKeys = new HashMap<>();
  // Stores the current/last selected genre in the movie grid page.
  private static String currentSelectedGenreName = "All";
  // Determines if the page was loaded by the navigation bar.
  public static boolean loadedByNavigationBarButton;
  public static boolean newReleasesLastLoaded;
  public static boolean mostPopularLastLoaded;

  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    HotBoxNavigator.clearStacks();

    // Magic to set the preferred width and height to the current window size.
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());

    if (loadedByNavigationBarButton) {
      currentSelectedGenreName = "All";
      loadedByNavigationBarButton = false;
    }

    // Gets the data for the GENRES table.
    var genres = HotelBox.dbConnection.getGenres();
    var movies = HotelBox.dbConnection.getMovies();

    genres.sort(Comparator.comparing(Genre::getGenreName));

    createChoiceBox(genres);
    createNewReleasesButton(movies);
    createMostPopularButton(movies);

    // If the new releases view was what the user was on before clicking a
    // movie button, return them back to it when they click go back.
    removeAllButtons();
    if (!newReleasesLastLoaded && !mostPopularLastLoaded && loadedByNavigationBarButton) {
      movies.forEach(movie ->
          GeneralUtilities.createButton(
              movie.getTitle(),
              movie.getImage(),
              movie.getId(),
              titleKeys, flowPane,
              HotBoxNavigator.MOVIE_PAGE,
              new Dimensions(Dimensions.DimensionTypes.MEDIUM),
              "movies",
              HotBoxNavigator.MOVIE_GRID)
      );
    } else {
      var moviesByGenre = HotelBox.dbConnection.getMoviesByGenreName(currentSelectedGenreName);
      moviesByGenre.forEach(movie ->
          GeneralUtilities.createButton(
              movie.getTitle(),
              movie.getImage(),
              movie.getId(),
              titleKeys,
              flowPane,
              HotBoxNavigator.MOVIE_PAGE,
              new Dimensions(Dimensions.DimensionTypes.MEDIUM),
              "movies",
              HotBoxNavigator.MOVIE_GRID)
      );
    }
  }

  private void createChoiceBox(List<Genre> genres) {
    choiceBox.setTooltip(new Tooltip("Filter by genre"));

    // Stores the genre names in the order they appear in the choice box
    var genreList = new ArrayList<String>();
    genres.add(0, new Genre("", "All"));

    // Add all genres to the choice box.
    genres.forEach(genre -> {
      var genreName = genre.getGenreName();
      genreList.add(genreName);
      choiceBox.getItems().add(genreName);
    });

    // Make the first selection (All) the default.
    choiceBox.getSelectionModel().select(genreList.indexOf(currentSelectedGenreName));
    choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
      int currentSelection = newValue.intValue();
      var currentGenre = genreList.get(currentSelection);
      currentSelectedGenreName = currentGenre;

      var movies = HotelBox.dbConnection.getMoviesByGenreName(currentGenre);
      // Clear the display of movies
      removeAllButtons();
      // Create new grid with current selection of genre
      movies.forEach(movie -> GeneralUtilities.createButton(
          movie.getTitle(),
          movie.getImage(),
          movie.getId(),
          titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.MEDIUM),
          "movies",
          HotBoxNavigator.MOVIE_GRID)
      );

      newReleasesLastLoaded = false;
      mostPopularLastLoaded = false;
    });
  }

  private void removeAllButtons() {
    // Remove all buttons from pane so they can be redrawn.
    flowPane.getChildren().clear();
  }

  private void createNewReleasesButton(List<Movie> newReleases) {
    recentMovies.setTooltip(new Tooltip("Filter by new releases"));
    recentMovies.setOnAction(event -> {
      newReleasesLastLoaded = true;
      mostPopularLastLoaded = false;
      changeMovieDisplay(newReleases);
    });
  }

  private void createMostPopularButton(List<Movie> mostPopular) {
    popularMovies.setTooltip(new Tooltip("Filter by most popular"));
    popularMovies.setOnAction(event -> {
      mostPopularLastLoaded = true;
      newReleasesLastLoaded = false;
      changeMovieDisplay(mostPopular);
    });
  }

  private void changeMovieDisplay(List<Movie> movies) {
    // Reset choice box back to all
    choiceBox.getSelectionModel().select("All");
    // Clear the display of movies
    removeAllButtons();
    if (newReleasesLastLoaded) {
      movies.sort(Comparator.comparing(Movie::getReleaseDate));
    } else {
      movies.sort(Comparator.comparing(Movie::getViewed));
    }

    getSubList(movies).forEach(movie ->
        GeneralUtilities.createButton(
            movie.getTitle(),
            movie.getImage(),
            movie.getId(),
            titleKeys,
            flowPane,
            HotBoxNavigator.MOVIE_PAGE,
            new Dimensions(Dimensions.DimensionTypes.MEDIUM),
            "movies",
            HotBoxNavigator.MOVIE_GRID)
    );
  }

  private List<Movie> getSubList(List<Movie> movies) {
    return movies.subList(0, 10);
  }
}
