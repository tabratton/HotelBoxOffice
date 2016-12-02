/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.swing.ButtonGroup;

/**
 * FXML Controller class
 *
 * @author gabrielguillen
 */
public class RatePageController implements Initializable {
    
    @FXML
    private RadioButton one = new RadioButton(Integer.toString(1));
    
    @FXML
    private RadioButton two = new RadioButton(Integer.toString(2));;
    
    @FXML
    private RadioButton three = new RadioButton(Integer.toString(3));;
    
    @FXML
    private RadioButton four = new RadioButton(Integer.toString(4)); 
    
    @FXML
    private RadioButton five = new RadioButton(Integer.toString(5));;
    
    @FXML
    private Button submit;
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          final ToggleGroup group = new ToggleGroup();
          one.setToggleGroup(group);
          two.setToggleGroup(group);
          three.setToggleGroup(group);
          four.setToggleGroup(group);
          five.setToggleGroup(group);

    
    
    }    
    
}

}
