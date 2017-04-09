import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class CustomerEditController implements Initializable {
  @FXML
  private TextField customerName;
  @FXML
  private TextField customerRoom;
  @FXML
  private TextField customerBalance;
  @FXML
  private TextField customerPassword;
  @FXML
  private TextField customerAddress;
  @FXML
  private TextField customerZipcode;
  @FXML
  private TextField customerCity;
  @FXML
  private TextField customerState;
  @FXML
  private CheckBox customerAdmin;
  @FXML
  private Button submitButton;
  @FXML
  private Button cancelButton;

  private String id;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    if (HotBoxNavigator.editRecord != null) {
      String search = String.format("SELECT * FROM customer WHERE customer_id"
              + " = %s", HotBoxNavigator.editRecord);
      ResultSet rs = HotelBox.dbConnection.searchStatement(search);
      try {
        rs.first();
        customerName.setText(rs.getString("customer_name"));
        id = rs.getString("customer_id");
        Double balance = Double.parseDouble(rs.getString("customer_balance"));

        customerRoom.setText(rs.getString("customer_roomnum"));
        customerBalance.setText(String.format("%.2f", balance));
        customerAddress.setText(rs.getString("customer_address"));
        customerCity.setText(rs.getString("customer_city"));
        customerState.setText(rs.getString("customer_state"));
        customerZipcode.setText(rs.getString("customer_zipcode"));
        customerPassword.setText(rs.getString("customer_password"));

        if (rs.getInt("customer_admin") == 1) {
          customerAdmin.setSelected(true);
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }

      submitButton.setOnAction(event -> {
        String name = customerName.getText();
        String bal = customerBalance.getText();
        String room = customerRoom.getText();
        String password = customerPassword.getText();
        String address = customerAddress.getText();
        String city = customerCity.getText();
        String zipcode = customerZipcode.getText();
        String state = customerState.getText();
        String admin;
        if (customerAdmin.isSelected()) {
          admin = "1";
        } else {
          admin = "0";
        }
        String upString = String.format("UPDATE customer SET customer_name="
                + "'%s', customer_password='%s', customer_balance=%s,"
                + " customer_roomnum='%s', customer_admin=%s,"
                + " customer_address='%s', customer_city='%s',"
                + " customer_zipcode='%s', customer_state='%s' WHERE"
                + " customer_id=%s", name, password, bal, room, admin, address,
            city, zipcode, state, id);
        HotelBox.dbConnection.updateStatement(upString);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
      });
    } else {
      submitButton.setOnAction(event -> {
        String name = customerName.getText();
        String bal = customerBalance.getText();
        String room = customerRoom.getText();
        String password = customerPassword.getText();
        String address = customerAddress.getText();
        String city = customerCity.getText();
        String zipcode = customerZipcode.getText();
        String state = customerState.getText();
        String admin;
        if (customerAdmin.isSelected()) {
          admin = "1";
        } else {
          admin = "0";
        }
        String upString = String.format("INSERT INTO customer"
                + "(customer_name, customer_password, customer_balance,"
                + " customer_roomnum, customer_admin, customer_address,"
                + " customer_city, customer_zipcode, customer_state) VALUES"
                + " ('%s', '%s', %s, '%s', %s, '%s', '%s', '%s', '%s')",
            name, password, bal, room, admin, address, city, zipcode, state);
        HotelBox.dbConnection.updateStatement(upString);
        GeneralUtilities.showSuccessMessage();
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      });
    }

    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
