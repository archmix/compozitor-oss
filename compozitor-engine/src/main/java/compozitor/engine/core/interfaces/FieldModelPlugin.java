package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.FieldModel;
import compozitor.template.core.interfaces.TemplateContextData;

public interface FieldModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  T accept(FieldModel fieldModel);
}
