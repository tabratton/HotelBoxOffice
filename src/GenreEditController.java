/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Chad
 */
public class GenreEditController implements Initializable {

    @FXML
    private TextField genreName;
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    
    private String id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        if (HotBoxNavigator.editRecord!=null) {
      
        ResultSet rs = HotelBox.dbConnection.searchStatement("GENRE",
          "GENRE_ID", HotBoxNavigator.editRecord);
        try{
            rs.first();
            id = rs.getString("GENRE_ID");
            genreName.setText(rs.getString("GENRE_NAME"));
            
        }   catch (SQLException ex) {
        // error handling
                 System.out.println(ex.getMessage());
            }
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String genre = genreName.getText();
                String upString = String.format("UPDATE GENRE SET GENRE_NAME='%s' "
                        + " WHERE GENRE_ID=%s", genre, id);
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
            
            });
        
        } else {
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String genre = genreName.getText();
                String upString = String.format("INSERT INTO GENRE VALUES"
                + "(null,'%s')", genre);
                Connection con = HotelBox.dbConnection.getCon();

                try {
                    PreparedStatement stm;
                    stm = con.prepareStatement(upString);
                    stm.executeUpdate();
                    HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE);

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
                
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
