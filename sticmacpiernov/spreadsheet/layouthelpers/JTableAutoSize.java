// From http://stackoverflow.com/a/25570812/1447751
package sticmacpiernov.spreadsheet.layouthelpers;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class JTableAutoSize extends JTable {
	public JTableAutoSize(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.doLayout();
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
