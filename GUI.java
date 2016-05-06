import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Classe de la <code>GUI</code>
 * @author Julien Lemaire
 * @author Guillaume Casagrande
 * @version 1.0
 */
class GUI extends JFrame implements ActionListener {
	//Elements du menu
	private DataCSV csv;
	private JMenuBar bar;
	private JTable results;
	private JComboBox disciplines;
	private JMenu game;
	private JMenu help;
	private JMenuItem quit;
	private JMenuItem rules;
	private JMenuItem aboutus;
	private JButton search;
	
	/**
	 * Constructeur de la <code>GUI</code>
	 */
	public GUI(DataCSV csv) {
		this.csv = csv;

		setTitle("MiaouNyan");
		setResizable(false);

		//Old Menus
		bar = new JMenuBar();
		game = new JMenu("Jeu");
		help = new JMenu("Aide");
		quit = new JMenuItem("Quitter");
		aboutus = new JMenuItem("À propos");
		rules = new JMenuItem("Règles");
		game.add(quit);
		bar.add(game);
		help.add(aboutus);
		help.add(rules);
		bar.add(help);
		this.setJMenuBar(bar);
		
		//New things
		search = new JButton("Search");
		disciplines = new JComboBox<String>(csv.getDisciplines());

		quit.addActionListener(this);
		aboutus.addActionListener(this);
		rules.addActionListener(this);
		search.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());
		this.add(search, BorderLayout.WEST);
		this.add(disciplines, BorderLayout.EAST);

		setVisible(true);
		pack();
	}

	public void displayResults(Object[][] rowData, Object[] columnNames) {
		results = new JTable(rowData, columnNames);
		results.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		results.doLayout();
		this.add(results, BorderLayout.CENTER);
		pack();
	}
	
	private void processSearch() {
		String discipline = (String)disciplines.getSelectedItem();
		csv.addFilter(b -> b.get(csv.findIndexForColumn("discipline")).equals(discipline));
		csv.addFilter(b -> !b.get(csv.findIndexForColumn("taux_dinsertion")).isEmpty());
		displayResults(csv.toArray(), csv.getColumnsName()); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) { System.exit(0); }
		else if (e.getSource() == aboutus) { JOptionPane.showMessageDialog(this, "Miaou", "Miaou",  JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == rules) { JOptionPane.showMessageDialog(this, "Nyan", "Règles", JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == search) { processSearch(); }
	}
}
