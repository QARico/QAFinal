package GDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class Customer {
	Utils utils = new Utils();

	public Customer() {

	}

	public static final Logger LOGGER = Logger.getLogger(Customer.class);

	Connection conn = null;
	Statement stmt = null;

	public void add() {

		String query = " insert into customer (customerId, fName, sName, email, city, postcode, addressLine, primaryNumber, secondaryNumber, dob)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		// PreparedStatement preparedStmt;

		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			LOGGER.info("\n ========== Enter First Name ==========\n");
			String fname = Utils.getInput();
			preparedStmt.setString(2, fname);
			LOGGER.info("\n ========== Enter Last Name ==========\n");
			String sname = Utils.getInput();
			preparedStmt.setString(3, sname);
			LOGGER.info("\n ========== Enter Email ==========\n");
			String email = Utils.getInput();
			preparedStmt.setString(4, email);
			LOGGER.info("\n ========== Enter City ==========\n");
			String city = Utils.getInput();
			preparedStmt.setString(5, city);
			LOGGER.info("\n ========== Enter Postcode ==========\n");
			String postcode = Utils.getInput();
			preparedStmt.setString(6, postcode);
			LOGGER.info("\n ========== Enter Address ==========\n");
			String addr = Utils.getInput();
			preparedStmt.setString(7, addr);
			LOGGER.info("\n ========== Enter Phone Number ==========\n");
			int pnum = Utils.getInput2();
			preparedStmt.setInt(8, pnum);
			LOGGER.info("\n ========== Enter Alt Number ==========\n");
			int snum = Utils.getInput2();
			preparedStmt.setInt(9, snum);
			preparedStmt.setDate(10, Utils.date);

			// execute the preparedstatement
			preparedStmt.execute();
			LOGGER.info("\n============= Customer Added ==============\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void view() {
		String query = "SELECT * FROM customer";
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int customerId = rs.getInt("customerId");
				String fname = rs.getString("fName");
				String sname = rs.getString("sName");
				String email = rs.getString("email");
				String city = rs.getString("city");
				String pcode = rs.getString("postcode");
				String addr = rs.getString("addressLine");
				int pNumber = rs.getInt("primaryNumber");
				int sNumber = rs.getInt("secondaryNumber");
				Date dob = rs.getDate("dOB");
				LOGGER.info("\nCustomerID: " + customerId + "/First Name: " + fname + "\nSurname: " + sname
						+ "\nEmail: " + email + "\nCity: " + city + "\nPostCode: " + pcode + "\nAddress: " + addr
						+ "\nPhone Number: " + pNumber + "\nAlt Number:  " + sNumber + "\nDOB " + dob);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete() {
		String query = "DELETE FROM customer WHERE customerId=?";

		PreparedStatement statement;
		try {
			conn = DriverManager.getConnection(Utils.getDbUrl(), Utils.getUser(), Utils.getPass());
			statement = conn.prepareStatement(query);
			LOGGER.info("=========== Enter ID ============");
			int id = Utils.getInput2();
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				LOGGER.info("A customer was deleted successfully");
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
		LOGGER.info("===========      Enter ID Of The Customer     ============");
		String whereidis = Utils.getInput();

		String query = "UPDATE customer SET " + set + " = '" + setto + "' WHERE customerId = '" + whereidis + "'";
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
		LOGGER.info("1 - Create, 2 - read, 3 - update, or 4 - delete");
		int process = Utils.getInput2();
		boolean running = true;
		Customer customer = new Customer();
		while (running) {
			switch (process) {
			case 1:
				customer.add();
				running = false;
				break;
			case 2:
				customer.view();
				running = false;
				break;
			case 3:
				customer.update();
				running = false;
				break;
			case 4:
				customer.delete();
				running = false;
				break;
			case 5:
				LOGGER.info("ending system");
				running = false;
			}
		}
	}
}
