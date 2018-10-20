package hotelbox;

import controllers.HotBoxController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

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
  public static final String MAIN = "../templates/HotBox.fxml";
  public static final String MOVIE_GRID = "../templates/MovieGrid.fxml";
  public static final String MOVIE_PAGE = "../templates/MoviePage.fxml";
  public static final String CUSTOMER_EDIT = "../templates/CustomerEdit.fxml";
  public static final String SEARCH_RESULTS = "../templates/SearchResults.fxml";
  public static final String ACTOR_PAGE = "../templates/ActorPage.fxml";
  public static final String ACTOR_LIST = "../templates/ActorList.fxml";
  public static final String ADMIN_PAGE = "../templates/AdminPage.fxml";
  public static final String RATE_PAGE = "../templates/RatePage.fxml";
  public static final String STATS_PAGE = "../templates/StatsPage.fxml";
  public static final String LOGIN_PAGE = "../templates/LoginPage.fxml";
  public static final String EDIT_PAGE = "../templates/EditPage.fxml";
  public static final String GENRE_EDIT = "../templates/GenreEdit.fxml";
  public static final String MOVIE_EDIT = "../templates/MovieEdit.fxml";
  public static final String CUSTOMER_INFORMATION = "../templates/CustomerInformation.fxml";
  public static final String ACTOR_EDIT = "../templates/ActorEdit.fxml";
  public static final String CASTING_EDIT = "../templates/CastingEdit.fxml";

  /**
   * Convenience static fields to determine the last clicked buttons.
   */
  public static String lastSearchTerm;
  public static Deque<String> lastLoadedPageStack = new ArrayDeque<>();
  public static Deque<String> lastClickedMovieStack = new ArrayDeque<>();
  public static Deque<String> lastClickedActorStack = new ArrayDeque<>();

  /**
   * Convenience static fields for Admin editing.
   */
  public static String editTable = null;
  public static String editRecord = null;

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
   * @param fxml the fxml file to be loaded.
   */
  public static void loadPage(String fxml) {
    try {
      mainController.setPage(FXMLLoader.load(HotBoxNavigator.class.getResource(fxml)));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Clear out the stacks used to track previously loaded things.
   */
  public static void clearStacks() {
    lastClickedActorStack.clear();
    lastClickedMovieStack.clear();
    lastLoadedPageStack.clear();
  }

}