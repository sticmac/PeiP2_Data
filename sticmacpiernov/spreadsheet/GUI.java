package sticmacpiernov.spreadsheet;

import sticmacpiernov.spreadsheet.*;
import sticmacpiernov.spreadsheet.menu.*;
import sticmacpiernov.spreadsheet.panel.*;
import sticmacpiernov.spreadsheet.struct.ResultSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class creates a window (JFrame object).
 * Handles the graphical user interface.
 *
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
class GUI extends JFrame {
	private DataCSV csv;

	private ResultsTable resultsTable;
	private Criteria criteria;
	private Sort sort;
	private Limit limit;
	private Columns columnsMenu;

	/**
	 * Creates a new GUI object using the given CSV data.
	 * @param	csv	a parsed CSV file
	 */
	public GUI(DataCSV csv) {
		this.csv = csv;

		// Main layout
		getContentPane().setLayout(new BorderLayout());

		// Window title
		setTitle(Config.windowTitle);

		// Menu bar
		JMenuBar bar = new JMenuBar();

		// Options menu
		bar.add(new Options());
		// Plus menu
		bar.add(new Plus());
		//Columns menu
		columnsMenu = new Columns(csv.getColumnsName(), Config.defaultColumns);
		bar.add(columnsMenu);

		this.setJMenuBar(bar); // Add menu bar to window

		// Search options box
		Box choices = new Box(BoxLayout.Y_AXIS);

		// Criteria selection panel
		criteria = new Criteria(csv) { @Override protected void pack() { GUI.this.pack(); } };
		choices.add(criteria);

		// Add criterion button
		choices.add(new JButton("Add") { { addActionListener(criteria); } });

		// Sort panel
		sort = new Sort(csv.getColumnsName());
		choices.add(sort);

		// Limit panel
		limit = new Limit();
		choices.add(limit);

		// Search button, runs the processSearch() method below when clicked
		choices.add(new JButton("Search") { { addActionListener((e) -> GUI.this.processSearch()); } });

		this.add(choices, BorderLayout.WEST);

		// Results table
		resultsTable = new ResultsTable();
		this.add(resultsTable, BorderLayout.CENTER);

		setVisible(true);
		pack();
	}

	/**
	 * Execute the requested search
	 */
	private void processSearch() {
		ResultSet results = csv.processSearch(sort.getSelected(), sort.getOrder(), columnsMenu.getSelected(), limit.get(), criteria.getFilters());
		resultsTable.displayResults(results.getContent(), results.getColumns());
		this.pack();
	}
}
