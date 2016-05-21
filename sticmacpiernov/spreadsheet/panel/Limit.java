package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.*;
import java.text.NumberFormat;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class Limit extends JPanel {
	private JFormattedTextField field;

	public Limit() {
		super();

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		field = new JFormattedTextField(formatter);
		field.setValue(10); // Default value: 10

		this.setLayout(new FlowLayout());
		this.add(new JLabel("Limit results to: "));
		this.add(field);
	}

	public int get() {
		return (int)field.getValue(); // Is it safe?
	}
}
