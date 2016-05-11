import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;

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
	private JComboBox sort;

	private JComboBox disciplines;
	private JComboBox academies;

	private JMenu options;
	private JMenu plus;
	private JMenuItem quit;
	private JMenuItem rules;
	private JMenuItem aboutus;
	private JButton search;
	
	/**
	 * Constructeur de la <code>GUI</code>
	 */
	public GUI(DataCSV csv) {
		this.csv = csv;

		//General options about 
		setTitle("Aide au choix des masters");

		//Menu set
		bar = new JMenuBar();
		options = new JMenu("Options");
		plus = new JMenu("Plus");
		quit = new JMenuItem("Quitter");
		aboutus = new JMenuItem("À propos");
		rules = new JMenuItem("Règles");
		options.add(quit);
		bar.add(options);
		plus.add(aboutus);
		plus.add(rules);
		bar.add(plus);
		this.setJMenuBar(bar);
		
		search = new JButton("Search");
		disciplines = new JComboBox<String>(csv.getColumnValues("discipline"));
		academies = new JComboBox<String>(csv.getColumnValues("academie"));
		sort = new JComboBox<String>(csv.getColumnsName());

		quit.addActionListener(this);
		aboutus.addActionListener(this);
		rules.addActionListener(this);
		search.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());
		
		//Options
		//JPanel options = new JPanel();
		//options.setLayout(new GridLayout(3,1));
		Box choices = new Box(BoxLayout.Y_AXIS);
		choices.add(disciplines);
		choices.add(academies);
		choices.add(sort);
		choices.add(search);
		this.add(choices, BorderLayout.WEST);

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
		csv.clearFilterList();

		String discipline = (String)disciplines.getSelectedItem();
		String academy = (String)academies.getSelectedItem();
		String strSort = (String)sort.getSelectedItem();

		csv.addFilter(b -> b.get(csv.findIndexForColumn("discipline")).equals(discipline));
		csv.addFilter(b -> b.get(csv.findIndexForColumn("academie")).equals(academy));
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).isEmpty()));
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).equals("ns")));
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		indexes.add(6);
		indexes.add(2);
		indexes.add(csv.findIndexForColumn(strSort));
		displayResults(csv.toArray(strSort), csv.getColumnsName(indexes));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) { System.exit(0); }
		else if (e.getSource() == aboutus) { JOptionPane.showMessageDialog(this, "Miaou", "Miaou",  JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == rules) { JOptionPane.showMessageDialog(this, "Nyan", "Règles", JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == search) { processSearch(); }
	}
}
