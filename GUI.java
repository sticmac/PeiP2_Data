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

class ColumnsMenu extends JMenu {
	ColumnsMenu(String[] columnsName) {
		super("Columns");
		ArrayList<JCheckBoxMenuItem> selectColumnsButtons = new ArrayList<JCheckBoxMenuItem>();
		for(String column: columnsName) {
			JCheckBoxMenuItem columnButton = new JCheckBoxMenuItem(column);
			columnButton.setUI(new StayOpenCheckBoxMenuItemUI());
			for(String c: Config.defaultColumns) {
				if(c.equals(column)) columnButton.setSelected(true); // Set default checkbox state
			}
			selectColumnsButtons.add(columnButton);
			this.add(columnButton);
		}
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

	private JPanel criteria;

	private JMenu options;
	private JMenu plus;
	private JMenu selectColumnsMenu;
	private JMenuItem quit;
	private JMenuItem aboutus;

	private JButton search;
	private JTextField numberOfElements;

	private JScrollPane scrollPane;

	private ArrayList<JCheckBoxMenuItem> selectColumnsButtons;
	
	/**
	 * Constructeur de la <code>GUI</code>
	 */
	public GUI(DataCSV csv) {
		this.csv = csv;

		// Main layout
		getContentPane().setLayout(new BorderLayout());

		// Window title
		setTitle(Config::windowTitle);

		// Menu bar
		bar = new JMenuBar();

		// Options menu
		options = new JMenu("Options");
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		options.add(quit);
		bar.add(options);

		// Plus menu
		plus = new JMenu("Plus");
		aboutus = new JMenuItem("About");
		aboutus.addActionListener(this);
		plus.add(aboutus);
		bar.add(plus);

		bar.add(new ColumnsMenu(csv.getColumnsName()));

		this.setJMenuBar(bar); // Add menu bar to window

		// Search options box
		Box choices = new Box(BoxLayout.Y_AXIS);

		// Criteria selection panel
		criteria = new JPanel();
		criteria.setLayout(new BoxLayout(criteria, BoxLayout.PAGE_AXIS));

		// Configure Criterion
		Criterion.setNames(csv::getColumnsName);
		Criterion.setValues(csv::getColumnValues);
		Criterion.setRemove((b) -> {criteria.remove(b); this.pack();});

		// Default criteria
		for(String c: Config.defaultCriteria) criteria.add(new Criterion(c));

		choices.add(criteria);

		// Add criterion button
		choices.add(new JButton("Add") { { addActionListener( (b) -> {
				criteria.add(new Criterion("academie"));
				GUI.this.pack();
			});}});

		// Search options panel
		sort = new JComboBox<String>(csv.getColumnsName());

		// Sort panel
		JPanel sortPanel = new JPanel();
		sortPanel.setLayout(new FlowLayout());
		sortPanel.add(new JLabel("Sort by: "));
		sortPanel.add(sort);

		// Sort order
		ButtonGroup sortOrder = new ButtonGroup();
		JRadioButton dec = new JRadioButton("Descending");
		JRadioButton inc = new JRadioButton("Ascending");
		inc.setSelected(true);
		inc.addActionListener((e) -> csv.setOrder(1));
		dec.addActionListener((e) -> csv.setOrder(-1));
		sortOrder.add(inc);
		sortOrder.add(dec);
		sortPanel.add(dec);
		sortPanel.add(inc);

		choices.add(sortPanel);

		search = new JButton("Search");
		search.addActionListener(this);

		// Limit panel
		JPanel limit = new JPanel();
		numberOfElements = new JTextField(3);
		limit.setLayout(new FlowLayout());
		limit.add(new JLabel("Nombre de résultats :"));
		limit.add(numberOfElements);

		choices.add(limit);

		// Search button
		search = new JButton("Search");
		search.addActionListener(this);
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
		for (Component cp : criteria.getComponents()) {
			Criterion c = (Criterion)cp;
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
	}
}
