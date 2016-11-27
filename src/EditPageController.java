/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Chad
 */
public class EditPageController implements Initializable {

    @FXML
    private FlowPane adminResults;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String loadpage;
        String nameColumn;
        String primaryKey;
        ResultSet editResults = HotelBox.dbConnection.searchStatement(HotBoxNavigator.editTable);
        if (HotBoxNavigator.editTable == "CUSTOMER"){
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "CUSTOMER_NAME";
            primaryKey = "CUSTOMER_ID";
        } else if (HotBoxNavigator.editTable == "MOVIES") {
            // change to movie edit page
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "MOVIE_TITLE";
            primaryKey = "MOVIE_ID";
        } else if (HotBoxNavigator.editTable == "GENRE") {
            // change to Genre edit page
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "GENRE_NAME";
            primaryKey = "GENRE_ID";
        } else {
            // Edit Actors coming soon
            
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "CUSTOMER_NAME";
            primaryKey = "CUSTOMER_ID";
        }
        GeneralUtilities.createEditResults(editResults, adminResults, loadpage, nameColumn, primaryKey);
    }    
    
}
