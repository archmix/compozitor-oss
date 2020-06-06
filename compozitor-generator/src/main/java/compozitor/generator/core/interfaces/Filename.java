package compozitor.generator.core.interfaces;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateEngine;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of = "value")
@RequiredArgsConstructor(staticName = "create")
public class Filename {
  private final String value;

  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }

  public Filename merge(TemplateEngine engine, TemplateContext context) {
    Template filenameTemplate = TemplateBuilder.create(engine, "Code")
      .withResourceLoader(new StringInputStream(this.value.toString()))
      .build();

    return Filename.create(filenameTemplate.mergeToString(context));
  }
}
