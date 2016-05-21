package sticmacpiernov.spreadsheet;

public class Spreadsheet {
	public static void main(String[] args) {
		GUI gui = new GUI(new DataCSV(new ReadCSV()));
	}
}
