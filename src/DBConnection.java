import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

  private String HOST = "jdbc:mysql://hotelboxoffice.cornjso48dgu" +
      ".us-east-1.rds.amazonaws.com:3306/HotelBoxOffice";
  private String USER_NAME = "masterRoot";
  private String PASS_WORD = "masterRoot";
  private Connection con;

  public DBConnection() {
    try {
      con = DriverManager.getConnection(HOST, USER_NAME, PASS_WORD);
      System.out.println("Connected");
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public Connection getCon() {
    return con;
  }

  public ResultSet searchStatement(String row1, String row2, String row3,
                                   String table) {
    PreparedStatement stm;
    try {
      String s = String.format("SELECT %s, %s, %s FROM %s", row1, row2, row3,
          table);
      stm = con.prepareStatement(s);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  public ResultSet searchStatement(String table, String field, String value) {
    PreparedStatement stm;
    try {
      String s = String.format("SELECT * FROM %s WHERE %s = %s", table, field,
          value);
      stm = con.prepareStatement(s);
      return stm.executeQuery();
      
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  public ResultSet searchStatement(String table, String field, String value, 
          boolean similar) {
    PreparedStatement stm;
    try {
      String s = String.format("SELECT * FROM %s WHERE %s LIKE %%%s%%", table, field,
          value);
      stm = con.prepareStatement(s);
      return stm.executeQuery();
      
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }
}
