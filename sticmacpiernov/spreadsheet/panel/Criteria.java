package sticmacpiernov.spreadsheet.panel;

import sticmacpiernov.spreadsheet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public abstract class Criteria extends JPanel implements ActionListener {
	public Criteria(DataCSV csv) {
		super();
		// Criteria selection panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Configure Criterion
		Criterion.setNames(csv::getColumnsName);
		Criterion.setValues(csv::getColumnValues);
		Criterion.setRemove((b) -> {this.remove(b); this.pack();});

		// Default criteria
		for(String c: Config.defaultCriteria) this.add(new Criterion(c));
	}

	protected abstract void pack();

	@Override
	public void actionPerformed(ActionEvent e) {
		this.add(new Criterion());
		this.pack();
	}

	public ArrayList<Filter> getFilters() {
		ArrayList<Filter> list = new ArrayList<Filter>();
		for (Component cp : this.getComponents()) {
			Criterion c = (Criterion)cp;
			if(!c.isEnabled()) continue;
			list.add(c);
		}
		return list;
	}
}
