/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author gabrielguillen
 */
public class MoviePageController 
        implements Initializable {
    
    @FXML
    private ListView<String> listView;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value
    HashMap<String, String> titleKeys = new HashMap<String, String>();
    // Initialize database connection
    DatabaseConnection con = new DatabaseConnection();
    // Get the MOVIE_ID, MOVIE_TITLE, and MOVIE_IMAGE columns from the MOVIES
    // table
    ResultSet rs = con.searchStatement("MOVIES","MOVIE_ID",
            HotBoxNavigator.lastClickedMovie);

    
        try{ 
                String director = rs.getString("MOVIE_DIRECTOR");
                String description = rs.getString("MOVIE_DESCRIPTION");
                String releaseDate = rs.getString("MOVIE_RELEASE_DATE");
            //Things for the ListView
            ObservableList<String> data = FXCollections.observableArrayList(director
                    , description, releaseDate);
            listView.setItems(data);
         }catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    }    
    
    
    
}
