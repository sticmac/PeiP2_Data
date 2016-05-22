package sticmacpiernov.spreadsheet.struct;

/**
 * Represent a filter for filter the CSV's data.
 * Used for passing Criterion from GUI to the DataCSV filter.
 * TODO: add configurable method support (other than equals()).
 *
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public interface Filter {
	/**
	 * Returns the column the filter acts on.
	 * @return	column for this filter
	 */
	public String getColumn();
	/**
	 * Returns the filter's value.
	 * @return	the filter value
	 */
	public String getValue();
}
