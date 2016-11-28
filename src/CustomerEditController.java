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
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class.
 *
 * @author chad
 */

public class CustomerEditController implements Initializable {

  /**
   * Initializes the controller class.
   */
  
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
  private CheckBox customerAdmin;
  @FXML
  private Button submitButton;
  @FXML
  private Button cancelButton;

  
  private String id;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    
      if (HotBoxNavigator.editRecord!=null) {
      
        ResultSet rs = HotelBox.dbConnection.searchStatement("CUSTOMER",
          "CUSTOMER_ID", HotBoxNavigator.editRecord);
        
      try {
          rs.first();
          customerName.setText(rs.getString("CUSTOMER_NAME"));
          id = rs.getString("CUSTOMER_ID");
          Double fuck = Double.parseDouble(rs.getString("CUSTOMER_BALANCE"));
          System.out.println(fuck);
          
          customerRoom.setText(rs.getString("CUSTOMER_ROOMNUM"));
          customerBalance.setText(String.format("%.2f", fuck));

      } catch (SQLException ex) {
        // error handling
         System.out.println(ex.getMessage());
      }

    
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
              public void handle(ActionEvent event) {
                  if (HotBoxNavigator.editRecord.equals(null)){
                    String name = customerName.getText();
                    String bal = customerBalance.getText();
                    String room = customerRoom.getText();
                    String password = customerPassword.getText();
                    String upString = String.format("INSERT INTO CUSTOMER VALUES"
                        + " (null,%s,%s,%s,%s)", name, password, bal, room);
                    Connection con = HotelBox.dbConnection.getCon();
                    try {
                        PreparedStatement stm;
                        stm = con.prepareStatement(upString);
                        stm.executeQuery();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                  } else {
                        String name = customerName.getText();
                        String bal = customerBalance.getText();
                        String room = customerRoom.getText();
                        String upString = String.format("UPDATE CUSTOMER SET"
                            + " CUSTOMER_NAME=%s, CUSTOMER_BALANCE=%s, CUSTOMER_ROOMNUM=%s"
                            + " WHERE CUSTOMER_ID=%s", name, bal, room, id);
                        Connection con = HotelBox.dbConnection.getCon();
                        try {
                            PreparedStatement stm;
                            stm = con.prepareStatement(upString);
                            stm.executeQuery();
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                  }
                
              }
            }
      );
    }

    
    cancelButton.setOnAction( new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            HotBoxNavigator.editRecord = null;

            customerName.setText("");
            customerBalance.setText("");
            customerRoom.setText("");
            customerPassword.setText("");
          }
        }
    );
  }


}
