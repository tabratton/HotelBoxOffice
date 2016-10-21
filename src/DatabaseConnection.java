import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// TODO Add javadoc comments

public class DatabaseConnection {

  private static final String HOST = "jdbc:mysql://hotelboxoffice"
      + ".cornjso48dgu.us-east-1.rds.amazonaws.com:3306/HotelBoxOffice";
  private static final String USERNAME = "masterRoot";
  private static final String PASSWORD = "masterRoot";
  private Connection con;

  /**
   * Constructor for DatabaseConnection. Initializes a Connection object for
   * SQL queries.
   */
  public DatabaseConnection() {
    try {
      con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
      System.out.println("Connected to online database");
    } catch (SQLException ex) {
      String localHost = "jdbc:mysql://localhost:3306/HotelBoxOffice";
      String localUser = "root";
      String localPass = "root";
      System.out.println("Cannot connect to online database.");
      System.out.println(ex.getMessage());
      try {
        con = DriverManager.getConnection(localHost, localUser, localPass);
      } catch (SQLException exp) {
        System.out.println("Cannot connect to local database.");
        System.out.println(exp.getMessage());
      }
    }
  }

  public Connection getCon() {
    return con;
  }

  /**
   * Automatically constructs search statement and executes it.
   *
   * @param col1  The first column you want to get data for.
   * @param col2  The second column you want to get data for.
   * @param col3  The third column you want to get data for.
   * @param table The table that you are selecting data from.
   * @return      A ResultSet object that contains the information you
   *              requested.
   */
  public ResultSet searchStatement(String col1, String col2, String col3,
                                   String table) {
    PreparedStatement stm;
    try {
      String search = String.format("SELECT %s, %s, %s FROM %s", col1, col2,
          col3, table);
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  /**
   * Automatically constructs search statement and executes it.
   *
   * @param table The table that you want to select data from.
   * @param field The column of data that you are using to find a row.
   * @param value The value of that column that you are getting the row data
   *              for.
   * @return      A ResultSet object that contains the information you
   *              requested.
   */
  public ResultSet searchStatement(String table, String field, String value) {
    PreparedStatement stm;
    try {
      String search = String.format("SELECT * FROM %s WHERE %s = %s", table,
          field, value);
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  /**
   * Automatically constructs search statement and executes it.
   *
   * @param table   The table that you want to select data from.
   * @param field   The column of data that you are using to find a row/rows.
   * @param value   The pattern that you are using to select froms.
   * @param similar Unused boolean that is simply to have one more argument
   *                so that it doesn't conflict with other searchStatement
   *                methods.
   * @return        A ResultSet object that contains the information you
   *                requested.
   */
  public ResultSet searchStatement(String table, String field, String value,
                                   boolean similar) {
    PreparedStatement stm;
    try {
      String search = String.format("SELECT * FROM %s WHERE %s LIKE %%%s%%",
          table, field, value);
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }
}
