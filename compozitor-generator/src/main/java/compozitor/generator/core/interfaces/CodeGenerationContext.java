package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class CodeGenerationContext {
  private final TemplateContext context;

  private final TemplateMetadata metadata;

  CodeGenerationContext(TemplateMetadata metadata) {
    this.context = TemplateContext.create();
    this.metadata = metadata;
  }

  public static CodeGenerationContext create(TemplateMetadata metadata) {
    return new CodeGenerationContext(metadata);
  }

  public CodeGenerationContext add(TemplateContextData<?>... entries) {
    for (TemplateContextData<?> entry : this.iterable(entries)) {
      this.context.add(entry);
    }
    return this;
  }

  private Iterable<TemplateContextData<?>> iterable(TemplateContextData<?>... entries) {
    if (entries == null) {
      return new ArrayList<>();
    }

    return Arrays.asList(entries);
  }
}
