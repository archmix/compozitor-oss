package processorEngineTest;

import compozitor.template.core.interfaces.LineDirective;
import compozitor.template.core.interfaces.TemplateEngine;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class InsertSQLDirective extends LineDirective {
  @Override
  protected String doRender(TemplateEngine engine, List<Variable> variables) {
    final TableMetadata metadata = variables.get(0).getValue();
    List<ColumnMetadata> columns = metadata.getColumns();

    Collector<CharSequence, ?, String> separator = Collectors.joining(",");

    final StringBuilder sql = new StringBuilder("INSERT INTO ");
    sql.append(metadata.getName()).append(" (");
    sql.append(columns.stream().map(ColumnMetadata::getName).collect(separator));

    sql.append(") VALUES (");
    sql.append(columns.stream().map(column -> "?").collect(separator));
    sql.append(")");

    return sql.toString();
  }

  @Override
  public String getName() {
    return "insertSQL";
  }
}
