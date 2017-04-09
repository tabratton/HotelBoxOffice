import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides a global database connection and various query methods.
 *
 * @author Tyler Bratton
 * @author Chad Goodwin
 */
public class DatabaseConnection {

  private static final String HOST = "jdbc:mysql://localhost:3306/HotelBoxOffice";
  private Connection con;

  /**
   * Constructor for DatabaseConnection. Initializes a Connection object for
   * SQL queries.
   */
  public DatabaseConnection(String[] args) {
    try {
      con = DriverManager.getConnection(HOST, args[0], args[1]);
      System.out.println("Connected to local database");
    } catch (SQLException ex) {
      System.out.println("Cannot connect to local database.");
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Use if you just want to put in the SQL command yourself.
   *
   * @param query The full SQL statement/command.
   * @return A ResultSet object that contains the information you requested.
   */
  public ResultSet searchStatement(String query) {
    PreparedStatement stm;
    try {
      stm = con.prepareStatement(query);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  /**
   * A method to execute update statements on the database.
   *
   * @param update The statement that you wish to issue.
   */
  public void updateStatement(String update) {
    PreparedStatement stm;
    try {
      stm = con.prepareStatement(update);
      stm.executeUpdate();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /**
   * Deletes all entries from a table that match the condition.
   *
   * @param delete The deletion you want to make.
   */
  public void deleteStatement(String delete) {
    PreparedStatement stm;
    try {
      stm = con.prepareStatement(delete);
      stm.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
