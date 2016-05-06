import java.io.*;
import java.util.*;
//import java.util.stream.*;
//import java.util.function.*;

public class ReadCSV {
	private final String DEFFILENAME = "fr-esr-insertion_professionnelle-master.csv";
	private final char DELIM = ';';
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> columns;

	private void readColumns(String linestr, ArrayList<String> line) throws IOException {
		if(linestr == null) return;
		line.add("");
		int j = 0;
		for(int i = 0; i < linestr.length(); i++) {
			char c = linestr.charAt(i);
			if(c == DELIM) {
				j++;
				line.add("");
			}
			else line.set(j, line.get(j)+c);
		}
	}

	public int findIndexForColumn(String name) {
		for (int i = 0 ; i < columns.size() ; i++) {
			if (columns.get(i).equals(name)) return i;
		}
		return -1;
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public ArrayList<ArrayList<String>> getData() {
		return this.data;
	}

	/**
	 * Reads a text file containing one word per line and stores it in the object.
	 */
	public ReadCSV(String filename) {
		if(filename == null) {
			System.err.println("No file name given. Using default: "+DEFFILENAME);
			filename = DEFFILENAME;
		}

		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(filename)); // Open the file
		}
		catch(FileNotFoundException e) {
			System.err.println("Cannot open " + filename + ": File not found.");
			System.exit(2);
		}
		try{
			String linestr = br.readLine();
			columns = new ArrayList<String>();
			data = new ArrayList<ArrayList<String>>();
			readColumns(linestr, columns); //TODO: replace by String.split(regexp) -> see if it's slower or not
			linestr = br.readLine();
			while(linestr != null) {
				ArrayList<String> line = new ArrayList<String>();
				readColumns(linestr, line);
				data.add(line);
				linestr = br.readLine();
			}
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(3);
		}
		finally {
			try {
				br.close(); // Always close the file
			}
			catch (IOException e) {
				System.err.println("Cannot close file: Input/Output error.");
				System.exit(4);
			}
		}

	}

	public ReadCSV() {
//		System.err.println("No file name given. Using default: "+DEFFILENAME);
		this(null);
	}
}
