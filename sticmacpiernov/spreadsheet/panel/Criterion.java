package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.struct.Filter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.*;

/**
 * This class represents a criterion selectable in the graphical user interface.
 * A Criterion is a Filter with additional functionalities for user interaction.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Criterion extends JPanel implements Filter {
	private JComboBox<String> list;
	private JComboBox<String> column;
	private static Supplier<String[]> getColumnsName; // method from the DataCSV object
	private static Function<String, String[]> getColumnValues; // method from the DataCSV object
	private static Consumer<Criterion> remove; // remove method from the containing Criteria panel

	/**
	 * Configure the method to call to get the list of the columns name, given by the Criteria container.
	 * @param	columnsName	method to call to get the columns name
	 */
	public static void setNames(Supplier<String[]> columnsName) {
		Criterion.getColumnsName = columnsName;
	}

	/**
	 * Configure the method to call to get the list of values for a specific column, given by the Criteria container.
	 * @param	getColumnValues	method to call to get the list of values for a column
	 */
	public static void setValues(Function<String, String[]> getColumnValues) {
		Criterion.getColumnValues = getColumnValues;
	}

	/**
	 * Configure the method to call deletion of this object, given by the Criteria container.
	 * @param	remove	method to call for deleting this criterion from the user interface
	 */
	public static void setRemove(Consumer<Criterion> remove) {
		Criterion.remove = remove;
	}

	/**
	 * Construct one <code>Criterion</code> on the given column.
	 * @param	column	name on the column which the <code>Criterion</code> is on
	 */
	public Criterion(String column) {
		if(column == null) return;
		this.column = new JComboBox<String>(getColumnsName.get()); // get all the available colunm names
		this.list = new JComboBox<String>(getColumnValues.apply(column)); // and the available values for the given column

		this.setLayout(new FlowLayout());
		this.column.setSelectedItem(column);
		this.add(this.column);
		this.add(new JCheckBox() { { addItemListener((e) -> list.setEnabled(e.getStateChange() == ItemEvent.SELECTED)); } } ); // enable the value selection list when checking the box
		this.add(list);
		this.add(new JButton("Remove") { { addActionListener((e) -> Criterion.this.remove.accept(Criterion.this)); } } ); // remove the criterion from the container when clicking on the button

		this.column.addActionListener((e) -> { // Refresh the values when changing the column selection
			this.list.removeAllItems();
			for(String s: getColumnValues.apply((String)this.column.getSelectedItem())) this.list.addItem(s);
		});

		list.setEnabled(false); //non activated by default: the user has to activate it
	}

	/**
	 * Add a Criterion with the first column as default.
	 */
	public Criterion() {
		this(getColumnsName.get()[0]);
	}

	/**
	 * Returns the selected item from the <code>JComboBox</code> in the <code>Criterion</code>.
	 * Implements the required Filter method.
	 * @return	the selected item in the <code>Criterion</code>
	 */
	@Override
	public String getValue() {
		return (String)list.getSelectedItem();
	}

	/**
	 * Return the name of the column which the <code>Criterion</code> is on.
	 * Implements the required Filter method.
	 * @return	the name of the column which the <code>Criterion</code> is on
	 */
	@Override
	public String getColumn() {
		return (String)column.getSelectedItem();
	}

	/**
	 * Return wether the <code>Criterion</code> is enabled (selected) or disabled.
	 * @return	true if the <code>JCheckBox</code> is selected
	 */
	public boolean isEnabled() {
		return list.isEnabled();
	}
}
