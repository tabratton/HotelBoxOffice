import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	private String host = "jdbc:mysql://hotelboxoffice.cornjso48dgu.us-east-1.rds.amazonaws.com:3306/HotelBoxOffice";
	private String username = "masterRoot";
	private String password = "masterRoot";
	private Connection con;
	
	public DBConnection() {
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
	
	public ResultSet searchStatement(String table, String field,
			String value) {
		PreparedStatement stm;
		try{
			stm = con.prepareStatement("SELECT * FROM ? WHERE ? LIKE %?%");
			stm.setString(1, table);
			stm.setString(2, field);
			stm.setString(3, value);
			ResultSet resultSet = stm.executeQuery();
			return resultSet;
		} catch (SQLException e){
			return null;
		}
	}
	
}
