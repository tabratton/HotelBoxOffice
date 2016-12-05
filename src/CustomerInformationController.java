import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Customer information controller class
 *
 * @author Tyler Bratton
 */
public class CustomerInformationController implements Initializable {
  private static final int IMAGE_HEIGHT = 180;
  private static final int IMAGE_WIDTH = 120;
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<String, String>();
  @FXML
  private FlowPane flowPane;
  @FXML
  private Label name;
  @FXML
  private Label address;
  @FXML
  private Label cityAndState;
  @FXML
  private Label zipcode;
  @FXML
  private Label balance;
  @FXML
  private Label room;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    flowPane.prefWidthProperty().bind(HotelBox.testStage.widthProperty());
    flowPane.prefHeightProperty().bind(HotelBox.testStage.heightProperty());
    createRentedList();
    ResultSet customer = HotelBox.dbConnection.searchStatement(String
        .format("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = %s",
            HotelBox.getCurrentUserId()),true);
    try {
      customer.first();
      name.setText(customer.getString("CUSTOMER_NAME"));
      address.setText(customer.getString("CUSTOMER_ADDRESS"));
      cityAndState.setText(customer.getString("CUSTOMER_CITY") + ", " +
          customer.getString("CUSTOMER_STATE"));
      zipcode.setText(customer.getString("CUSTOMER_ZIPCODE"));
      double currentBalance = Double.parseDouble(customer.getString
          ("CUSTOMER_BALANCE"));
      balance.setText(String.format("$%.2f", currentBalance));
      room.setText(customer.getString("CUSTOMER_ROOMNUM"));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

  }

  private void createRentedList() {
    ResultSet movieList = HotelBox.dbConnection.searchStatement(String.format
        ("SELECT CUSTOMER_RENTALS.RENTAL_DATE, MOVIES.MOVIE_ID, MOVIES"
                + ".MOVIE_TITLE, MOVIES.MOVIE_IMAGE FROM CUSTOMER_RENTALS INNER"
                + " JOIN MOVIES ON CUSTOMER_RENTALS.MOVIE_ID = MOVIES"
                + ".MOVIE_ID AND CUSTOMER_RENTALS.CUSTOMER_ID = %s ORDER BY"
                + " CUSTOMER_RENTALS.RENTAL_DATE DESC",
            HotelBox.getCurrentUserId()), true);
    GeneralUtilities.createButtons(movieList, titleKeys, flowPane,
        HotBoxNavigator.MOVIE_PAGE, IMAGE_WIDTH, IMAGE_HEIGHT, "MOVIE_TITLE",
        "MOVIE_IMAGE", "MOVIE_ID", HotBoxNavigator.MOVIE_PAGE);
    ObservableList<Node> list = flowPane.getChildren();
    try {
      movieList.first();
      for (Node node : list) {
        try {
          DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
          Date date = isoDate.parse(movieList.getString("RENTAL_DATE"));
          Button button = (Button) node;
          button.setPrefSize(button.getPrefWidth(), button.getPrefHeight() +
              50);
          button.setText(button.getText() + "\nRented: " + isoDate.format(date));
        } catch (ParseException ex) {
          System.out.println(ex.getMessage());
        }
        movieList.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
