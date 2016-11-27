import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Login page controller
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

  public void initialize(URL url, ResourceBundle rsBundle) {
    BufferedImage bf = null;
    try {
      bf = ImageIO.read(LoginPageController.class.getResourceAsStream(
          "/images/logo.png"));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    imageView.setImage(SwingFXUtils.toFXImage(Scalr.resize(bf, WIDTH, HEIGHT),
        null));
  }

  public void verifyLoginCredentials() {
    String username = this.username.getText();
    String password = this.password.getText();

    ResultSet rs = HotelBox.dbConnection.searchStatement(String.format("SELECT"
        + " * FROM CUSTOMER WHERE CUSTOMER.CUSTOMER_NAME = '%s' AND CUSTOMER"
        + ".CUSTOMER_PASSWORD = '%s'", username, password), true);
    try {
      if (rs.next()) {
        HotelBox.setCurrentUserId(rs.getString("CUSTOMER_ID"));
        setAdminStatus(rs);
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
    int adminValue = Integer.parseInt(rs.getString("CUSTOMER_ADMIN"));
    boolean admin = (adminValue != 0);
    HotelBox.setIsAdmin(admin);
  }
}
