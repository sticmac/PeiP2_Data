package sticmacpiernov.spreadsheet.panel;

import java.text.NumberFormat;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * This class contains a text box used to specify the number of results to display.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Limit extends JPanel {
	private JFormattedTextField field;

	/**
	 * Creates a new input for the user to enter the number of elements to display.
	 * Only integers are allowed thanks to a JFormattedTextField.
	 */
	public Limit() {
		super();

		// Allow only integer to be typed in the text field
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

	/**
	 * Returns the value of the text field.
	 * @return	value entered by the user
	 */
	public int get() {
		return (int)field.getValue(); // Is it safe?
	}
}
