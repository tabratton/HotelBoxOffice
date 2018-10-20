package models;

public class Casting {
  private String castingId;
  private String actorId;
  private String movieId;

  public Casting() {
    this.castingId = "";
    this.actorId = "";
    this.movieId = "";
  }

  public Casting(String castingId, String actorId, String movieId) {
    this.castingId = castingId;
    this.actorId = actorId;
    this.movieId = movieId;
  }

  public String getCastingId() {
    return castingId;
  }

  public void setCastingId(String castingId) {
    this.castingId = castingId;
  }

  public String getActorId() {
    return actorId;
  }

  public void setActorId(String actorId) {
    this.actorId = actorId;
  }

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }
}
