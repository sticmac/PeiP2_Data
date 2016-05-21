package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sort extends JPanel {
	private JComboBox<String> sortList;
	private int order = 1;

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
		inc.addActionListener((e) -> this.setOrder(1));
		dec.addActionListener((e) -> this.setOrder(-1));
		sortOrder.add(inc);
		sortOrder.add(dec);
		this.add(dec);
		this.add(inc);
	}

	public String getSelected() {
		return (String)sortList.getSelectedItem();
	}

	private void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}
}
