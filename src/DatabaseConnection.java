import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides a global database connection and various query methods.
 *
 * @author Chad Goodwin and Tyler Bratton
 */
public class DatabaseConnection {

  private static final String HOST = "jdbc:mysql://hotelboxoffice"
      + ".cornjso48dgu.us-east-1.rds.amazonaws"
      + ".com:3306/HotelBoxOffice?verifyServerCertificate=false&useSSL=true"
      + "&requireSSL=true";
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
   * <p>Selects all data from an entire table.
   *
   * @param table The table that you are selecting data from.
   * @return A ResultSet object that contains the information that you
   *         requested.
   */
  public ResultSet searchStatement(String table) {
    PreparedStatement stm;
    try {
      String search = String.format("SELECT * FROM %s", table);
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
   * <p>Selects 3 columns of a specified table.
   *
   * @param col1  The first column you want to get data for.
   * @param col2  The second column you want to get data for.
   * @param col3  The third column you want to get data for.
   * @param table The table that you are selecting data from.
   * @return A ResultSet object that contains the information you requested.
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
   * <p>Selects all rows that match a specified pattern.
   *
   * @param table The table that you want to select data from.
   * @param field The column of data that you are using to find a row.
   * @param value The value of that column that you are getting the row data
   *              for.
   * @return A ResultSet object that contains the information you requested.
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
   * @param table    The table that you want to select data from.
   * @param field    The column of data that you are using to find a row/rows.
   * @param value    The pattern that you are using to select froms.
   * @param useLower Determines whether to use the LOWER() function when
   *                 searching.
   * @return A ResultSet object that contains the information you requested.
   */
  public ResultSet searchStatement(String table, String field, String value,
                                   boolean useLower) {
    PreparedStatement stm;
    try {
      String search = "";
      if (useLower) {
        search = String.format("SELECT * FROM %s WHERE LOWER(%s) LIKE "
            + "LOWER(\'%%%s%%\')", table, field, value);
      } else {
        search = String.format("SELECT * FROM %s WHERE %s LIKE \'%%%s%%\')",
            table, field, value);
      }
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }


  /**
   * USING THIS SEARCH TO GET THE LIST OF MOVIE FOR EACH ACTOR.
   * Automatically constructs search statement and executes it.
   *
   * @param table1 The table that has field1 (MOVIES).
   * @param table2 The table that has info from table1 and table3 (CASTING).
   * @param table3 The table that has the field2 used to look for field1 (ACTORS).
   * @param field1 The field from table1 (MOVIE_ID)
   * @param field2 The field from table3 (ACTOR_ID)
   *               
   * @param value  The pattern that you are looking for
   * @return A ResultSet object that contains the information you requested.
   */
   
   public ResultSet searchStatement (String table1, String table2, String table3,
                                     String value, String field1, String field2){
       
    PreparedStatement stm;
    try {
      String search = String.format("SELECT * FROM %s, %s, %s WHERE %s.%s ="
              + " %s AND %s.%s = %s.%s AND %s.%s = %s.%s", table1,
          table2, table3, table2, field2, value, table2, field1, table1, field1,
          table2, field2, table3, field2);
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }
  
  
  /**
   * NOT SURE IF THIS SEARCH STATEMENT SHOULD BE USED TO ADD THE GENRE IN
   * MOVIEPAGE, CASTING IS NOT INCLUDED
   * Automatically constructs search statement and executes it.
   *
   * @param table1 The table that you want to select data from.
   * @param table2 The table that you want to join a field from.
   * @param field1 The column of data that you are using to find a row/rows.
   * @param field2 The column of data from table2 that you are using to find a
   *               row/rows.
   * @param value  The pattern that you are using to select from.
   * @return A ResultSet object that contains the information you requested.
   */
  public ResultSet searchStatement(String table1, String table2, String field1,
                                   String field2, String value) {
    PreparedStatement stm;
    try {
      String search = String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.%s"
          + " WHERE %s = %s", table1, table2, table1, field1, table2, field1,
          field2, value);
      stm = con.prepareStatement(search);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  /**
   * Use if you just want to put in the SQL command yourself.
   *
   * @param entireCommand The full SQL statement/command.
   * @param fullControl   Verifying that you are crazy.
   * @return A ResultSet object that contains the information you requested.
   */
  public ResultSet searchStatement(String entireCommand, boolean fullControl) {
    PreparedStatement stm;
    try {
      stm = con.prepareStatement(entireCommand);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  /**
   * A method to execute update statements on the database.
   *
   * @param entireStatement The statement that you wish to issue.
   */
  public void updateStatement(String entireStatement) {
    PreparedStatement stm;
    try {
      stm = con.prepareStatement(entireStatement);
      stm.executeUpdate();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
  
  public void deleteStatement(String table, String primaryKeyName, String index){
      PreparedStatement stm;
    try {
      String search = String.format("DELETE FROM %s WHERE %s = %s", 
              table,primaryKeyName, index);
      stm = con.prepareStatement(search);
      stm.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      
    } 
  }
}
