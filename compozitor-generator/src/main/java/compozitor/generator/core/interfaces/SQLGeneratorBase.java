package compozitor.generator.core.interfaces;

public class SQLGeneratorBase {

	public static final String COMMA = ", ";

	private static SQLGeneratorBase current = new SQLGeneratorBase();

	protected SQLGeneratorBase() {
		super();
	}

	public static SQLGeneratorBase current() {
		return current;
	}

	@SuppressWarnings("unused")
	public String insertSQL(String entity, Iterable<String> columns) {
		StringBuffer SQL;

		SQL = new StringBuffer("INSERT INTO ");
		SQL.append(entity).append(" (");

		for (String column : columns) {
			SQL.append(column).append(SQLGeneratorBase.COMMA);
		}

		this.parse(SQL);
		SQL.append(") VALUES (");

		for (String column : columns) {
			SQL.append("?, ");
		}

		this.parse(SQL);
		SQL.append(")");

		return SQL.toString();
	}

	public String deleteSQL(String entity, Iterable<String> indexes) {
		StringBuffer SQL;

		SQL = new StringBuffer(this.deleteSQL(entity));

		for (String column : indexes) {
			SQL.append(" AND ").append(column).append(" = ? ");
		}

		return SQL.toString().replaceFirst(" AND ", " WHERE ");
	}

	public String deleteBySQL(String entity, String column) {
		StringBuffer SQL;

		SQL = new StringBuffer(deleteSQL(entity));
		SQL.append(" WHERE ").append(column).append(" = ?");

		return SQL.toString();
	}

	public String updateSQL(String entity, Iterable<String> columns, Iterable<String> indexes) {
		StringBuffer SQL;

		SQL = new StringBuffer("UPDATE ");
		SQL.append(entity).append(" SET ");

		for (String column : columns) {
			SQL.append(column).append(" = ?").append(SQLGeneratorBase.COMMA);
		}

		this.parse(SQL);
		SQL.append(" WHERE ");

		for (String column : indexes) {
			SQL.append(column).append(" = ? ").append("AND ");
		}

		this.remove(SQL, "AND ");

		return SQL.toString();
	}

	public String selectSQL(String entity, Iterable<String> columns, Iterable<String> indexes) {
		StringBuffer SQL;

		SQL = new StringBuffer(selectAllSQL(entity, columns));
		SQL.append(" WHERE ");

		for (String column : indexes) {
			SQL.append(column).append(" = ? ").append("AND ");
		}

		this.remove(SQL, "AND ");

		return SQL.toString();
	}

	public String selectAllSQL(String entity, Iterable<String> columns) {
		StringBuffer SQL;

		SQL = new StringBuffer("SELECT ");

		for (String column : columns) {
			SQL.append(column).append(SQLGeneratorBase.COMMA);
		}

		this.parse(SQL);
		SQL.append(" FROM ").append(entity);

		return SQL.toString();
	}

	public String selectAllFKsSQL(String entity, Iterable<String> columns, Iterable<String> constraints) {
		StringBuffer SQL;

		SQL = new StringBuffer(selectAllSQL(entity, columns));
		SQL.append(" WHERE ");

		for (String column : constraints) {
			SQL.append(column).append(" = ? ").append("AND ");
		}

		if (SQL.indexOf("?") > -1) {
			this.remove(SQL, "AND ");
		}

		return SQL.toString();
	}

	public String selectBySQL(String entity, Iterable<String> columns, String column) {
		StringBuffer SQL;

		SQL = new StringBuffer(selectAllSQL(entity, columns));
		SQL.append(" WHERE ").append(column).append(" = ?");

		return SQL.toString();
	}

	/**
	 * <b>CAUTION</b> It has no WHERE Clause, so it will clean table entirely
	 */
	public String deleteSQL(String entity) {
		StringBuffer SQL;

		SQL = new StringBuffer("DELETE FROM ");
		SQL.append(entity);

		return SQL.toString();
	}

	public void removeLastComma(StringBuffer query) {
		this.parse(query);
	}

	private void parse(StringBuffer SQL) {
		this.remove(SQL, SQLGeneratorBase.COMMA);
	}

	private void remove(StringBuffer SQL, String value) {
		if (SQL.toString().contains(value)) {
			SQL.delete(SQL.lastIndexOf(value), SQL.length());
		}
	}
}