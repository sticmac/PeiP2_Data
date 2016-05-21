package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;

public class Options extends JMenu {
	public Options() {
		super("Options");
		this.add(new JMenuItem("Quit") { { addActionListener((e) -> System.exit(0)); } } );
	}
}

