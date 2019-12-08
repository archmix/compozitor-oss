package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.template.core.interfaces.TemplateEngine;

public class CodeGenerationCategoryEngine<T extends TemplateContextData<T>> {

  private final CodeGenerationEngine<T> generator;

  CodeGenerationCategoryEngine() {
    this.generator = new CodeGenerationEngine<>();
  }

  public static <T extends TemplateContextData<T>> CodeGenerationCategoryEngine<T> create() {
    return new CodeGenerationCategoryEngine<>();
  }

  public void generate(TemplateEngine engine, CodeGenerationCategoryContext<T> context, GeneratedCodeListener listener) {
    context.apply((repository, template) -> {
      CodeGenerationContext generatorContext = CodeGenerationContext.create(template);

      this.generator.execute(generatorContext, repository, engine).forEach(code -> {
        listener.accept(code);
      });
    });
  }
}
