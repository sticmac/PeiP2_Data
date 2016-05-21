package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.layouthelpers.*;
import javax.swing.*;

/**
 * This class is a container for the results table.
 * Inherit JScrollPane to be able to scroll vertically and horizontally.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class ResultsTable extends JScrollPane {
	private JTable results;

	/**
	 * Creates a new JScrollPane container for the table.
	 */
	public ResultsTable() {
		super();
	}

	/**
	 * Display the results on a JTable object.
	 * Uses the JTableAutoSize layouthelper for auto-sizing the JTable columns.
	 * @param	rowData		array representing the data to display in the table
	 * @param	columnNames	names of the column to display in the header of the table
	 */
	public void displayResults(Object[][] rowData, Object[] columnNames) {
		if(results != null) this.remove(results);
		results = new JTableAutoSize(rowData, columnNames);
		this.getViewport().add(results);
		results.setFillsViewportHeight(true);
	}
}
