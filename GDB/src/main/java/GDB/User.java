package GDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class User {
	Utils utils = new Utils();

	public User() {

	}

	public static final Logger LOGGER = Logger.getLogger(User.class);

	Connection conn = null;
	Statement stmt = null;

	public void add() {

		String query = " insert into user (userId, username, password, rankId)"
				+ " VALUES (?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		// PreparedStatement preparedStmt;

		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			LOGGER.info("\n ========== Enter Username ==========\n");
			String username = Utils.getInput();
			preparedStmt.setString(2, username);
			LOGGER.info("\n ========== Enter Password ==========\n");
			String password = Utils.getInput();
			preparedStmt.setString(3, password);
			LOGGER.info("\n ========== Enter Rank 1 - 3 ==========\n");
			int rankId = Utils.getInput2();
			preparedStmt.setInt(4, rankId);

			// execute the preparedstatement
			preparedStmt.execute();
			LOGGER.info("\n============= User Added ==============\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void view() {
		String query = "SELECT * FROM user";
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int userId = rs.getInt("userId");
				String fname = rs.getString("username");
				String sname = rs.getString("password");
				String rank = rs.getString("rankId");
				
				LOGGER.info("\nUserID: " + userId + "/nUsername: " + fname + "\nPassword: " + sname
						+ "\nRank 1 - 3: " + rank);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete() {
		String query = "DELETE FROM user WHERE userId=?";

		PreparedStatement statement;
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			statement = conn.prepareStatement(query);
			LOGGER.info("=========== Enter ID ============");
			int id = Utils.getInput2();
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				LOGGER.info("A user was deleted successfully");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean update() {
		PreparedStatement preparedStmt;
		boolean success = false;
		LOGGER.info("=========== Column Of Item You Want To Change ============");
		String set = Utils.getInput();
		LOGGER.info("===========   What Do You Want To Set It To   ============");
		String setto = Utils.getInput();
		LOGGER.info("===========      Enter ID Of The user     ============");
		String whereidis = Utils.getInput();

		String query = "UPDATE user SET " + set + " = '" + setto + "' WHERE userId = '" + whereidis + "'";
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			success = true;
			LOGGER.info("updated");
		} catch (SQLException e) {
			// TODO manually typing this is dumb
			e.printStackTrace();
		}
		return success;
	}
	
	public void tableLoop() {
		LOGGER.info("1 - Create, 2 - read, 3 - update, or 4 - delete, 5 - return");
		int process = Utils.getInput2();
		boolean running = true;
		User user = new User();
		while (running) {
			switch (process) {
			case 1:
				user.add();
				running = false;
				break;
			case 2:
				user.view();
				running = false;
				break;
			case 3:
				user.update();
				running = false;
				break;
			case 4:
				user.delete();
				running = false;
				break;
			case 5:
				LOGGER.info("ending system");
				running = false;
			}
		}
	}
}
