package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class CodeGenerationCategoryContext<T extends TemplateContextData<T>> {
  private final Map<CodeGenerationCategory, MetaModelRepository<T>> metadata;

  private final Map<CodeGenerationCategory, List<TemplateMetadata>> templates;

  CodeGenerationCategoryContext() {
    this.metadata = new HashMap<>();
    this.templates = new HashMap<>();
  }

  public static <T extends TemplateContextData<T>> CodeGenerationCategoryContext create() {
    return new CodeGenerationCategoryContext();
  }

  public void add(CodeGenerationCategory type, MetaModelRepository<T> repository) {
    this.metadata.put(type, repository);
  }

  public void add(CodeGenerationCategory type, TemplateMetadata template) {
    List<TemplateMetadata> templates = this.templates.get(type);
    if (templates == null) {
      templates = new ArrayList<>();
      this.templates.put(type, templates);
    }
    templates.add(template);
  }

  public final void apply(BiConsumer<MetaModelRepository<T>, TemplateMetadata> consumer) {
    this.templates.keySet().forEach(type -> {
      this.templates.get(type).forEach(template -> {
        consumer.accept(this.metadata.get(type), template);
      });
    });
  }
}
