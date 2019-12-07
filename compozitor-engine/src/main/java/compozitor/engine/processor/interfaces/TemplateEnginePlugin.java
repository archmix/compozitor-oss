package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

public interface TemplateEnginePlugin {
  void accept(TemplateEngineBuilder builder);

  EngineCategory engineType();
}
