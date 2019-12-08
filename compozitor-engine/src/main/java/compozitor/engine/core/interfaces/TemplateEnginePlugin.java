package compozitor.engine.core.interfaces;

import compozitor.template.core.interfaces.TemplateEngineBuilder;

public interface TemplateEnginePlugin extends CodeGenerationCategoryPlugin {
  void accept(TemplateEngineBuilder builder);
}
