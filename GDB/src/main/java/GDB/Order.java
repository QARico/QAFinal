package GDB;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class Order {
	Utils utils = new Utils();

	public Order() {

	}

	public static final Logger LOGGER = Logger.getLogger(Order.class);

	Connection conn = null;
	Statement stmt = null;

	public void add() {

		String query = " insert into order (orderId, fk_gameId, date, status)" + " VALUES (?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		// PreparedStatement preparedStmt;

		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, 0);
			preparedStmt.setDate(3, Utils.date);
			LOGGER.info("\n ========== Status ==========\n");
			String status = Utils.getInput();
			preparedStmt.setString(4, status);
			// execute the preparedstatement
			preparedStmt.execute();
			LOGGER.info("\n============= Order Added ==============\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void view() {
		String query = "SELECT * FROM order";
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int orderId = rs.getInt("orderId");
				String fgid = rs.getString("fk_gameId");
				Date date = rs.getDate("date");
				String status = rs.getString("status");
				LOGGER.info(
						"\norderID: " + orderId + "\nfk_gameId: " + fgid + "\nDate: " + date + "\nStatus: " + status);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete() {
		String query = "DELETE FROM order WHERE orderId=?";

		PreparedStatement statement;
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			statement = conn.prepareStatement(query);
			LOGGER.info("=========== Enter ID ============");
			int id = Utils.getInput2();
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				LOGGER.info("A order was deleted successfully");
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
		LOGGER.info("===========      Enter ID Of The order     ============");
		String whereidis = Utils.getInput();

		String query = "UPDATE order SET " + set + " = '" + setto + "' WHERE orderId = '" + whereidis + "'";
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
		Order order = new Order();
		while (running) {
			switch (process) {
			case 1:
				order.add();
				running = false;
				break;
			case 2:
				order.view();
				running = false;
				break;
			case 3:
				order.update();
				running = false;
				break;
			case 4:
				order.delete();
				running = false;
				break;
			case 5:
				LOGGER.info("ending system");
				running = false;
			}
		}
	}
}
