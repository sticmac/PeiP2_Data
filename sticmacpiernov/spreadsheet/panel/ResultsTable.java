package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.*;
import sticmacpiernov.spreadsheet.layouthelpers.*;
import javax.swing.*;

public class ResultsTable extends JScrollPane {
	private JTable results;

	public ResultsTable() {
		super();
	}

	/**
	 * Display the results on a JTable object
	 */
	public void displayResults(Object[][] rowData, Object[] columnNames) {
		if(results != null) this.remove(results);
		results = new JTableAutoSize(rowData, columnNames);
		this.getViewport().add(results);
		results.setFillsViewportHeight(true);
	}
}
