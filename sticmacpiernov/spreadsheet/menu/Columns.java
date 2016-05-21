package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;
import java.util.ArrayList;
import sticmacpiernov.spreadsheet.layouthelpers.*;

public class Columns extends JMenu {
	public Columns(String[] columnsName, String[] defaultSelection) {
		super("Columns");
		ArrayList<JCheckBoxMenuItem> selectColumnsButtons = new ArrayList<JCheckBoxMenuItem>();
		for(String column: columnsName) {
			JCheckBoxMenuItem columnButton = new JCheckBoxMenuItem(column);
			columnButton.setUI(new StayOpenCheckBoxMenuItemUI());
			for(String c: defaultSelection) {
				if(c.equals(column)) columnButton.setSelected(true); // Set default checkbox state
			}
			selectColumnsButtons.add(columnButton);
			this.add(columnButton);
		}
	}

	public ArrayList<String> getSelected() {
		ArrayList<String> list = new ArrayList<String>();
		for(int i =0; i < this.getItemCount(); i++) {
			JMenuItem item = this.getItem(i);
			if(item.isSelected()) list.add(this.getItem(i).getText());
		}
		return list;
	}
}

