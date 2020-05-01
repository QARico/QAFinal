package GDB;

import org.apache.log4j.Logger;

public class MainSystem {
	public static final Logger LOGGER = Logger.getLogger(MainSystem.class);

	Connections c = new Connections();
	Customer customer = new Customer();
	Game game = new Game();
	Order order = new Order();
	User user = new User();

	public String username;
	public String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void System() {

		// Connects To The Database
		c.Connect();

		// Takes in username & password
		LOGGER.info("What is your username");
		username = Utils.getInput();
		LOGGER.info("What is your password");
		password = Utils.getInput();

		// finds username in username table and matches the username and password

		if (c.loginConnect(username, password) == false) {
			int count = 0;
			int tries = 3;
			while (c.loginConnect(username, password) == false) {
				if (++count != tries) {
					LOGGER.fatal("\n ----------- Invalid Credentials -------------\n");
					LOGGER.info("What is your username");
					username = Utils.getInput();
					LOGGER.info("What is your password");
					password = Utils.getInput();
				}
			}
		}

		LOGGER.info("\n------------Which entity would you like to use ?------------\n");

		LOGGER.info("\nPress 1 For Customers \nPress 2 For Games \nPress 3 For Orders \nPress 4 for User\n5TO Exit");
		int tableNames = Utils.getInput2();
		boolean running = true;

		while (running) {
			switch (tableNames) {
			case 1:
				customer.tableLoop();
				break;
			case 2:
				game.tableLoop();
				break;
			case 3:
				order.tableLoop();
				break;
			case 4:
				user.tableLoop();
				break;
			case 5:
				LOGGER.info("Closing Program");
				running = false;
				System.exit(0);
				break;

			default:
				break;
			}
		}
	}
}
