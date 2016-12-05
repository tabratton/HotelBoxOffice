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
public class ActorEditController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField actorName;
    @FXML
    private TextField actorImage;
    @FXML
    private TextField actorBio;
    @FXML
    private TextField timesViewed;
    private String id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        if (HotBoxNavigator.editRecord!=null) {
      
        ResultSet rs = HotelBox.dbConnection.searchStatement("ACTORS",
          "ACTOR_ID", HotBoxNavigator.editRecord);
        
      try {
          rs.first();
          actorName.setText(rs.getString("ACTOR_NAME"));
          id = rs.getString("ACTOR_ID");
          actorImage.setText(rs.getString("ACTOR_IMAGE"));
          actorBio.setText(rs.getString("ACTOR_BIO"));
          timesViewed.setText(rs.getString("TIMES_VIEWED"));
          
          
      } catch (SQLException ex) {
        // error handling
         System.out.println(ex.getMessage());
      }
        
      submitButton.setOnAction(new EventHandler<ActionEvent>() {
              public void handle(ActionEvent event) {
                  
                  String name = actorName.getText();
                  String image = actorImage.getText();
                  String bio = actorBio.getText();
                  String viewed = timesViewed.getText();
                  
                  
                String upString = String.format("UPDATE ACTORS SET"
                    + " ACTOR_NAME='%s', ACTOR_IMAGE='%s', ACTOR_BIO=%s, TIMES_VIEWED"
                    + " WHERE ACTOR_ID=%s", name, image, bio, viewed, id);
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
                  
                  String name = actorName.getText();
                  String image = actorImage.getText();
                  String bio = actorBio.getText();
                  String viewed = timesViewed.getText();
                  
                   
                    String upString = String.format("INSERT INTO ACTORS VALUES"
                        + " (null,'%s','%s','%s',%s)", name, image, bio, viewed);
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
        // TODO
    }    
    }
    

