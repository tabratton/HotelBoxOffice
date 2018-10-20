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
 * FXML Controller class.
 *
 * @author Vilma Un Jan
 */
public class ActorListController implements Initializable {

  @FXML
  private FlowPane flowPane;
  // HashMap to store ACTOR_NAME as a key and ACTOR_ID as a value.
  private Map<String, String> nameKeys = new HashMap<>();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HotBoxNavigator.clearStacks();
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());

    var actorsList = HotelBox.dbConnection.getActors();

    actorsList.forEach(actor ->
        GeneralUtilities.createButton(
            actor.getName(),
            actor.getImage(),
            actor.getId(),
            nameKeys,
            flowPane,
            HotBoxNavigator.ACTOR_PAGE,
            new Dimensions(Dimensions.DimensionTypes.MEDIUM),
            "actors",
            HotBoxNavigator.ACTOR_LIST)
    );
  }
}
