package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.TemplateRepository;
import compozitor.template.core.interfaces.TemplateEngine;

public interface TemplatePlugin extends CodeGenerationCategoryPlugin {
  void accept(TemplateEngine templateEngine, TemplateRepository templateRepository);
}
