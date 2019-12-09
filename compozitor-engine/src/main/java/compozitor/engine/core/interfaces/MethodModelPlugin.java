package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.MethodModel;
import compozitor.template.core.interfaces.TemplateContextData;

public interface MethodModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  T accept(MethodModel fieldModel);
}
