import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Stack;

/**
 * Utility class for controlling navigation between views.
 *
 * <p>All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 *
 * @author Tyler Bratton
 */
public class HotBoxNavigator {

  /**
   * Convenience constants for fxml layouts managed by the navigator.
   */
  public static final String MAIN = "HotBox.fxml";
  public static final String MOVIE_GRID = "MovieGrid.fxml";
  public static final String MOVIE_PAGE = "MoviePage.fxml";
  public static final String CUSTOMER_EDIT = "CustomerEdit.fxml";
  public static final String SEARCH_RESULTS = "SearchResults.fxml";
  public static final String ACTOR_PAGE = "ActorPage.fxml";
  public static final String ACTOR_LIST = "ActorList.fxml";
  public static final String ADMIN_PAGE = "AdminPage.fxml";
  public static final String RATE_PAGE = "RatePage.fxml";
  public static final String STATS_PAGE = "StatsPage.fxml";


  /**
   * Convenience static fields to determine the last clicked buttons.
   */
  //public static String lastClickedMovie;
  public static String lastClickedActor;
  //public static String lastPageLoaded;
  public static String lastSearchTerm;
  public static Stack<String> lastLoadedPageStack = new Stack<>();
  public static Stack<String> lastClickedMovieStack = new Stack<>();
  public static Stack<String> lastClickedActorStack = new Stack<>();

  /**
   * The main application layout controller.
   */
  private static HotBoxController mainController;

  /**
   * Stores the main controller for later use in navigation tasks.
   *
   * @param mainController the main application layout controller.
   */
  public static void setMainController(HotBoxController mainController) {
    HotBoxNavigator.mainController = mainController;
  }

  /**
   * Loads the view specified by the fxml file into the
   * hotBoxHolder pane of the main application layout.
   *
   * <p>Previously loaded view for the same fxml file are not cached.
   * The fxml is loaded anew and a new view node hierarchy generated
   * every time this method is invoked.
   *
   * <p>A more sophisticated load function could potentially add some
   * enhancements or optimizations, for example:
   * cache FXMLLoaders
   * cache loaded view nodes, so they can be recalled or reused
   * allow a user to specify view node reuse or new creation
   * allow back and forward history like a browser
   *
   * @param fxml the fxml file to be loaded.
   */
  public static void loadPage(String fxml) {
    try {
      mainController.setPage(FXMLLoader.load(HotBoxNavigator.class
          .getResource(fxml)));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

}
