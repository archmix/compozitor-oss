package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.template.core.interfaces.TemplateContextData;

public interface FieldModelPlugin<T extends TemplateContextData<T>> {
  <T extends TemplateContextData<T>> T accept(FieldModel fieldModel);

  EngineCategory engineType();
}
