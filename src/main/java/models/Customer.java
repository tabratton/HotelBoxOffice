package models;

import java.math.BigDecimal;

public class Customer {
  private String customerId;
  private String customerName;
  private String pwd;
  private BigDecimal balance;
  private int room;
  private boolean admin;
  private String address;
  private String city;
  private int zipcode;
  private String state;

  public Customer() {
    this.customerId = "";
    this.customerName = "";
    this.pwd = "";
    this.balance = BigDecimal.ZERO;
    this.room = 0;
    this.admin = false;
    this.address = "";
    this.city = "";
    this.zipcode = 0;
    this.state = "";
  }

  public Customer(String customerId,
                  String customerName,
                  String pwd,
                  BigDecimal balance,
                  int room,
                  boolean admin,
                  String address,
                  String city,
                  int zipcode,
                  String state
  ) {
    this.customerId = customerId;
    this.customerName = customerName;
    this.pwd = pwd;
    this.balance = balance;
    this.room = room;
    this.admin = admin;
    this.address = address;
    this.city = city;
    this.zipcode = zipcode;
    this.state = state;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Integer getRoom() {
    return room;
  }

  public void setRoom(int room) {
    this.room = room;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getZipcode() {
    return zipcode;
  }

  public void setZipcode(int zipcode) {
    this.zipcode = zipcode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
