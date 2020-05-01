package GDB;
import org.apache.log4j.Logger;

public enum Prints {
	CUSTOMER("Information about customers"),
	GAME("Individual Games"),
	ORDER("Purchases of items"),
	QUIT("To close the application");
	
	public static final Logger LOGGER = Logger.getLogger(Prints.class);
	private String description;
	
	Prints(String description) {
		this.description = description;// TODO Auto-generated constructor stub
	}

	//
	public String getDescription() {
		return this.name() + ": " +this.description;
	}
	
	//Loops through enums and prints each one to screen
	public static void print() {
		for (Prints print : Prints.values()) {
			LOGGER.info(print.getDescription());
		}
	}
	
	
	//gets user inputs converts it to uppercase and compares to enum
	public static Prints getPrints() {
		Prints print;
		while (true) {
			try {
				print = Prints.valueOf(Utils.getInput().toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return print;
	}
	
}
