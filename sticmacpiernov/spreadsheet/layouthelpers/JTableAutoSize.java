package sticmacpiernov.spreadsheet.layouthelpers;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This class is a JTable with auto-sizing columns.
 * From http://www.java-forums.org/awt-swing/14821-jtable-jscrollpane-renders-partly-invisible.html#post50751
 *
 * @author DarrylBurke
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class JTableAutoSize extends JTable {
	/**
	 * Creates a new JTable which auto-resizes its columns.
	 * @param	rowData		the data for the new table
	 * @param	columnNames	names of each column
	 */
	public JTableAutoSize(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.doLayout();
	}
	/**
	 * Extends prepareRenderer() with auto-sizing columns width.
	 * @param	renderer	the TableCellRenderer to prepare
	 * @param	row		the row of the cell to render, where 0 is the first row
	 * @param	column		the column of the cell to render, where 0 is the first column
	 * @return	the Component under the event location
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component component = super.prepareRenderer(renderer, row, column);
		int rendererWidth = component.getPreferredSize().width;
		TableColumn tableColumn = getColumnModel().getColumn(column);
		tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		return component;
	}
}
