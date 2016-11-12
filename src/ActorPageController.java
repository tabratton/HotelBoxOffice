import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Vilma Un Jan
 */
public class ActorPageController implements Initializable {

    @FXML
    private Button goBack;
    
    @FXML
    private Label actorName;
  
    /*
    * Initialize controller class
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ResultSet actorPage = HotelBox.dbConnection.searchStatement("ACTORS",
                "ACTOR_ID", HotBoxNavigator.lastClickedActor);
        
        try {
            actorPage.first();
            final String name = actorPage.getString("ACTOR_NAME");
            final String actorImage = actorPage.getString("ACTOR_IMAGE");
            final String actorBio = "Biography: " + actorPage.getString( 
                    "ACTOR_BIO");
        
            actorName.setWrapText(true);
            actorName.setTextAlignment(TextAlignment.CENTER);
            actorName.setText(name);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
        
        
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                HotBoxNavigator.loadPage(HotBoxNavigator.ACTOR_LIST);
            }
        });
    }    
    
}
