/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author chad
 */

public class CustomerEditController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML // initialize all named objects in fxml
    private TextField cID;
    private TextField cName;
    private TextField cRoom;
    private TextField cBalance;
    private TextField cPassword;
    private TextField cConfirm;
    private Button submitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DBConnection con = new DBConnection();
        String value = "1";
        if (value == "2"){
            ResultSet rs = con.searchStatement("CUSTOMER", "CUSTOMER_ID", value);
            try {
                while (rs.next()){
                    cID.setText(rs.getString("CUSTOMER_ID"));
                    cName.setText(rs.getString("CUSTOMER_NAME"));
                    cBalance.setText(rs.getString("CUSTOMER_BALANCE"));
                    cRoom.setText(rs.getString("CUSTOMER_ROOMNUM"));

                }
            } catch (SQLException e){
                // error handling
            }
        }
        submitButton.setOnAction( 

            new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String name = cName.getText();
                String bal = cBalance.getText();
                String room = cRoom.getText();
                String ID = cID.getText();
                String upString =String.format( "UPDATE CUSTOMER SET CUSTOMER_NAME=%s,"
                        + " CUSTOMER_BALANCE=%s, CUSTOMER_ROOMNUM=%s"
                        + "WHERE CUSTOMER_ID=%s", name, bal, room, ID);
            }
        }
        );
    }    
    

}
