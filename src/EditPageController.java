import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Chad Goodwin
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
        if (HotBoxNavigator.editTable.equals("CUSTOMER")){
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "CUSTOMER_NAME";
            primaryKey = "CUSTOMER_ID";
        } else if (HotBoxNavigator.editTable.equals("MOVIES")) {
            // change to movie edit page
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "MOVIE_TITLE";
            primaryKey = "MOVIE_ID";
        } else if (HotBoxNavigator.editTable.equals("GENRE")) {
            // change to Genre edit page
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "GENRE_NAME";
            primaryKey = "GENRE_ID";
        } else {
            // Edit Actors
            
            loadpage = HotBoxNavigator.CUSTOMER_EDIT;
            nameColumn = "ACTOR_NAME";
            primaryKey = "ACTOR_ID";
        }
        GeneralUtilities.createEditResults(editResults, adminResults, loadpage, nameColumn, primaryKey);
    }    
    

}
