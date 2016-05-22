package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;

/**
 * This class consist of a menu allowing the user to quit the software.
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class Options extends JMenu {
	/**
	 * Creates a menu (for the menu bar) with a menu item to quit the software.
	 */
	public Options() {
		super("Options");
		this.add(new JMenuItem("Quit") { { addActionListener((e) -> System.exit(0)); } } );
	}
}

