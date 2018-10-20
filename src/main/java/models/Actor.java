package models;

import java.time.ZonedDateTime;

public class Actor {

  private String id;
  private String name;
  private String image;
  private String bio;
  private int viewed;
  private ZonedDateTime lastModified;

  public Actor() {
    this.id = "";
    this.name = "";
    this.image = "";
    this.bio = "";
    this.viewed = 0;
    this.lastModified = ZonedDateTime.now();
  }

  public Actor(String id, String name, String image, String bio, int viewed, ZonedDateTime lastModified) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.bio = bio;
    this.viewed = viewed;
    this.lastModified = lastModified;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
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
