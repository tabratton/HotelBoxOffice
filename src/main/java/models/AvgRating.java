package models;

public class AvgRating {
  private String movieId;
  private double average;

  public AvgRating(String movieId, double average) {
    this.movieId = movieId;
    this.average = average;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public double getAverage() {
    return average;
  }

  public void setAverage(double average) {
    this.average = average;
  }
}
