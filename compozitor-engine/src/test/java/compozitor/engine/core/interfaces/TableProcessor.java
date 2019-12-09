package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.CodeGenerationCategory;

public class TableProcessor extends ProcessorEngine<TableMetadata> {
  @Override
  protected CodeGenerationCategory category() {
    return TableCategory.INSTANCE;
  }
}
