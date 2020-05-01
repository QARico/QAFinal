package GDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import java.sql.ResultSet;

public class Connections {
	Utils utils = new Utils();
	public static final Logger LOGGER = Logger.getLogger(Connections.class);

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static Connection conn = null;
	public static Statement stmt = null;

	public Connections() {
		
	}

	public boolean loginConnect(String username, String pass) {
		boolean success = false;

		try {
			String query = "SELECT `username`,`password` FROM `gameManagement`.`user` WHERE `username` = '" + username
					+ "'";
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				if (pass.equals(rs.getString("password"))) {
					LOGGER.info("\nLogin Success");
					LOGGER.info("\n ----------- Welcome " + username + " -------------\n");
					success = true;
				}
			} else {
				LOGGER.error("Login Failed");
				success = false;
			}
		} catch (SQLException e1) {
			LOGGER.error(e1);// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return success;
	}

	public void Connect() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Starting connection to db");

		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Connected!!");

	}

	public void disconnect() {
		boolean off = false;
		try {
			stmt.close();
			conn.close();
			LOGGER.info("Connection CLosed");
			off=true;
		} catch (Exception sqlException) {
			sqlException.printStackTrace();// TODO: handle exception
		}
	}



}
