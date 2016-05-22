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
		ReadCSV csv;
		if(args.length > 0)
			csv = new ReadCSV(args[0]);
		else
			csv = new ReadCSV();
		GUI gui = new GUI(new DataCSV(csv));
	}
}
