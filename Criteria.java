import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Criteria<T> extends JPanel implements ActionListener{
	private JComboBox<T> list;
	private JCheckBox enable;

	public Criteria(T[] items) {
		list = new JComboBox<T>(items);
		enable = new JCheckBox();

		this.setLayout(new FlowLayout());
		this.add(enable);
		this.add(list);

		enable.addActionListener(this);

		list.setEnabled(false); //non activated by default: the user has to activate it
	}

	public Object getSelectedItem() {
		return list.getSelectedItem();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enable) {
			if (enable.isSelected()) {
				list.setEnabled(true);
			}
			else {
				list.setEnabled(false);
			}
		}
	}
}
