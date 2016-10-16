import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// TODO: Make logic related to database, need database first.

public class MovieGridController implements Initializable {
  @FXML
  private GridPane pane;
  public static String lastClickedMovie;

  public void selectMovie(ActionEvent event) {
    Button button = (Button) event.getSource();

    lastClickedMovie = button.getText();

  }

  public void initialize(URL url, ResourceBundle bundle) {
    ArrayList<Button> buttons = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      buttons.add(new Button(String.format("%d", i)));
      buttons.get(i).setContentDisplay(ContentDisplay.TOP);
      buttons.get(i).setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          Button button = (Button) event.getSource();
          lastClickedMovie = button.getText();
        }
      });

      Image image = new Image("PLACEHOLDER.jpg");

      buttons.get(i).setGraphic(new ImageView(image));
    }

    int counter = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 5; j++, counter++) {
        pane.add(buttons.get(counter), j, i);
      }
    }
  }
}
