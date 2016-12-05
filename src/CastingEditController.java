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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Chad
 */
public class CastingEditController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button submitButton;
    @FXML
    private ComboBox<String> moviePicker;
    @FXML
    private ComboBox<String> actorPicker;

    private int countMovies=0;
    private int countActors = 0;
    private String id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet movies = HotelBox.dbConnection.searchStatement("MOVIES");
        try{
            ObservableList<String> options = FXCollections.observableArrayList();
            while (movies.next()){
                
                String movieName = movies.getString("MOVIE_TITLE");
                options.add(movieName);
                countMovies++;
            }
            moviePicker.setItems(options);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        ResultSet actors = HotelBox.dbConnection.searchStatement("ACTORS");
        try{
            ObservableList<String> options = FXCollections.observableArrayList();
            while (actors.next()){
                
                String actorName = actors.getString("ACTOR_NAME");
                options.add(actorName);
                countActors++;
            }
            actorPicker.setItems(options);
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        if (HotBoxNavigator.editRecord!=null) {
      System.out.println(HotBoxNavigator.editRecord);
            ResultSet rs = HotelBox.dbConnection.searchStatement("CASTING",
              "CASTING_ID", HotBoxNavigator.editRecord);

            try {
                rs.first();
                
                id = rs.getString("CASTING_ID");
                
                moviePicker.getSelectionModel().select(rs.getInt("MOVIE_ID")-1);
                actorPicker.getSelectionModel().select(rs.getInt("ACTOR_ID")-1);
                
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                 
                    
                    int movie=1;
                    for (int i = 1; i<=countMovies; i++){
                        if (moviePicker.getSelectionModel().isSelected(i)){
                            movie = i+1;
                        }
                    }
                    
                    int actor=1;
                    for (int i = 1; i<=countActors; i++){
                        if (actorPicker.getSelectionModel().isSelected(i)){
                            actor = i+1;
                        }
                    }
                    
                    
                    String upString = String.format("UPDATE CASTING SET"
                    + "  MOVIE_ID=%s, ACTOR_ID=%s"
                    + " WHERE MOVIE_ID=%s", movie, actor, id);
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
                
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        
        } else {
         
            submitButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                 
                    
                    int movie=1;
                    for (int i = 1; i<=countMovies; i++){
                        if (moviePicker.getSelectionModel().isSelected(i)){
                            movie = i+1;
                        }
                    }
                    
                    int actor=1;
                    for (int i = 1; i<=countActors; i++){
                        if (actorPicker.getSelectionModel().isSelected(i)){
                            actor = i+1;
                        }
                    }
                    
                    
                    String upString = String.format("INSERT INTO CASTING VALUES"
            + " (null,%s,%s)", movie, actor);
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
        });
    }    
    
}
