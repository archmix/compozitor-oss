package compozitor.generator.core.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TemplateRepository implements Iterable<TemplateMetadata> {
  private final List<TemplateMetadata> templates;

  TemplateRepository() {
    this.templates = new ArrayList<>();
  }

  public static TemplateRepository create() {
    return new TemplateRepository();
  }

  @Override
  public Iterator<TemplateMetadata> iterator() {
    return this.templates.iterator();
  }

  public TemplateMetadata addRegularTemplate() {
    return this.add(TemplateMetadata.regularTemplate());
  }

  public TemplateMetadata addRegularResourceTemplate() {
    return this.add(TemplateMetadata.regularResourceTemplate());
  }

  public TemplateMetadata addTestTemplate() {
    return this.add(TemplateMetadata.testTemplate());
  }

  public TemplateMetadata addTestResourceTemplate() {
    return this.add(TemplateMetadata.testResourceTemplate());
  }

  private TemplateMetadata add(TemplateMetadata template) {
    this.templates.add(template);
    return template;
  }
}
