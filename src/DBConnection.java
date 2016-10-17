import javax.xml.transform.Result;
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

  public ResultSet searchStatement(String table) {
    PreparedStatement stm;
    try {
      String s = String.format("SELECT *FROM %s", table);
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
      stm = con.prepareStatement("SELECT * FROM ? WHERE ? LIKE ?");
      stm.setString(1, table);
      stm.setString(2, field);
      stm.setString(3, value);
      return stm.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

}
