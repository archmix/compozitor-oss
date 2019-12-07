package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;

public interface TypeModelPlugin<T extends TemplateContextData<T>> {
  <T extends TemplateContextData<T>> T accept(TypeModel typeModel);

  EngineCategory engineType();
}
