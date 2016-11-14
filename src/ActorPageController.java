import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class.
 *
 * @author Vilma Un Jan
 */
public class ActorPageController implements Initializable {

  @FXML
  private Button goBack;

  @FXML
  private Button actorImageButton;

  @FXML
  private Label actorName;

  @FXML
  private Label actorBio;

  @FXML
  private Label listMovieLabel;

  @FXML
  private ListView<String> listView;

  /*
  * Initialize controller class
  */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    ResultSet actorPage = HotelBox.dbConnection.searchStatement("ACTORS",
        "ACTOR_ID", HotBoxNavigator.lastClickedActor);


    ResultSet movieList = HotelBox.dbConnection.searchStatement("MOVIE_TITLE",
        "MOVIES", "CASTING", "ACTORS", "ACTOR_ID",
        HotBoxNavigator.lastClickedActor, "MOVIE_ID", "ACTOR_ID");

    try {
      actorPage.first();
      final String name = actorPage.getString("ACTOR_NAME");
      final String actorImage = actorPage.getString("ACTOR_IMAGE");
      final String bio = "Biography: " + actorPage.getString(
          "ACTOR_BIO");
      final String movieLabel = actorPage.getString("ACTOR_NAME") + " movies:";

      /// final ArrayList<String> movies = new ArrayList<String>();
      //  try {
      movieList.last();
      int numRows = movieList.getRow();
      movieList.first();

      //Add movies to the list
      for (int i = 0; i < numRows; i++) {
        String movieName = movieList.getString("MOVIE_TITLE");
        //  movies.add(movieName);
        listView.getItems().add(movieName);
        movieList.next();
      }

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

      actorImageButton.setGraphic(GeneralUtilities.getImage(actorImage, 300,
          450));
      actorImageButton.setStyle("-fx-background-color: transparent;");

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
