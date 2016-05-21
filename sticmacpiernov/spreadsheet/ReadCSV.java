package sticmacpiernov.spreadsheet;

import java.io.*;
import java.util.*;

/**
 * This class parses a <code>CSV</code> file.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class ReadCSV {
	private final String DEFFILENAME = "fr-esr-insertion_professionnelle-master.csv"; // default file name
	private final char DELIM = ';'; // CSV delimiter
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> columns;

	/**
	 * Reads a CSV-formatted text file.
	 * Column names (first line of CSV file) are stored in their of ArrayList<String>.
	 * Each line is splitted according to the DELIM delimiter and the result is stored in an ArrayList<String>.
	 * All lines are stored in an ArrayList<ArrayList<String>>.
	 * @param	filename	the CSV file name to parse
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
			// First reads column names
			String linestr = br.readLine();
			this.columns = new ArrayList<String>();
			this.data = new ArrayList<ArrayList<String>>();
			readColumns(linestr, this.columns); //TODO: replace by String.split(regexp) -> see if it's slower or not

			// Then the actual data
			linestr = br.readLine();
			while(linestr != null) {
				ArrayList<String> line = new ArrayList<String>();
				readColumns(linestr, line);
				this.data.add(line);
				linestr = br.readLine();
			}
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(3);
		}
		finally {
			try {
				br.close(); // Always close the file — well, not if we already exited
			}
			catch (IOException e) {
				System.err.println(e);
				System.exit(4);
			}
		}

	}

	/**
	 * Parses the default <code>CSV</code> file.
	 */
	public ReadCSV() {
		this(null);
	}

	/**
	 * Splits a line according to the DELIM delimiter and stores the result in the given ArrayList<String> line.
	 * @param	linestr	the line to split
	 * @param	line	the list to store the result to
	 */
	private void readColumns(String linestr, ArrayList<String> line) {
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

	/**
	 * Returns the index of the given column
	 * @param	name	the column name
	 * @return	the index of the given column, -1 when not found
	 */
	public int findIndexForColumn(String name) {
		for (int i = 0 ; i < columns.size() ; i++) {
			if (this.columns.get(i).equals(name)) return i;
		}
		return -1;
	}

	/**
	 * Returns the list of the parsed columns name from the <code>CSV</code> file
	 * @return	the list of the columns name from the <code>CSV</code> file
	 */
	public ArrayList<String> getColumns() {
		return this.columns;
	}

	/**
	 * Return the whole data from the <code>CSV</code> file (without column names)
	 * @return	data from the <code>CSV</code> file
	 */
	public ArrayList<ArrayList<String>> getData() {
		return this.data;
	}
}
