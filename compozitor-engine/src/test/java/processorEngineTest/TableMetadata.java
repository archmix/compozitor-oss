package processorEngineTest;

import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class TableMetadata implements TemplateContextData<TableMetadata> {
  private final String name;

  private final List<ColumnMetadata> columns;

  public static TableMetadata create(TypeModel model){
    List<ColumnMetadata> columns = new ArrayList<>();
    model.getFields().forEach(field -> columns.add(ColumnMetadata.create(field)));

    return new TableMetadata(model.getName(), columns);
  }

  @Override
  public String key() {
    return "Table";
  }
}
