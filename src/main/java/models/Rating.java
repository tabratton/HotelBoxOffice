package models;

import java.time.ZonedDateTime;

public class Rating {
  private String ratingId;
  private String customerId;
  private String movieId;
  private int score;
  private ZonedDateTime lastModified;

  public Rating() {
    this.ratingId = "";
    this.customerId = "";
    this.movieId = "";
    this.score = -1;
    this.lastModified = ZonedDateTime.now();
  }

  public Rating(String ratingId, String customerId, String movieId, int score, ZonedDateTime lastModified) {
    this.ratingId = ratingId;
    this.customerId = customerId;
    this.movieId = movieId;
    this.score = score;
    this.lastModified = lastModified;
  }

  public String getRatingId() {
    return ratingId;
  }

  public void setRatingId(String ratingId) {
    this.ratingId = ratingId;
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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public ZonedDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(ZonedDateTime lastModified) {
    this.lastModified = lastModified;
  }
}
