// From https://community.oracle.com/message/5726401#5726401

package sticmacpiernov.spreadsheet.layouthelpers;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.MenuSelectionManager;

public class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
	@Override
	protected void doClick(MenuSelectionManager msm) {
		menuItem.doClick(0);
	}
}
