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

    
      if (HotBoxNavigator.editRecord!=null) {
      
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
          if (rs.getInt("CUSTOMER_ADMIN")== 1) {
              customerAdmin.setSelected(true);
          }
          
      } catch (SQLException ex) {
        // error handling
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
                  if( customerAdmin.isSelected()) {
                      admin = "1";
                  } else {
                      admin = "0";
                  }
                  

                String upString = String.format("UPDATE CUSTOMER SET"
                    + " CUSTOMER_NAME='%s', CUSTOMER_PASSWORD='%s', CUSTOMER_BALANCE=%s, CUSTOMER_ROOMNUM='%s', CUSTOMER_ADMIN=%s, CUSTOMER_ADDRESS='%s', CUSTOMER_CITY='%s', CUSTOMER_ZIPCODE='%s', CUSTOMER_STATE='%s'"
                    + " WHERE CUSTOMER_ID=%s", name,password, bal, room, admin, address, city, zipcode, state, id);
                Connection con = HotelBox.dbConnection.getCon();

                try {
                    PreparedStatement stm;
                    stm = con.prepareStatement(upString);
                    stm.executeUpdate();
                    HotBoxNavigator.loadPage(HotBoxNavigator.EDIT_PAGE);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
              }
            }
      );
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
                  if( customerAdmin.isSelected()) {
                      admin = "1";
                  } else {
                      admin = "0";
                  }
                   
                    String upString = String.format("INSERT INTO CUSTOMER VALUES"
                        + " (null,'%s','%s',%s,'%s',%s,'%s','%s','%s','%s')", name, password, bal, room, admin, address, city, zipcode, state );
                    Connection con = HotelBox.dbConnection.getCon();
                    
                    try {
                        PreparedStatement stm;
                        stm = con.prepareStatement(upString);
                        stm.executeUpdate();
                        
                        
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                    HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
                }
            });
          }

    cancelButton.setOnAction( new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);
          }
        }
    );
  }


}
