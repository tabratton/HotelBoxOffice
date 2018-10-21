package database;

import models.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides a global database connection and various query methods.
 *
 * @author Tyler Bratton
 * @author Chad Goodwin
 */
public class DatabaseConnection {

  private Connection con;

  /**
   * Constructor for DatabaseConnection. Initializes a Connection object for
   * SQL queries.
   */
  public DatabaseConnection(String... args) {
    try {
      con = DriverManager.getConnection(String.format("jdbc:postgresql://%s/hotel_box_office", args[0]), args[1],
          args[2]);
      System.out.println("Connected to local database");
    } catch (SQLException ex) {
      System.out.println("Cannot connect to local database.");
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Deletes all entries from a table that match the condition.
   *
   * @param delete The deletion you want to make.
   */
  public void deleteStatement(String delete) {
    try (PreparedStatement stm = con.prepareStatement(delete)) {
      stm.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<AvgRating> getTopFiveMovies() {
    var stmt = "SELECT movie_id, SUM(score)/COUNT(movie_id) avg FROM ratings GROUP BY movie_id ORDER BY avg DESC " +
        "LIMIT 5";
    var averages = new ArrayList<AvgRating>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        averages.add(new AvgRating(
            result.getString("movie_id"),
            result.getDouble("avg")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return averages;
  }

  public List<AvgRating> getBottomFiveMovies() {
    var stmt = "SELECT movie_id, SUM(score)/COUNT(movie_id) avg FROM ratings GROUP BY movie_id ORDER BY avg ASC LIMIT" +
        " 5";
    var averages = new ArrayList<AvgRating>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        averages.add(new AvgRating(
            result.getString("movie_id"),
            result.getDouble("avg")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return averages;
  }

  public List<Actor> getActors() {
    var stmt = "SELECT * FROM actors";
    var actors = new ArrayList<Actor>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        actors.add(new Actor(
            result.getString("actor_id"),
            result.getString("actor_name"),
            result.getString("image"),
            result.getString("bio"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return actors;
  }

  public List<Actor> getActorsByMovieId(String movieId) {
    var stmt = "SELECT * FROM actors as a INNER JOIN (SELECT * FROM casting as c"
        + " INNER JOIN movies as m ON c.movie_id=m.movie_id WHERE m.movie_id=?::uuid) mc"
        + " ON mc.actor_id=a.actor_id";

    var actors = new ArrayList<Actor>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, movieId);
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          actors.add(new Actor(
              result.getString("actor_id"),
              result.getString("actor_name"),
              result.getString("image"),
              result.getString("bio"),
              result.getInt("viewed"),
              getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return actors;
  }

  public Optional<Actor> getActor(String id) {
    var stmt = "SELECT * FROM actors WHERE actor_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, id);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Actor(
            result.getString("actor_id"),
            result.getString("actor_name"),
            result.getString("image"),
            result.getString("bio"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveActor(Actor actor) {
    String stmt;
    if (actor.getId().isBlank()) {
      stmt = "INSERT INTO actors (actor_id, actor_name, image, bio, viewed, last_modified) VALUES (DEFAULT, ?, ?, ?,"
          + " ?, ?)";
    } else {
      stmt = "INSERT INTO actors (actor_id, actor_name, image, bio, viewed, last_modified) VALUES (?::uuid, ?, ?, ?,"
          + " ?, ?) ON CONFLICT (actor_id) DO UPDATE SET actor_name=EXCLUDED.actor_name, image=EXCLUDED.image,"
          + " bio=EXCLUDED.bio, viewed=EXCLUDED.viewed, last_modified=EXCLUDED.last_modified";
    }

    try (var pstmt = con.prepareStatement(stmt)) {
      var index = 1;
      if (!actor.getId().isBlank()) {
        pstmt.setString(index++, actor.getId());
      }
      pstmt.setString(index++, actor.getName());
      pstmt.setString(index++, actor.getImage());
      pstmt.setString(index++, actor.getBio());
      pstmt.setInt(index++, actor.getViewed());
      pstmt.setTimestamp(index, Timestamp.from(actor.getLastModified().toInstant()));

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<Movie> getMovies() {
    var stmt = "SELECT * FROM movies";
    var movies = new ArrayList<Movie>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        movies.add(new Movie(
            result.getString("movie_id"),
            result.getString("title"),
            result.getString("director"),
            result.getString("description"),
            result.getDate("release_date").toLocalDate(),
            result.getString("image"),
            result.getString("genre_id"),
            result.getBigDecimal("price"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return movies;
  }

  public List<Movie> getMoviesByActorId(String actorId) {
    var stmt = "SELECT * FROM movies as m INNER JOIN (SELECT * FROM casting as c"
        + " INNER JOIN actors as a ON c.actor_id=a.actor_id WHERE a.actor_id=?::uuid) ac"
        + " ON ac.movie_id=m.movie_id";

    var movies = new ArrayList<Movie>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, actorId);
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          movies.add(new Movie(
              result.getString("movie_id"),
              result.getString("title"),
              result.getString("director"),
              result.getString("description"),
              result.getDate("release_date").toLocalDate(),
              result.getString("image"),
              result.getString("genre_id"),
              result.getBigDecimal("price"),
              result.getInt("viewed"),
              getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return movies;
  }

  public List<Movie> getMoviesByGenreName(String genre) {
    if (genre.equals("All")) {
      return getMovies();
    }

    var stmt = "SELECT * FROM movies as m INNER JOIN genres as g ON g.genre_id=m.genre_id WHERE g.genre_name=?";
    var movies = new ArrayList<Movie>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, genre);
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          movies.add(new Movie(
              result.getString("movie_id"),
              result.getString("title"),
              result.getString("director"),
              result.getString("description"),
              result.getDate("release_date").toLocalDate(),
              result.getString("image"),
              result.getString("genre_id"),
              result.getBigDecimal("price"),
              result.getInt("viewed"),
              getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return movies;
  }

  public Optional<Movie> getMovie(String id) {
    var stmt = "SELECT * FROM movies WHERE movie_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, id);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Movie(
            result.getString("movie_id"),
            result.getString("title"),
            result.getString("director"),
            result.getString("description"),
            result.getDate("release_date").toLocalDate(),
            result.getString("image"),
            result.getString("genre_id"),
            result.getBigDecimal("price"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveMovie(Movie movie) {
    String stmt;
    if (movie.getId().isBlank()) {
      stmt = "INSERT INTO movies (movie_id, title, director, description, release_date, image, genre_id, price,"
          + " viewed, last_modified) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?::uuid, ?, ?, ?::timestamp with time zone)";
    } else {
      stmt = "INSERT INTO movies (movie_id, title, director, description, release_date, image, genre_id, price,"
          + " viewed, last_modified) VALUES (?::uuid, ?, ?, ?, ?, ?, ?::uuid, ?, ?, ?::timestamp with time zone) ON"
          + " CONFLICT (movie_id) DO UPDATE SET title=EXCLUDED.title, director=EXCLUDED.director,"
          + " description=EXCLUDED.description, release_date=EXCLUDED.release_date, image=EXLUDED.image,"
          + " genre_id=EXCLUDED.genre_id, price=EXCLUDED.price, viewed=EXLUCDED.viewed, last_modifed=EXCLUDED"
          + ".last_modified";
    }

    try (var pstmt = con.prepareStatement(stmt)) {
      var index = 1;
      if (!movie.getId().isBlank()) {
        pstmt.setString(index++, movie.getId());
      }
      pstmt.setString(index++, movie.getTitle());
      pstmt.setString(index++, movie.getDirector());
      pstmt.setString(index++, movie.getDescription());
      pstmt.setDate(index++, Date.valueOf(movie.getReleaseDate()));
      pstmt.setString(index++, movie.getImage());
      pstmt.setString(index++, movie.getGenreId());
      pstmt.setBigDecimal(index++, movie.getPrice());
      pstmt.setInt(index++, movie.getViewed());
      pstmt.setString(index, movie.getLastModified().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public Optional<Casting> getCasting(String actorId, String movieId) {
    var stmt = "SELECT * FROM casting WHERE actor_id=?::uuid AND movie_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, actorId);
      pstmt.setString(2, movieId);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Casting(
            result.getString("casting_id"),
            result.getString("actor_id"),
            result.getString("movie_id")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveCasting(Casting casting) {
    String stmt;
    if (casting.getCastingId().isBlank()) {
      stmt = "INSERT INTO casting (casting_id, actor_id, movie_id) VALUES (DEFAULT, ?::uuid, ?::uuid)";
    } else {
      stmt = "INSERT INTO casting (casting_id, actor_id, movie_id) VALUES (?::uuid, ?::uuid, ?::uuid) ON CONFLICT"
          + " (casting_id) DO UPDATE SET actor_id=EXCLUDED.actor_id, movie_id=EXCLUDED.movie_id";
    }

    try (var pstmt = con.prepareStatement(stmt)) {
      var index = 1;
      if (!casting.getCastingId().isBlank()) {
        pstmt.setString(index++, casting.getCastingId());
      }
      pstmt.setString(index++, casting.getActorId());
      pstmt.setString(index, casting.getMovieId());

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void removeCasting(Casting casting) {
    var stmt = "DELETE FROM casting WHERE actor_id=?::uuid AND movie_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, casting.getActorId());
      pstmt.setString(2, casting.getMovieId());
      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<Customer> getCustomers() {
    var stmt = "SELECT * FROM customers";
    var customers = new ArrayList<Customer>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        customers.add(new Customer(
            result.getString("customer_id"),
            result.getString("customer_name"),
            result.getString("pwd"),
            result.getBigDecimal("balance"),
            result.getInt("room"),
            result.getBoolean("is_admin"),
            result.getString("address"),
            result.getString("city"),
            result.getInt("zipcode"),
            result.getString("us_state")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return customers;
  }

  public Optional<Customer> getCustomerById(String customerId) {
    var stmt = "SELECT * FROM customers WHERE customer_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, customerId);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Customer(
            result.getString("customer_id"),
            result.getString("customer_name"),
            result.getString("pwd"),
            result.getBigDecimal("balance"),
            result.getInt("room"),
            result.getBoolean("is_admin"),
            result.getString("address"),
            result.getString("city"),
            result.getInt("zipcode"),
            result.getString("us_state")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public Optional<Customer> getCustomerByName(String name) {
    var stmt = "SELECT * FROM customers WHERE customer_name=?";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, name);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Customer(
            result.getString("customer_id"),
            result.getString("customer_name"),
            result.getString("pwd"),
            result.getBigDecimal("balance"),
            result.getInt("room"),
            result.getBoolean("is_admin"),
            result.getString("address"),
            result.getString("city"),
            result.getInt("zipcode"),
            result.getString("us_state")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveCustomer(Customer customer) {
    if (customer.getPwd().isBlank()) {
      var existingCustomer = this.getCustomerById(customer.getCustomerId());
      existingCustomer.ifPresent(c -> customer.setPwd(c.getPwd()));
      return;
    }

    String stmt;
    if (customer.getCustomerId().isBlank()) {
      stmt = "INSERT INTO customers (customer_id, customer_name, pwd, balance, room, is_admin, address, city,"
          + " zipcode, us_state) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    } else {
      stmt = "INSERT INTO customers (customer_id, customer_name, pwd, balance, room, is_admin, address, city,"
          + " zipcode, us_state) VALUES (?::uuid, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (customer_id)"
          + " DO UPDATE SET customer_name=EXCLUDED.customer_name, pwd=EXCLUDED.pwd, balance=EXCLUDED.balance,"
          + " room=EXCLUDED.room, is_admin=EXCLUDED.is_admin, address=EXCLUDED.address, city=EXCLUDED.city,"
          + " zipcode=EXCLUDED.zipcode, us_state=EXCLUDED.us_state";
    }

    try (var pstmt = con.prepareStatement(stmt)) {
      var index = 1;
      if (!customer.getCustomerId().isBlank()) {
        pstmt.setString(index++, customer.getCustomerId());
      }

      pstmt.setString(index++, customer.getCustomerName());
      pstmt.setString(index++, customer.getPwd());
      pstmt.setBigDecimal(index++, customer.getBalance());
      pstmt.setInt(index++, customer.getRoom());
      pstmt.setBoolean(index++, customer.isAdmin());
      pstmt.setString(index++, customer.getAddress());
      pstmt.setString(index++, customer.getCity());
      pstmt.setInt(index++, customer.getZipcode());
      pstmt.setString(index, customer.getState());

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void addToBalance(String customerId, BigDecimal price) {
    String stmt = "UPDATE customers SET balance=balance + ? WHERE customer_id=?::uuid";

    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setBigDecimal(1, price);
      pstmt.setString(2, customerId);
      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<Rental> getRentalsByCustomer(String customerId) {
    var stmt = "SELECT * FROM rentals as r INNER JOIN movies as m ON r.movie_id=m.movie_id WHERE r.customer_id=?::uuid"
        + " ORDER BY r.rental_date DESC";

    var rentals = new ArrayList<Rental>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, customerId);
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          rentals.add(new Rental(
              result.getString("rental_id"),
              result.getString("customer_id"),
              result.getString("movie_id"),
              result.getDate("rental_date").toLocalDate(),
              result.getBigDecimal("cost")
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return rentals;
  }

  public void saveRental(Rental rental) {
    var stmt = "INSERT INTO rentals (rental_id, customer_id, movie_id, rental_date, cost) VALUES (DEFAULT, ?::uuid,"
        + " ?::uuid, ?::date, ?)";

    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, rental.getCustomerId());
      pstmt.setString(2, rental.getMovieId());
      pstmt.setString(3, rental.getRentalDate().format(DateTimeFormatter.ISO_DATE));
      pstmt.setBigDecimal(4, rental.getScore());
      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<Genre> getGenres() {
    var stmt = "SELECT * FROM genres";
    var genres = new ArrayList<Genre>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        genres.add(new Genre(
            result.getString("genre_id"),
            result.getString("genre_name")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return genres;
  }

  public Optional<Genre> getGenre(String genreId) {
    var stmt = "SELECT * FROM genres WHERE genre_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, genreId);
      try (var result = pstmt.executeQuery()) {
        result.next();
        return Optional.of(new Genre(
            result.getString("genre_id"),
            result.getString("genre_name")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveGenre(Genre genre) {
    String stmt;
    if (genre.getGenreId().isBlank()) {
      stmt = "INSERT INTO genres (genre_id, genre_name) VALUES (DEFAULT, ?)";
    } else {
      stmt = "INSERT INTO genres (genre_id, genre_name) VALUES (?::uuid, ?) ON CONFLICT (genre_id)"
          + " DO UPDATE SET genre_name=EXCLUDED.genre_name";
    }

    var index = 1;
    try (var pstmt = con.prepareStatement(stmt)) {
      if (!genre.getGenreId().isBlank()) {
        pstmt.setString(index++, genre.getGenreId());
      }
      pstmt.setString(index, genre.getGenreName());

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public void updateTimesViewed(String table, String id) {
    var stmt = String.format("UPDATE %s SET viewed=viewed + 1 WHERE %s_id=?::uuid",
        table,
        table.substring(0, table.length() - 1));

    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, id);
      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public List<SearchTerm> getSearchTerms(boolean orderDesc) {
    var stmt = "SELECT * FROM search_terms" + (orderDesc ? " ORDER BY freq DESC LIMIT 5" : "");
    var genres = new ArrayList<SearchTerm>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        genres.add(new SearchTerm(
            result.getString("searchterm_id"),
            result.getString("term"),
            result.getInt("freq")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return genres;
  }

  public void saveSearchTerm(String term) {
    String stmt = "INSERT INTO search_terms (searchterm_id, term, freq) VALUES (DEFAULT, ?, 1) ON CONFLICT (term)"
        + " DO UPDATE SET freq=search_terms.freq + 1";

    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, term);
      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public boolean checkRentalExists(String customerId, String movieId) {
    String stmt = "SELECT customer_id FROM rentals WHERE customer_id=?::uuid AND movie_id=?::uuid";

    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, customerId);
      pstmt.setString(2, movieId);
      try (var result = pstmt.executeQuery()) {
        return result.next();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return false;
  }

  public Optional<Rating> getRating(String customerId, String movieId) {
    var stmt = "SELECT * FROM ratings WHERE customer_id=?::uuid AND movie_id=?::uuid";
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, customerId);
      pstmt.setString(2, movieId);
      try (var result = pstmt.executeQuery()) {
        return result.next() ? Optional.of(new Rating(
            result.getString("rating_id"),
            result.getString("customer_id"),
            result.getString("movie_id"),
            result.getInt("score"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        )) : Optional.empty();
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return Optional.empty();
  }

  public void saveRating(Rating rating) {
    String stmt;
    var exists = getRating(rating.getCustomerId(), rating.getMovieId()).isPresent();
    if (exists) {
      stmt = "UPDATE ratings SET score=?, last_modified=?::timestamp with time zone WHERE customer_id=?::uuid AND"
          + " movie_id=?::uuid";
    } else {
      stmt = "INSERT INTO ratings (rating_id, customer_id, movie_id, score, last_modified) VALUES (DEFAULT, ?::uuid," +
          " ?::uuid, ?, ?::timestamp with time zone)";
    }

    try (var pstmt = con.prepareStatement(stmt)) {
      if (exists) {
        pstmt.setInt(1, rating.getScore());
        pstmt.setString(2, rating.getLastModified().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        pstmt.setString(3, rating.getCustomerId());
        pstmt.setString(4, rating.getMovieId());
      } else {
        pstmt.setString(1, rating.getCustomerId());
        pstmt.setString(2, rating.getMovieId());
        pstmt.setInt(3, rating.getScore());
        pstmt.setString(4, rating.getLastModified().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
      }

      pstmt.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public double getRatingForMovie(String movieId) {
    var stmt = "SELECT SUM(score)/COUNT(score) avg FROM ratings WHERE movie_id=?::uuid";

    double ratingAverage = -10.0;
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, movieId);
      try (var rating = pstmt.executeQuery()) {
        rating.next();
        ratingAverage = rating.getDouble("avg");
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return ratingAverage;
  }

  public List<Movie> getMoviesOrderViewed() {
    var stmt = "SELECT * FROM movies ORDER BY viewed DESC LIMIT 5";
    var movies = new ArrayList<Movie>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        movies.add(new Movie(
            result.getString("movie_id"),
            result.getString("title"),
            result.getString("director"),
            result.getString("description"),
            result.getDate("release_date").toLocalDate(),
            result.getString("image"),
            result.getString("genre_id"),
            result.getBigDecimal("price"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return movies;
  }

  public List<Actor> getActorsOrderViewed() {
    var stmt = "SELECT * FROM actors ORDER BY viewed DESC LIMIT 5";
    var actors = new ArrayList<Actor>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        actors.add(new Actor(
            result.getString("actor_id"),
            result.getString("actor_name"),
            result.getString("image"),
            result.getString("bio"),
            result.getInt("viewed"),
            getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return actors;
  }

  public List<Movie> findMovieByTerm(String term) {
    var stmt = "SELECT * FROM movies WHERE LOWER(title) LIKE LOWER(?)";
    var movies = new ArrayList<Movie>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, "%" + term + "%");
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          movies.add(new Movie(
              result.getString("movie_id"),
              result.getString("title"),
              result.getString("director"),
              result.getString("description"),
              result.getDate("release_date").toLocalDate(),
              result.getString("image"),
              result.getString("genre_id"),
              result.getBigDecimal("price"),
              result.getInt("viewed"),
              getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return movies;
  }

  public List<Actor> findActorByTerm(String term) {
    var stmt = "SELECT * FROM actors WHERE LOWER(actor_name) LIKE LOWER(?)";
    var actors = new ArrayList<Actor>();
    try (var pstmt = con.prepareStatement(stmt)) {
      pstmt.setString(1, "%" + term + "%");
      try (var result = pstmt.executeQuery()) {
        while (result.next()) {
          actors.add(new Actor(
              result.getString("actor_id"),
              result.getString("actor_name"),
              result.getString("image"),
              result.getString("bio"),
              result.getInt("viewed"),
              getZDTFromTimestamp(result.getTimestamp("last_modified"), result.getString("last_modified"))
          ));
        }
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return actors;
  }

  public int getCount(String table) {
    var stmt = "SELECT COUNT(*) number FROM " + table;
    try (var pstmt = con.prepareStatement(stmt)) {
      try (var count = pstmt.executeQuery()) {
        count.next();
        return count.getInt("number");
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return 0;
  }

  public List<RentalTotals> getTopRented() {
    var stmt = "SELECT movie_id, COUNT(movie_id) total FROM rentals GROUP BY movie_id ORDER BY COUNT(movie_id) DESC " +
        "LIMIT 5";
    var totals = new ArrayList<RentalTotals>();
    try (var pstmt = con.prepareStatement(stmt); var result = pstmt.executeQuery()) {
      while (result.next()) {
        totals.add(new RentalTotals(
            result.getString("movie_id"),
            result.getInt("total")
        ));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }

    return totals;
  }

  private ZonedDateTime getZDTFromTimestamp(Timestamp timestamp, String timestring) {
    return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of(timestring.substring(timestring.lastIndexOf('-'))));
  }

}
