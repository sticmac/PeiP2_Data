package sticmacpiernov.spreadsheet.menu;

import javax.swing.*;

public class Plus extends JMenu {
	public Plus() {
		super("Plus");
		this.add(new JMenuItem("About") { { addActionListener((e) -> JOptionPane.showMessageDialog(this, "Copyleft - Julien Lemaire & Pierre-Emmanuel Novac - 2016", "A propos",  JOptionPane.INFORMATION_MESSAGE)); } } );
	}
}

