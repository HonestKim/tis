package tis_pos;
import java.sql.*;
public class DBUtil {
	static String url="jdbc:oracle:thin:@192.168.0.92:1521:XE";
	static String user="tis";
	static String pwd="1234";

	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("성공");
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	public static Connection getCon()
	throws SQLException
	{
		Connection con =DriverManager.getConnection(url,user,pwd);
		return con;
	}

}
