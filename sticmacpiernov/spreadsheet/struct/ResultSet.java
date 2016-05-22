package sticmacpiernov.spreadsheet.struct;

/**
 * Represent the dataset of CSV's filtered data along with column names.
 * Used for giving the results from DataCSV filtered data back to GUI.
 *
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public interface ResultSet {
	/**
	 * Returns the column names to display.
	 * @return	columns to display
	 */
	public String[] getColumns();
	/**
	 * Returns the content of the table.
	 * @return	content of the table
	 */
	public String[][] getContent();
}
