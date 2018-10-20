package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class Movie {
  private String id;
  private String title;
  private String director;
  private String description;
  private LocalDate releaseDate;
  private String image;
  private String genreId;
  private BigDecimal price;
  private int viewed;
  private ZonedDateTime lastModified;

  public Movie() {
    this.id = "";
    this.title = "";
    this.director = "";
    this.description = "";
    this.releaseDate = LocalDate.now();
    this.image = "";
    this.genreId = "";
    this.price = BigDecimal.ZERO;
    this.viewed = 0;
    this.lastModified = ZonedDateTime.now();
  }

  public Movie(
      String id,
      String title,
      String director,
      String description,
      LocalDate releaseDate,
      String image,
      String genreId,
      BigDecimal price,
      int viewed,
      ZonedDateTime lastModified) {

    this.id = id;
    this.title = title;
    this.director = director;
    this.description = description;
    this.releaseDate = releaseDate;
    this.image = image;
    this.genreId = genreId;
    this.price = price;
    this.viewed = viewed;
    this.lastModified = lastModified;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getGenreId() {
    return genreId;
  }

  public void setGenreId(String genreId) {
    this.genreId = genreId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getViewed() {
    return viewed;
  }

  public void setViewed(int viewed) {
    this.viewed = viewed;
  }

  public ZonedDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(ZonedDateTime lastModified) {
    this.lastModified = lastModified;
  }
}
