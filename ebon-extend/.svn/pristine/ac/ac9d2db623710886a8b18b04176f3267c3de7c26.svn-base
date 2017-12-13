import java.sql.Connection;

public class TestConn {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@174.34.50.217:1521:p6r8db";
		String username = "ebon";
		String password = "ebon";

		try {
			Class.forName(driver);

			Connection conn = java.sql.DriverManager.getConnection(url,
					username, password);
			System.out.println(conn.getMetaData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
