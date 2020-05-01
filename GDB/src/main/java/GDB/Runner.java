package GDB;

import org.apache.log4j.BasicConfigurator;

public class Runner {
	public static void main(String[] args) {
		MainSystem ms = new MainSystem();
		BasicConfigurator.configure();
		ms.System();

	}
}
