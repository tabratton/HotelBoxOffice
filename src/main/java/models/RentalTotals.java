package models;

public class RentalTotals {
  private String movieId;
  private int total;

  public RentalTotals(String movieId, int total) {
    this.movieId = movieId;
    this.total = total;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
