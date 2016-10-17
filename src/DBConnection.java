import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private String host = "jdbc:mysql://hotelboxoffice.cornjso48dgu.us-east-1.rds.amazonaws.com:3306/HotelBoxOffice";
	private String username = "masterRoot";
	private String password = "masterRoot";
	private Connection con;
	
	public DBConnection(String[] args) {
		try {
		  con = DriverManager.getConnection(host, username, password);
		} catch (SQLException ex) {
		  System.out.println(ex.getMessage());
		}

		System.out.println("Connected");
	}
	
	public Connection getCon(){
		return con;
	}
	
}
