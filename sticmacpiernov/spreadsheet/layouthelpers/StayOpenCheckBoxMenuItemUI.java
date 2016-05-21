package sticmacpiernov.spreadsheet.layouthelpers;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.MenuSelectionManager;

/**
 * This class is a BasicCheckBoxMenuItemUI which does nothing when clicked.
 * Used to keep the menu open when checking a box.
 * From https://community.oracle.com/message/5726401#5726401
 *
 * @author DarrylBurke
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
	/**
	 * Modified doClick for doing nothing.
	 * @param	sm	a MenuSelectionManager
	 */
	@Override
	protected void doClick(MenuSelectionManager msm) {
		menuItem.doClick(0);
	}
}
