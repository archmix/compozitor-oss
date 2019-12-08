package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.TemplateRepository;

public interface TemplatePlugin extends CodeGenerationCategoryPlugin {
  void accept(TemplateRepository templateRepository);
}
