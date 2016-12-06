import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
      ResultSet rs = HotelBox.dbConnection.searchStatement("CUSTOMER",
          "CUSTOMER_ID", HotBoxNavigator.editRecord);
      try {
        rs.first();
        customerName.setText(rs.getString("CUSTOMER_NAME"));
        id = rs.getString("CUSTOMER_ID");
        Double balance = Double.parseDouble(rs.getString("CUSTOMER_BALANCE"));

        customerRoom.setText(rs.getString("CUSTOMER_ROOMNUM"));
        customerBalance.setText(String.format("%.2f", balance));
        customerAddress.setText(rs.getString("CUSTOMER_ADDRESS"));
        customerCity.setText(rs.getString("CUSTOMER_CITY"));
        customerState.setText(rs.getString("CUSTOMER_STATE"));
        customerZipcode.setText(rs.getString("CUSTOMER_ZIPCODE"));
        customerPassword.setText(rs.getString("CUSTOMER_PASSWORD"));

        if (rs.getInt("CUSTOMER_ADMIN") == 1) {
          customerAdmin.setSelected(true);
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }

      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
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
          String upString = String.format("UPDATE CUSTOMER SET CUSTOMER_NAME"
                  + " = '%s', CUSTOMER_PASSWORD = '%s', CUSTOMER_BALANCE = %s,"
                  + " CUSTOMER_ROOMNUM = '%s', CUSTOMER_ADMIN = %s,"
                  + " CUSTOMER_ADDRESS = '%s', CUSTOMER_CITY = '%s',"
                  + " CUSTOMER_ZIPCODE = '%s', CUSTOMER_STATE = '%s' WHERE"
                  + " CUSTOMER_ID=%s", name, password, bal, room, admin,
              address, city, zipcode, state, id);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
        }
      });
    } else {
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
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
          String upString = String.format("INSERT INTO CUSTOMER"
                  + "(CUSTOMER_NAME, CUSTOMER_PASSWORD, CUSTOMER_BALANCE,"
                  + " CUSTOMER_ROOMNUM, CUSTOMER_ADMIN, CUSTOMER_ADDRESS,"
                  + " CUSTOMER_CITY, CUSTOMER_ZIPCODE, CUSTOMER_STATE) VALUES"
                  + " ('%s', '%s', %s, '%s', %s, '%s', '%s', '%s', '%s')",
              name, password, bal, room, admin, address, city, zipcode, state);
          HotelBox.dbConnection.updateStatement(upString);
          GeneralUtilities.showSuccessMessage();
          HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
        }
      });
    }

    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
      }
    });
  }
}
