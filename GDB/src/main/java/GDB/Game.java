package GDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Game {
	Utils utils = new Utils();

	public Game() {

	}

	public static final Logger LOGGER = Logger.getLogger(Game.class);

	Connection conn = null;
	Statement stmt = null;

	public void add() {

		String query = " insert into game (gameId, name, price, ageRating, stockQty)"
				+ " VALUES (?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		// PreparedStatement preparedStmt;

		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			LOGGER.info("\n ========== Enter Name ==========\n");
			String name = Utils.getInput();
			preparedStmt.setString(2, name);
			LOGGER.info("\n ========== Enter Price ==========\n");
			double price = Utils.getInput3();
			preparedStmt.setDouble(3, price);
			LOGGER.info("\n ========== Enter Age Rating ==========\n");
			int age = Utils.getInput2();
			preparedStmt.setInt(4, age);
			LOGGER.info("\n ========== Stock Qty ==========\n");
			int sqty = Utils.getInput2();
			preparedStmt.setInt(5, sqty);
			// execute the preparedstatement
			preparedStmt.execute();
			LOGGER.info("\n============= Game Added ==============\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void view() {
		String query = "SELECT * FROM game";
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int gameId = rs.getInt("gameId");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int age = rs.getInt("ageRating");
				int stock = rs.getInt("stockQty");

				LOGGER.info("\ngameID: " + gameId + "/Name: " + name + "\nPrice: " + price
						+ "\nAge: " + age + "\nStock: " + stock);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete() {
		String query = "DELETE FROM game WHERE gameId=?";

		PreparedStatement statement;
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			statement = conn.prepareStatement(query);
			LOGGER.info("=========== Enter ID ============");
			int id = Utils.getInput2();
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				LOGGER.info("A game was deleted successfully");
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
		LOGGER.info("===========      Enter ID Of The game     ============");
		String whereidis = Utils.getInput();

		String query = "UPDATE game SET " + set + " = '" + setto + "' WHERE gameId = '" + whereidis + "'";
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
		LOGGER.info("1 - Create, 2 - read, 3 - update, 4 - delete, 5 - to go back");
		int process = Utils.getInput2();
		boolean running = true;
		Game game = new Game();
		while (running) {
			switch (process) {
			case 1:
				game.add();
				running = false;
				break;
			case 2:
				game.view();
				running = false;
				break;
			case 3:
				game.update();
				running = false;
				break;
			case 4:
				game.delete();
				running = false;
				break;
			case 5:
				LOGGER.info("ending system");
				running = false;
			}
		}
	}
}
