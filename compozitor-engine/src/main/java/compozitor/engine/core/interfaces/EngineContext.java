package compozitor.engine.core.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.generator.core.interfaces.TemplateMetadata;
import compozitor.template.core.interfaces.TemplateContextData;

public class EngineContext<T extends TemplateContextData<T>> {
  private final Map<EngineType, MetamodelRepository<T>> metadata;

  private final Map<EngineType, List<TemplateMetadata>> templates;

  EngineContext() {
    this.metadata = new HashMap<>();
    this.templates = new HashMap<>();
  }

  public static <T extends TemplateContextData<T>> EngineContext<T> create() {
    return new EngineContext<>();
  }

  public void add(EngineType type, MetamodelRepository<T> repository) {
    this.metadata.put(type, repository);
  }

  public void add(EngineType type, TemplateMetadata template) {
    List<TemplateMetadata> templates = this.templates.get(type);
    if (templates == null) {
      templates = new ArrayList<>();
      this.templates.put(type, templates);
    }
    templates.add(template);
  }

  public void apply(BiConsumer<MetamodelRepository<T>, TemplateMetadata> consumer) {
    this.templates.keySet().forEach(type -> {
      this.templates.get(type).forEach(template -> {
        consumer.accept(this.metadata.get(type), template);
      });
    });
  }
}
