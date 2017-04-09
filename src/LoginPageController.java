import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

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
      bf = ImageIO.read(LoginPageController.class.getResourceAsStream(
          "/images/logo.png"));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    if (bf != null) {
      imageView.setImage(SwingFXUtils.toFXImage(Scalr.resize(bf, WIDTH, HEIGHT),
          null));
    }
  }

  /**
   * Verify that the username and password entered are correct.
   */
  public void verifyLoginCredentials() {
    String username = this.username.getText();
    String password = this.password.getText();

    String search = String.format("SELECT customer_id, customer_admin FROM"
        + " customer WHERE customer.customer_name='%s' AND"
        + " customer.customer_password='%s'", username, password);
    ResultSet rs = HotelBox.dbConnection.searchStatement(search);
    try {
      if (rs.next()) {
        HotelBox.setCurrentUserId(rs.getString("customer_id"));
        setAdminStatus(rs);
        HotelBox.setIsLoggedIn(true);
        HotBoxNavigator.loadPage(HotBoxNavigator.MOVIE_GRID);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Username/Password");
        alert.setHeaderText(null);
        alert.setContentText("Your username/password is incorrect");
        alert.showAndWait();
        this.username.setText("");
        this.password.setText("");
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void setAdminStatus(ResultSet rs) throws SQLException {
    int adminValue = Integer.parseInt(rs.getString("customer_admin"));
    boolean admin = (adminValue != 0);
    HotelBox.setIsAdmin(admin);
  }
}
