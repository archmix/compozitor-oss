package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.template.core.interfaces.TemplateContextData;

public interface MethodModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  T accept(ProcessingContext context, MethodModel fieldModel);
}
