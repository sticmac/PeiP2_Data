import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.*;

/**
 * <code>Criterion</code> class
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 * @version 1.0
 */
class Criterion extends JPanel{
	private JComboBox<String> list;
	private JComboBox<String> column;
	private static Supplier<String[]> getColumnsName;
	private static Function<String, String[]> getColumnValues;
	private static Consumer<Criterion> remove;

	public static void setNames(Supplier<String[]> columnsName) {
		Criterion.getColumnsName = columnsName;
	}

	public static void setValues(Function<String, String[]> getColumnValues) {
		Criterion.getColumnValues = (Function<String, String[]>) getColumnValues;
	}

	public static void setRemove(Consumer<Criterion> remove) {
		Criterion.remove = remove;
	}

	/**
	 * Construct one <code>Criterion</code> on the given column with the given items
	 * @param column name on the column which the <code>Criterion</code> is on
	 * @param items the content of the column
	 */
	public Criterion(String column) {
		this.column = new JComboBox<String>(getColumnsName.get());
		this.list = new JComboBox<String>(getColumnValues.apply(column));

		this.setLayout(new FlowLayout());
		this.column.setSelectedItem(column);
		this.add(this.column);
		this.add(new JCheckBox() { { addItemListener((e) -> list.setEnabled(e.getStateChange() == ItemEvent.SELECTED)); } } );
		this.add(list);
		this.add(new JButton("Remove") { { addActionListener((e) -> Criterion.this.remove.accept(Criterion.this)); } } );

		this.column.addActionListener((e) -> {
			this.list.removeAllItems();
			for(String s: getColumnValues.apply((String)this.column.getSelectedItem())) this.list.addItem(s);
		});

		list.setEnabled(false); //non activated by default: the user has to activate it
	}

	/**
	 * Returns the selected item from the <code>JComboBox</code> in the <code>Criterion</code>
	 * @return the selected item in the <code>Criterion</code>
	 */
	public Object getSelectedItem() {
		return list.getSelectedItem();
	}

	/**
	 * Return the name of the column which the <code>Criterion</code> is on
	 * @return the name of the column which the <code>Criterion</code> is on
	 */
	public String getColumn() {
		return (String)column.getSelectedItem();
	}

	/**
	 * Return if the <code>Criterion</code> is enabled (selected) or not
	 * @return true if the <code>JCheckBox</code> is selected
	 */
	public boolean isEnabled() {
		return list.isEnabled();
	}
}
