import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Vilma Un Jan
 */
public class ActorPageController implements Initializable {
  @FXML
  private FlowPane flowPane;
  @FXML
  private Button goBack;
  @FXML
  private ImageView imageView;
  @FXML
  private Label actorName;
  @FXML
  private Label actorBio;
  @FXML
  private Label listMovieLabel;

  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<>();

  /*
  * Initialize controller class
  */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    flowPane.prefWidth(250);
    String lastActor = HotBoxNavigator.lastClickedActorStack.peek();
    String search = String.format("SELECT * FROM actors WHERE actor_id=%s",
        lastActor);
    ResultSet actorPage = HotelBox.dbConnection.searchStatement(search);
    search = String.format("SELECT movies.movie_id, movies.movie_title,"
        + " movies.movie_image FROM movies INNER JOIN (SELECT movie_id FROM"
        + " casting INNER JOIN actors ON casting.actor_id=actors.actor_id"
        + " WHERE actors.actor_id=%s) cast ON cast.movie_id=movies.movie_id",
        lastActor);
    ResultSet movieList = HotelBox.dbConnection.searchStatement(search);
    try {
      actorPage.first();
      final String name = actorPage.getString("actor_name");
      final String actorImage = actorPage.getString("actor_image");
      final String bio = "Biography: " + actorPage.getString("actor_bio");
      final String movieLabel = actorPage.getString("actor_name") + " movies:";

      GeneralUtilities.createButtons(movieList, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.SMALL), "MOVIES",
          HotBoxNavigator.ACTOR_PAGE);

      actorName.setWrapText(true);
      actorName.setTextAlignment(TextAlignment.CENTER);
      actorName.setFont(new Font(30.0));
      actorName.setText(name);

      actorBio.setWrapText(true);
      actorBio.setTextAlignment(TextAlignment.CENTER);
      actorBio.setText(bio);

      listMovieLabel.setWrapText(true);
      listMovieLabel.setTextAlignment(TextAlignment.CENTER);
      listMovieLabel.setText(movieLabel);

      imageView.setImage(GeneralUtilities.getImage(actorImage,
          new Dimensions(Dimensions.DimensionTypes.LARGE),"ACTORS",
          lastActor).getImage());

      goBack.setOnAction(event -> {
        HotBoxNavigator.lastClickedActorStack.pop();
        HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
      });
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
