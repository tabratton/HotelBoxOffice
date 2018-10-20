package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rental {
  private String rentalId;
  private String customerId;
  private String movieId;
  private LocalDate rentalDate;
  private BigDecimal score;

  public Rental() {
    this.rentalId = "";
    this.customerId = "";
    this.movieId = "";
    this.rentalDate = LocalDate.now();
    this.score = BigDecimal.ZERO;
  }

  public Rental(String rentalId, String customerId, String movieId, LocalDate rentalDate, BigDecimal score) {
    this.rentalId = rentalId;
    this.customerId = customerId;
    this.movieId = movieId;
    this.rentalDate = rentalDate;
    this.score = score;
  }

  public String getRentalId() {
    return rentalId;
  }

  public void setRentalId(String rentalId) {
    this.rentalId = rentalId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public LocalDate getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(LocalDate rentalDate) {
    this.rentalDate = rentalDate;
  }

  public BigDecimal getScore() {
    return score;
  }

  public void setScore(BigDecimal score) {
    this.score = score;
  }
}
