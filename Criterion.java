import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Criterion<T> extends JPanel{
	private JComboBox<T> list;
	private JCheckBox enable;
	private String column;

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

	public Object getSelectedItem() {
		return list.getSelectedItem();
	}

	public String getColumn() {
		return column;
	}

	public boolean isEnabled() {
		return enable.isSelected();
	}
}
