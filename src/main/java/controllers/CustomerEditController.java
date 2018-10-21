package controllers;

import hotelbox.HotBoxNavigator;
import hotelbox.HotelBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Customer;
import util.GeneralUtilities;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class.
 *
 * @author Chad Goodwin
 */
public class CustomerEditController implements Initializable {
  @FXML
  private TextField customerName;
  @FXML
  private TextField customerRoom;
  @FXML
  private TextField customerBalance;
  @FXML
  private TextField customerPassword;
  @FXML
  private TextField customerAddress;
  @FXML
  private TextField customerZipcode;
  @FXML
  private TextField customerCity;
  @FXML
  private TextField customerState;
  @FXML
  private CheckBox customerAdmin;
  @FXML
  private Button submitButton;
  @FXML
  private Button cancelButton;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    var customer = HotelBox.dbConnection.getCustomerById(HotBoxNavigator.editRecord).orElse(new Customer());
    if (HotBoxNavigator.editRecord != null) {
      customerName.setText(customer.getCustomerName());
      var balance = customer.getBalance();

      customerRoom.setText(customer.getRoom().toString());
      customerBalance.setText(String.format("%.2f", balance));
      customerAddress.setText(customer.getAddress());
      customerCity.setText(customer.getCity());
      customerState.setText(customer.getState());
      customerZipcode.setText(customer.getZipcode().toString());

      if (customer.isAdmin()) {
        customerAdmin.setSelected(true);
      }
    }

    submitButton.setOnAction(event -> {
      var name = customerName.getText();
      var bal = customerBalance.getText();
      var room = customerRoom.getText();
      var password = GeneralUtilities.encodePassword(customerPassword.getText());
      var address = customerAddress.getText();
      var city = customerCity.getText();
      var zipcode = customerZipcode.getText();
      var state = customerState.getText();
      var admin = customerAdmin.isSelected();

      HotelBox.dbConnection.saveCustomer(new Customer(
          customer.getCustomerId(),
          name,
          password,
          new BigDecimal(bal),
          Integer.parseInt(room),
          admin,
          address,
          city,
          Integer.parseInt(zipcode),
          state
      ));
      GeneralUtilities.showSuccessMessage();
      HotBoxNavigator.loadPage(HotBoxNavigator.editRecord != null ? HotBoxNavigator.ADMIN_PAGE :
          HotBoxNavigator.EDIT_PAGE);
    });

    cancelButton.setOnAction(event ->
        HotBoxNavigator.loadPage(HotBoxNavigator.ADMIN_PAGE));
  }
}
