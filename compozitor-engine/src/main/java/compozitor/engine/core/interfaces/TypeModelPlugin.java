package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;

public interface TypeModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  T accept(ProcessingContext context, TypeModel typeModel);
}
