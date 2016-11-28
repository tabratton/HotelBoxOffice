import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author chad
 */

public class CustomerEditController implements Initializable {

  /**
   * Initializes the controller class.
   */
  @FXML // initialize all named objects in fxml
  private TextField customerId;
  @FXML
  private TextField customerName;
  @FXML
  private TextField customerRoom;
  @FXML
  private TextField customerBalance;
  @FXML
  private TextField customerPassword;
  @FXML
  private TextField customerConfirm;
  @FXML
  private Button submitButton;
  @FXML
  private Button createButton;
  @FXML
  private Button cancelButton;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String value = "1";
    if (HotBoxNavigator.editRecord != null && HotBoxNavigator.editTable
        .equals("CUSTOMER")) {
      ResultSet rs = HotelBox.dbConnection.searchStatement("CUSTOMER",
          "CUSTOMER_ID", HotBoxNavigator.editRecord);
      try {
        while (rs.next()) {
          customerId.setText(rs.getString("CUSTOMER_ID"));
          customerName.setText(rs.getString("CUSTOMER_NAME"));
          customerBalance.setText(rs.getString("CUSTOMER_BALANCE"));
          customerRoom.setText(rs.getString("CUSTOMER_ROOMNUM"));

        }
      } catch (SQLException ex) {
        // error handling
      }

      submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              String name = customerName.getText();
              String bal = customerBalance.getText();
              String room = customerRoom.getText();
              String id = customerId.getText();
              String upString = String.format("UPDATE CUSTOMER SET"
                  + " CUSTOMER_NAME = '%s', CUSTOMER_BALANCE = %s,"
                  + " CUSTOMER_ROOMNUM = %s WHERE CUSTOMER_ID = %s", name,
                  bal, room, id);
              HotelBox.dbConnection.updateStatement(upString);
            }
          }
      );
    }
    createButton.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            String name = customerName.getText();
            String bal = customerBalance.getText();
            String room = customerRoom.getText();
            String password = customerPassword.getText();
            String upString = String.format("INSERT INTO CUSTOMER"
                + " (CUSTOMER_NAME, CUSTOMER_PASSWORD, CUSTOMER_BALANCE,"
                + " CUSTOMER_ROOMNUM) VALUES ('%s', '%s', %s, %s)", name,
                password, bal, room);
            HotelBox.dbConnection.updateStatement(upString);
          }
        }
    );

    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            HotBoxNavigator.editRecord = null;

            customerName.setText("");
            customerBalance.setText("");
            customerRoom.setText("");
            customerId.setText("");
            customerPassword.setText("");
            customerConfirm.setText("");
          }
        }
    );
  }


}
