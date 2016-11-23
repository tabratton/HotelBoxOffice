import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

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
  private Button actorImageButton;
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
      
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());

    ResultSet actorPage = HotelBox.dbConnection.searchStatement("ACTORS",
        "ACTOR_ID", HotBoxNavigator.lastClickedActor);

        ResultSet movieList = HotelBox.dbConnection.searchStatement("MOVIES",
                "CASTING", "ACTORS",HotBoxNavigator.lastClickedActor,
                "MOVIE_ID", "ACTOR_ID");
  
    try {
      actorPage.first();
      final String name = actorPage.getString("ACTOR_NAME");
      final String actorImage = actorPage.getString("ACTOR_IMAGE");
      final String bio = "Biography: " + actorPage.getString(
          "ACTOR_BIO");
      final String movieLabel = actorPage.getString("ACTOR_NAME") + " movies:";
      
      int actorViewed = actorPage.getInt("TIMES_VIEWED");
      actorViewed +=1;
      String updateViewed = String.format("Update ACTORS SET TIMES_VIEWED = %s Where ACTOR_ID = %s"
              , actorViewed,  HotBoxNavigator.lastClickedActor);
      HotelBox.dbConnection.updateStatement(updateViewed);
      
      GeneralUtilities.createButtons(movieList, titleKeys, flowPane, HotBoxNavigator
            .MOVIE_PAGE, 100, 150, "MOVIE_TITLE",
            "MOVIE_IMAGE", "MOVIE_ID", HotBoxNavigator.ACTOR_PAGE);
      
      /// final ArrayList<String> movies = new ArrayList<String>();
      //  try {
      movieList.last();
      int numRows = movieList.getRow();
      movieList.first();


      //  }catch (SQLException ex){
      //      System.out.println(ex.getMessage());
      //  }

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
          HotBoxNavigator.loadPage(HotBoxNavigator.lastPageLoaded);
        }
      });

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
