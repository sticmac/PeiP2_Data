import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <code>Criterion</code> class
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 * @version 1.0
 */
class Criterion<T> extends JPanel{
	private JComboBox<T> list;
	private JCheckBox enable;
	private String column;

	/**
	 * Construct one <code>Criterion</code> on the given column with the given items
	 * @param column name on the column which the <code>Criterion</code> is on
	 * @param items the content of the column
	 */
	public Criterion(String column, T[] items) {
		this.column = column;
		this.list = new JComboBox<T>(items);
		this.enable = new JCheckBox();

		this.setLayout(new FlowLayout());
		this.add(new JLabel(column));
		this.add(enable);
		this.add(list);

		enable.addItemListener((e) -> list.setEnabled(e.getStateChange() == ItemEvent.SELECTED));

		list.setEnabled(false); //non activated by default: the user has to activate it
	}

	@Override
	public Object getSelectedItem() {
		return list.getSelectedItem();
	}

	/**
	 * Return the name of the column which the <code>Criterion</code> is on
	 * @return the name of the column which the <code>Criterion</code> is on
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * Return if the <code>Criterion</code> is enabled (selected) or not
	 * @return true if the <code>JCheckBox</code> is selected
	 */
	public boolean isEnabled() {
		return enable.isSelected();
	}
}
