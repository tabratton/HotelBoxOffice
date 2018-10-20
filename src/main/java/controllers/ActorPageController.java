package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import models.Actor;
import util.Dimensions;
import util.GeneralUtilities;

import java.net.URL;
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
    var actor = HotBoxNavigator.lastClickedActorStack.peek();
    var actorPage = HotelBox.dbConnection.getActor(actor).orElse(new Actor());
    var movieList = HotelBox.dbConnection.getMoviesByActorId(actor);

    final var name = actorPage.getName();
    final var actorImage = actorPage.getImage();
    final var bio = "Biography: " + actorPage.getBio();
    final var movieLabel = String.format("%s movies:", name);

    movieList.forEach(movie ->
        GeneralUtilities.createButton(
            movie.getTitle(),
            movie.getImage(),
            movie.getId(),
            titleKeys,
            flowPane,
            HotBoxNavigator.MOVIE_PAGE,
            new Dimensions(Dimensions.DimensionTypes.SMALL),
            "movies",
            HotBoxNavigator.ACTOR_PAGE)
    );


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
        new Dimensions(Dimensions.DimensionTypes.LARGE), "ACTORS",
        actor).getImage());

    goBack.setOnAction(event -> {
      HotBoxNavigator.lastClickedActorStack.pop();
      HotBoxNavigator.loadPage(HotBoxNavigator.lastLoadedPageStack.pop());
    });
  }
}
