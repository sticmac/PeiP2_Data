import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Criteria<T> extends JPanel{
	private JComboBox<T> list;
	private JCheckBox enable;

	public Criteria(T[] items) {
		list = new JComboBox<T>(items);
		enable = new JCheckBox();

		this.setLayout(new FlowLayout());
		this.add(enable);
		this.add(list);

		enable.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				list.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
			}
		});

		list.setEnabled(false); //non activated by default: the user has to activate it
	}

	public Object getSelectedItem() {
		return list.getSelectedItem();
	}
}
