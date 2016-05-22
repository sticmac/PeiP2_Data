package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.*;
import sticmacpiernov.spreadsheet.struct.Filter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * This class represents the list of criteria displayed on the user interface.
 * Declared as asbstract for the pack() method to be given by the JFrame.
 * Implements ActionListener for handling event of the Add button.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public abstract class Criteria extends JPanel implements ActionListener {
	/**
	 * Creates a new panel for display and adding criteria.
	 * @param	csv	a parsed CSV file to read the columns name and value from
	 */
	public Criteria(DataCSV csv) {
		super();
		// Criteria selection panel using BoxLayout
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Configure Criterion
		Criterion.setNames(csv::getColumnsName);
		Criterion.setValues(csv::getColumnValues);
		Criterion.setRemove((b) -> {this.remove(b); this.pack();}); // Give the method to remove an element from this panel

		// Add default criteria to the user interface
		for(String c: Config.defaultCriteria) this.add(new Criterion(c));
	}

	/**
	 * pack() method to be given by the calling JFrame.
	 */
	protected abstract void pack();

	/**
	 * Add a new criteria to the list when the ActionListener is triggered.
	 * Acts as an event handler for the Add button.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.add(new Criterion());
		this.pack(); // pack() method from JFrame
	}

	/**
	 * Returns the list of the selected filters.
	 * The criteria are represented as filters because the result should not have anything to do with the GUI.
	 * @return	list of selected filters
	 */
	public ArrayList<Filter> getFilters() {
		ArrayList<Filter> list = new ArrayList<Filter>();
		for (Component cp : this.getComponents()) {
			Criterion c = (Criterion)cp;
			if(!c.isEnabled()) continue;
			list.add(c);
		}
		return list;
	}
}
