package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.Customer;
import org.imgscalr.Scalr;
import util.GeneralUtilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Login page controller.
 *
 * @author Tyler Bratton
 */
public class LoginPageController implements Initializable {
  @FXML
  private ImageView imageView;
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  private static final int HEIGHT = 282;
  private static final int WIDTH = (HEIGHT * 516) / 333;

  @Override
  public void initialize(URL url, ResourceBundle rsBundle) {
    BufferedImage bf = null;
    try {
      bf = ImageIO.read(LoginPageController.class.getResourceAsStream("/assets/images/logo.png"));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    if (bf != null) {
      imageView.setImage(SwingFXUtils.toFXImage(Scalr.resize(bf, WIDTH, HEIGHT), null));
    }
  }

  /**
   * Verify that the username and password entered are correct.
   */
  public void verifyLoginCredentials() {
    var u = this.username.getText();
    var p = this.password.getText();
    if (GeneralUtilities.verifyPassword(u, p)) {
      var user = HotelBox.dbConnection.getCustomerByName(u).orElse(new Customer());
      HotelBox.setCurrentUserId(user.getCustomerId());
      HotelBox.setIsAdmin(user.isAdmin());
      HotelBox.setIsLoggedIn(true);
      MovieGridController.loadedByNavigationBarButton = true;
      HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);
    } else {
      var alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Invalid Username/Password");
      alert.setHeaderText(null);
      alert.setContentText("Your username/password is incorrect");
      alert.showAndWait();
      this.username.setText("");
      this.password.setText("");
    }
  }
}
