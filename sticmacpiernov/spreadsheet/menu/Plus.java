package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;

/**
 * This class consist of a menu containing information about the software.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Plus extends JMenu {
	/**
	 * Creates a menu (for the menu bar) with a menu item to show information about the software.
	 */
	public Plus() {
		super("Plus");
		this.add(new JMenuItem("About") { { addActionListener((e) -> JOptionPane.showMessageDialog(this, "Copyleft - Julien Lemaire & Pierre-Emmanuel Novac - 2016", "About",  JOptionPane.INFORMATION_MESSAGE)); } } );
	}
}

