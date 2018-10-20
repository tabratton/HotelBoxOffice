package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import models.Customer;
import models.Movie;
import util.Dimensions;
import util.GeneralUtilities;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Customer information controller class.
 *
 * @author Tyler Bratton
 */
public class CustomerInformationController implements Initializable {
  // HashMap to store MOVIE_TITLE as a key and MOVIE_ID as a value.
  private Map<String, String> titleKeys = new HashMap<>();
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
    var customer = HotelBox.dbConnection.getCustomerById(HotelBox.getCurrentUserId()).orElse(new Customer());

    name.setText(customer.getCustomerName());
    address.setText(customer.getAddress());
    cityAndState.setText(String.format("%s, %s", customer.getCity(), customer.getState()));
    zipcode.setText(customer.getZipcode().toString());
    BigDecimal currentBalance = customer.getBalance();
    balance.setText(String.format("$%.2f", currentBalance));
    room.setText(customer.getRoom().toString());
  }

  private void createRentedList() {
    var rentals = HotelBox.dbConnection.getRentalsByCustomer(HotelBox.getCurrentUserId());
    rentals.forEach(rental -> {
      var movie = HotelBox.dbConnection.getMovie(rental.getMovieId()).orElse(new Movie());
      GeneralUtilities.createButton(
          movie.getTitle(),
          movie.getImage(),
          movie.getId(),
          titleKeys,
          flowPane,
          HotBoxNavigator.MOVIE_PAGE,
          new Dimensions(Dimensions.DimensionTypes.SMALL),
          "movies",
          HotBoxNavigator.CUSTOMER_INFORMATION);
    });

    var list = flowPane.getChildren();

    var count = 0;
    for (Node node : list) {
      var date = rentals.get(count).getRentalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
      var button = (Button) node;
      button.setPrefSize(button.getPrefWidth(), button.getPrefHeight() + 50);
      button.setText(String.format("%s\nRented: %s", button.getText(), date));
      count++;
    }
  }
}