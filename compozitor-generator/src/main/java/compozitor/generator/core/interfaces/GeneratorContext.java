package compozitor.generator.core.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;

@Getter
public class GeneratorContext {
  private final TemplateContext context;

  private final TemplateMetadata metadata;

  GeneratorContext(TemplateMetadata metadata) {
    this.context = TemplateContext.create();
    this.metadata = metadata;
  }

  public static GeneratorContext create(TemplateMetadata metadata) {
    return new GeneratorContext(metadata);
  }

  public GeneratorContext add(TemplateContextData... entries) {
    for (TemplateContextData entry : this.iterable(entries)) {
      this.context.add(entry);
    }
    return this;
  }

  private Iterable<TemplateContextData> iterable(TemplateContextData... entries) {
    if (entries == null) {
      return new ArrayList<>();
    }

    return Arrays.asList(entries);
  }
}
