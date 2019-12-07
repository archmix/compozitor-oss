package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.generator.core.interfaces.TemplateMetadata;
import compozitor.template.core.interfaces.TemplateContextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class EngineContext<T extends TemplateContextData<T>> {
  private final Map<EngineCategory, MetamodelRepository<T>> metadata;

  private final Map<EngineCategory, List<TemplateMetadata>> templates;

  EngineContext() {
    this.metadata = new HashMap<>();
    this.templates = new HashMap<>();
  }

  public static <T extends TemplateContextData<T>> EngineContext create(){
    return new EngineContext();
  }

  public void add(EngineCategory type, MetamodelRepository<T> repository) {
    this.metadata.put(type, repository);
  }

  public void add(EngineCategory type, TemplateMetadata template) {
    List<TemplateMetadata> templates = this.templates.get(type);
    if (templates == null) {
      templates = new ArrayList<>();
      this.templates.put(type, templates);
    }
    templates.add(template);
  }

  public final void apply(BiConsumer<MetamodelRepository<T>, TemplateMetadata> consumer) {
    this.templates.keySet().forEach(type -> {
      this.templates.get(type).forEach(template -> {
        consumer.accept(this.metadata.get(type), template);
      });
    });
  }
}
