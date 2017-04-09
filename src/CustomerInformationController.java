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
 * Customer information controller class.
 *
 * @author Tyler Bratton
 */
public class CustomerInformationController implements Initializable {
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private HashMap<String, String> titleKeys = new HashMap<>();
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
    String search = String.format("SELECT * FROM customer WHERE"
        + " customer_id=%s", HotelBox.getCurrentUserId());
    ResultSet customer = HotelBox.dbConnection.searchStatement(search);
    try {
      customer.first();
      name.setText(customer.getString("customer_name"));
      address.setText(customer.getString("customer_address"));
      cityAndState.setText(customer.getString("customer_city")
          + ", " + customer.getString("customer_state"));
      zipcode.setText(customer.getString("customer_zipcode"));
      double currentBalance = Double.parseDouble(customer.getString(
          "customer_balance"));
      balance.setText(String.format("$%.2f", currentBalance));
      room.setText(customer.getString("customer_roomnum"));
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void createRentedList() {
    String search = String.format("SELECT customer_rentals.rental_date,"
        + " movies.movie_id, movies.movie_title, movies.movie_image FROM"
        + " customer_rentals INNER JOIN movies ON customer_rentals"
        + ".movie_id=movies.movie_id WHERE customer_rentals.customer_id=%s"
        + " ORDER BY customer_rentals.rental_date DESC",
        HotelBox.getCurrentUserId());
    ResultSet movieList = HotelBox.dbConnection.searchStatement(search);
    GeneralUtilities.createButtons(movieList, titleKeys, flowPane,
        HotBoxNavigator.MOVIE_PAGE,
        new Dimensions(Dimensions.DimensionTypes.SMALL), "MOVIES",
        HotBoxNavigator.CUSTOMER_INFORMATION);
    ObservableList<Node> list = flowPane.getChildren();
    try {
      movieList.first();
      for (Node node : list) {
        try {
          DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
          Date date = isoDate.parse(movieList.getString("rental_date"));
          Button button = (Button) node;
          button.setPrefSize(button.getPrefWidth(), button.getPrefHeight()
              + 50);
          button.setText(String.format("%s\nRented: %s", button.getText(),
              isoDate.format(date)));
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
