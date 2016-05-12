import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;


class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
   @Override
   protected void doClick(MenuSelectionManager msm) {
      menuItem.doClick(0);
   }
}

/**
 * <code>GUI</code> class
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac 
 * @version 1.0
 */
class GUI extends JFrame implements ActionListener {
	private DataCSV csv;
	private JMenuBar bar;
	
	private JTable results;
	private JComboBox sort;

	private ArrayList<Criteria> criterias;

	private JMenu options;
	private JMenu plus;
	private JMenu selectColumnsMenu;
	private JMenuItem quit;
	private JMenuItem rules;
	private JMenuItem aboutus;
	private JButton search;

	private ArrayList<JCheckBoxMenuItem> selectColumnsButtons;
	
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
		selectColumnsMenu = new JMenu("Columns");

		quit = new JMenuItem("Quitter");
		aboutus = new JMenuItem("À propos");
		rules = new JMenuItem("Règles");

		options.add(quit);
		bar.add(options);
		plus.add(aboutus);
		plus.add(rules);

		selectColumnsButtons = new ArrayList<JCheckBoxMenuItem>();

		for(String column: csv.getColumnsName()) {
			JCheckBoxMenuItem columnButton = new JCheckBoxMenuItem(column);
			columnButton.setUI(new StayOpenCheckBoxMenuItemUI());
			selectColumnsButtons.add(columnButton);
			selectColumnsMenu.add(columnButton);
		}

		bar.add(options);
		bar.add(plus);
		bar.add(selectColumnsMenu);
		this.setJMenuBar(bar);
		
		criterias = new ArrayList<Criteria>();
		criterias.add(new Criteria<String>("discipline", csv.getColumnValues("discipline")));
		criterias.add(new Criteria<String>("academie", csv.getColumnValues("academie")));

		search = new JButton("Search");
		sort = new JComboBox<String>(csv.getColumnsName());

		results = new JTable();

		quit.addActionListener(this);
		aboutus.addActionListener(this);
		rules.addActionListener(this);
		search.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());
		
		//Options
		Box choices = new Box(BoxLayout.Y_AXIS);
		for (Criteria c : criterias) {
			choices.add(c);
		}
		choices.add(sort);
		choices.add(search);
		this.add(choices, BorderLayout.WEST);

		setVisible(true);
		pack();
	}

	public void displayResults(Object[][] rowData, Object[] columnNames) {
		this.remove(results);
		results = new JTable(rowData, columnNames);
		results.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		results.doLayout();
		this.add(results, BorderLayout.CENTER);
		pack();
	}
	
	private void processSearch() {
		csv.clearFilterList();

		String strSort = (String)sort.getSelectedItem();
		/*String discipline = (String)disciplines.getSelectedItem();
		String academy = (String)academies.getSelectedItem();

		csv.addFilter(b -> b.get(csv.findIndexForColumn("discipline")).equals(discipline));
		csv.addFilter(b -> b.get(csv.findIndexForColumn("academie")).equals(academy));*/
		for (Criteria c : criterias) {
			if(!c.isEnabled()) continue;
			String value = (String)c.getSelectedItem();
			csv.addFilter(b -> b.get(csv.findIndexForColumn(c.getColumn())).equals(value));
		}
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).isEmpty()));
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).equals("ns")));

		ArrayList<Integer> indexes = new ArrayList<Integer>();

		for(JCheckBoxMenuItem column: selectColumnsButtons) {
			if(!column.isSelected()) continue;
			indexes.add(csv.findIndexForColumn(column.getText()));
		}

		if(indexes.size() < 1) return; // Don't search if no column was selected

		displayResults(csv.toArray(strSort, indexes), csv.getColumnsName(indexes));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) { System.exit(0); }
		else if (e.getSource() == aboutus) { JOptionPane.showMessageDialog(this, "Miaou", "Miaou",  JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == rules) { JOptionPane.showMessageDialog(this, "Nyan", "Règles", JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == search) { processSearch(); }
	}
}
