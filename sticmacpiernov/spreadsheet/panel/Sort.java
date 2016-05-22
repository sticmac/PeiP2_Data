package sticmacpiernov.spreadsheet.panel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class displays component on the user interface for choosing the sorting column and the order.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Sort extends JPanel {
	private JComboBox<String> sortList;
	private int order = 1;

	/**
	 * Creates the panel containing the sorting options.
	 * @param	columnsName	available columns to sort on
	 */
	public Sort(String[] columnsName) {
		super();
		// Search options panel
		sortList = new JComboBox<String>(columnsName);

		// Sort panel
		this.setLayout(new FlowLayout());
		this.add(new JLabel("Sort by: "));
		this.add(sortList);

		// Sort order
		ButtonGroup sortOrder = new ButtonGroup();
		JRadioButton dec = new JRadioButton("Descending");
		JRadioButton inc = new JRadioButton("Ascending");
		inc.setSelected(true);
		dec.addActionListener((e) -> this.setOrder(-1));
		inc.addActionListener((e) -> this.setOrder(1));
		sortOrder.add(inc);
		sortOrder.add(dec);
		this.add(dec);
		this.add(inc);
	}

	/**
	 * Returns the selected columns to sort on.
	 * @return	the selected sorting column
	 */
	public String getSelected() {
		return (String)sortList.getSelectedItem();
	}

	/**
	 * Used internally to save the specified order when clicking on a radio button.
	 * @param	order	new order: 1 for ascending, -1 for descending
	 */
	private void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Returns the current selected order.
	 * @return	1 for ascending, -1 for descending
	 */
	public int getOrder() {
		return order;
	}
}
