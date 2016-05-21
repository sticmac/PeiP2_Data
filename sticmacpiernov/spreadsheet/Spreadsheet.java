package sticmacpiernov.spreadsheet;

/**
 * The main class which contains the method main().
 * This the class to run in order to execute the program.
 *
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Spreadsheet {
	public static void main(String[] args) {
		GUI gui = new GUI(new DataCSV(new ReadCSV()));
	}
}
