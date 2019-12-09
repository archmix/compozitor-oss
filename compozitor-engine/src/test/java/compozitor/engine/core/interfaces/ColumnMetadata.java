package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.FieldModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ColumnMetadata {
  private final String name;

  public static ColumnMetadata create(FieldModel model){
    return new ColumnMetadata(model.getName());
  }
}
