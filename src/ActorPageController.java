import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

  private static final int IMAGE_WIDTH = 300;
  private static final int IMAGE_HEIGHT = 450;

  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<String, String>();

  /*
  * Initialize controller class
  */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    flowPane.prefWidth(250);
    String lastActor = HotBoxNavigator.lastClickedActorStack.peek();
    ResultSet actorPage = HotelBox.dbConnection.searchStatement("ACTORS",
        "ACTOR_ID", lastActor);
    ResultSet movieList = HotelBox.dbConnection.searchStatement("MOVIES",
        "CASTING", "ACTORS", lastActor, "MOVIE_ID", "ACTOR_ID");
    try {
      actorPage.first();
      final String name = actorPage.getString("ACTOR_NAME");
      final String actorImage = actorPage.getString("ACTOR_IMAGE");
      final String bio = "Biography: " + actorPage.getString("ACTOR_BIO");
      final String movieLabel = actorPage.getString("ACTOR_NAME") + " movies:";

      GeneralUtilities.createButtons(movieList, titleKeys, flowPane,
          HotBoxNavigator.MOVIE_PAGE, 100, 150, "MOVIE_TITLE",
          "MOVIE_IMAGE", "MOVIE_ID", HotBoxNavigator.ACTOR_PAGE);

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

      imageView.setImage(GeneralUtilities.getImage(actorImage, IMAGE_WIDTH,
          IMAGE_HEIGHT).getImage());

      goBack.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
        }
      });
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
