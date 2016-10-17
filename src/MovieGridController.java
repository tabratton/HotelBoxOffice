import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MovieGridController implements Initializable {
  @FXML
  private FlowPane pane;
  public static String lastClickedMovie;

  public void initialize(URL url, ResourceBundle bundle) {
    ArrayList<Button> buttons = new ArrayList<>();
    DBConnection con = new DBConnection();
    ResultSet rs = con.searchStatement("MOVIES");

    try {
      rs.last();
      int numRows = rs.getRow();
      rs.first();

      for (int i = 0; i < numRows; i++) {
        buttons.add(new Button(String.format("%s", rs.getString("MOVIE_TITLE")
        )));
        Button currentButton = buttons.get(i);
        currentButton.setStyle("-fx-background-color: transparent");
        currentButton.setContentDisplay(ContentDisplay.TOP);
        currentButton.setPrefSize(150, 200);
        currentButton.setWrapText(true);
        currentButton.setAlignment(Pos.TOP_CENTER);
        currentButton.setTextAlignment(TextAlignment.CENTER);
        currentButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Button button = (Button) event.getSource();
            System.out.println(button.getText());
            lastClickedMovie = button.getText();
            //HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_PAGE);
          }
        });

        ImageView imageView = new ImageView(rs.getString("MOVIE_IMAGE"));
        currentButton.setGraphic(imageView);

        pane.getChildren().add(currentButton);
        rs.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }
}
