package processorEngineTest;

import compozitor.processor.core.interfaces.FieldModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ColumnMetadata {
  private final String name;

  public static ColumnMetadata create(FieldModel model){
    return new ColumnMetadata(model.getName());
  }
}
