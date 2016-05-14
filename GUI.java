import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.table.*;


class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
   @Override
   protected void doClick(MenuSelectionManager msm) {
      menuItem.doClick(0);
   }
}

class JTableAutoSize extends JTable {
	public JTableAutoSize(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}
    @Override
       public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
           Component component = super.prepareRenderer(renderer, row, column);
           int rendererWidth = component.getPreferredSize().width;
           TableColumn tableColumn = getColumnModel().getColumn(column);
           tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
           return component;
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

	private ArrayList<Criterion> criteria;

	private JMenu options;
	private JMenu plus;
	private JMenu selectColumnsMenu;
	private JMenuItem quit;
	private JMenuItem aboutus;

	private JRadioButton inc;
	private JRadioButton dec;
	private JButton search;
	private JTextField numberOfElements;

	private JScrollPane scrollPane;

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

		numberOfElements = new JTextField(3);

		options.add(quit);
		bar.add(options);
		plus.add(aboutus);

		selectColumnsButtons = new ArrayList<JCheckBoxMenuItem>();

		for(String column: csv.getColumnsName()) {
			JCheckBoxMenuItem columnButton = new JCheckBoxMenuItem(column);
			columnButton.setUI(new StayOpenCheckBoxMenuItemUI());
			selectColumnsButtons.add(columnButton);
			selectColumnsMenu.add(columnButton);
		}

		bar.add(options);
		bar.add(selectColumnsMenu);
		bar.add(plus);
		this.setJMenuBar(bar);
		
		criteria = new ArrayList<Criterion>();
		criteria.add(new Criterion<String>("domaine", csv.getColumnValues("domaine")));
		criteria.add(new Criterion<String>("discipline", csv.getColumnValues("discipline")));
		criteria.add(new Criterion<String>("academie", csv.getColumnValues("academie")));
		criteria.add(new Criterion<String>("etablissement", csv.getColumnValues("etablissement")));

		search = new JButton("Search");
		sort = new JComboBox<String>(csv.getColumnsName());

		quit.addActionListener(this);
		aboutus.addActionListener(this);

		search.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());

		//Options
		Box choices = new Box(BoxLayout.Y_AXIS);
		for (Criterion c : criteria) {
			choices.add(c);
		}
		JPanel sortPanel = new JPanel();
		sortPanel.setLayout(new FlowLayout());
		sortPanel.add(new JLabel("Classement par : "));
		sortPanel.add(sort);
		ButtonGroup sortOrder = new ButtonGroup();
		dec = new JRadioButton("Décroissant");
		inc = new JRadioButton("Croissant");
		inc.addActionListener(this);
		dec.addActionListener(this);
		sortOrder.add(inc);
		inc.setSelected(true);
		sortOrder.add(dec);
		sortPanel.add(dec);
		sortPanel.add(inc);
		choices.add(sortPanel);
		
		JPanel limit = new JPanel();
		limit.setLayout(new FlowLayout());
		limit.add(new JLabel("Nombre de résultats :"));
		limit.add(numberOfElements);
		choices.add(limit);

		choices.add(search);

		this.add(choices, BorderLayout.WEST);

		this.scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
		pack();
	}

	/**
	 * Display the results on a JTable object
	 */
	public void displayResults(Object[][] rowData, Object[] columnNames) {
		if(results != null) this.remove(results);
		results = new JTableAutoSize(rowData, columnNames);
		results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		results.doLayout();
		this.scrollPane.getViewport().add(results);
		results.setFillsViewportHeight(true);
		pack();
	}
	
	/**
	 * Execute the requested search
	 */
	private void processSearch() {
		csv.clearFilterList();

		String strSort = (String)sort.getSelectedItem();
		for (Criterion c : criteria) {
			if(!c.isEnabled()) continue;
			String value = (String)c.getSelectedItem();
			csv.addFilter(b -> b.get(csv.findIndexForColumn(c.getColumn())).equals(value));
		}
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).isEmpty()));
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).equals("ns")));
		csv.addFilter(b -> !(b.get(csv.findIndexForColumn(strSort)).equals("nd")));

		ArrayList<Integer> indexes = new ArrayList<Integer>();

		for(JCheckBoxMenuItem column: selectColumnsButtons) {
			if(!column.isSelected()) continue;
			indexes.add(csv.findIndexForColumn(column.getText()));
		}

		if(indexes.size() < 1) return; // Don't search if no column was selected

		try {
			displayResults(csv.toArray(strSort, indexes, Integer.parseInt(numberOfElements.getText())), csv.getColumnsName(indexes));
		} catch (NumberFormatException e) {
			displayResults(csv.toArray(strSort, indexes, 10), csv.getColumnsName(indexes)); //We put 10 as a default value if no value is set for the number of results to display
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) { System.exit(0); }
		else if (e.getSource() == aboutus) { JOptionPane.showMessageDialog(this, "Copyleft - Julien Lemaire & Pierre-Emmanuel Novac - 2016", "A propos",  JOptionPane.INFORMATION_MESSAGE); }
		else if (e.getSource() == search) { processSearch(); }
		else if (e.getSource() == inc) { csv.setOrder(1); }
	       	else if (e.getSource() == dec) { csv.setOrder(-1); }	
	}
}
