package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.CodeGenerator;
import compozitor.generator.core.interfaces.GeneratorContext;
import compozitor.template.core.interfaces.TemplateContextData;

public class CodeEngine<T extends TemplateContextData<T>> {

  private final CodeGenerator<T> generator;

  CodeEngine() {
    this.generator = new CodeGenerator<>();
  }

  public static <T extends TemplateContextData<T>> CodeEngine<T> create() {
    return new CodeEngine<>();
  }

  public void generate(EngineContext<T> context, EngineListener listener) {
    context.apply((repository, template) -> {
      GeneratorContext generatorContext = GeneratorContext.create(template);

      this.generator.execute(generatorContext, repository).forEach(code -> {
        listener.accept(code);
      });
    });
  }
}
