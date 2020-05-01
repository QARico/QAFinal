package GDB;

import java.util.Calendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Utils {

	public static final Scanner SCANNER = new Scanner(System.in);
	static Calendar calendar = Calendar.getInstance();
	// create a sql date object so i can use it in my INSERT statement
	public static Date date = new Date(calendar.getTime().getTime());
	
	private static final String DB_URL = "jdbc:mysql://34.89.61.63/gameManagement?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "root";
	private static PreparedStatement preparedStmt;

	
	public static PreparedStatement getPreparedStmt() {
		return preparedStmt;
	}

	public static void setPreparedStmt(PreparedStatement preparedStmt) {
		Utils.preparedStmt = preparedStmt;
	}

	public static String getDbUrl() {
		return DB_URL;
	}

	public static String getUser() {
		return USER;
	}

	public static String getPass() {
		return PASS;
	}

	
	public Utils() {

	}

	public static String getInput() {
		return SCANNER.nextLine();
	}
	
	public static int getInput2() {
		return SCANNER.nextInt();
	}
	
	public static double getInput3() {
		return SCANNER.nextDouble();
	}
}
