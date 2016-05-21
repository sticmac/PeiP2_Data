package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;
import java.util.ArrayList;
import sticmacpiernov.spreadsheet.layouthelpers.*;

/**
 * This class consist of a menu with a list of available columns to allow the user to choose which one to display.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Columns extends JMenu {
	/**
	 * Creates a menu (for the menu bar) and menu items for the selection of the columns to display.
	 * @param	columnsName		list of available columns
	 * @param	defaultSelection	default columns to select
	 */
	public Columns(String[] columnsName, String[] defaultSelection) {
		super("Columns"); // creates the "Columns" menu
		ArrayList<JCheckBoxMenuItem> selectColumnsButtons = new ArrayList<JCheckBoxMenuItem>();
		for(String column: columnsName) { // add a check box menu item for every available columns
			JCheckBoxMenuItem columnButton = new JCheckBoxMenuItem(column);
			columnButton.setUI(new StayOpenCheckBoxMenuItemUI()); // use this layouthelper for the menu to stay open after checking an item
			for(String c: defaultSelection) {
				if(c.equals(column)) columnButton.setSelected(true); // Set default checkbox state
			}
			selectColumnsButtons.add(columnButton);
			this.add(columnButton);
		}
	}

	/**
	 * Returns the names of the currently checked columns.
	 * @return	columns currently selected
	 */
	public ArrayList<String> getSelected() {
		ArrayList<String> list = new ArrayList<String>();
		for(int i =0; i < this.getItemCount(); i++) {
			JMenuItem item = this.getItem(i);
			if(item.isSelected()) list.add(this.getItem(i).getText());
		}
		return list;
	}
}

