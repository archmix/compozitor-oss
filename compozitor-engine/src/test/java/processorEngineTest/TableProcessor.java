package processorEngineTest;

import com.google.common.collect.Sets;
import compozitor.engine.core.interfaces.ProcessorEngine;
import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.processor.core.interfaces.Processor;
import processorEngineTest.Table;
import processorEngineTest.TableCategory;
import processorEngineTest.TableMetadata;

import java.util.Set;

public class TableProcessor extends ProcessorEngine<TableMetadata> {
  @Override
  protected CodeGenerationCategory category() {
    return TableCategory.INSTANCE;
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Sets.newHashSet(Table.class.getName());
  }
}
