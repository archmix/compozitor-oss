package compozitor.template.core.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Render extends compozitor.template.core.interfaces.LineDirective {

  @Override
  protected String doRender(TemplateEngine engine, List<Variable> variables) {
    Iterable<Object> contextObject = (Iterable<Object>) variables.get(0).getValue();
    if (contextObject == null) {
      return null;
    }

    String expression = variables.get(1).getValue().toString();
    expression = expression.replace("directive:", "#");

    String separator = variables.get(2).getValue().toString();

    Template template = TemplateBuilder.create(engine, "render").withResourceLoader(expression).build();
    TemplateContext templateContext = TemplateContext.create();
    List<String> renderedCode = new ArrayList<>();

    contextObject.forEach(it -> {
      templateContext.add("it", it);
      renderedCode.add(template.mergeToString(templateContext));
    });

    if (renderedCode.isEmpty()) {
      return null;
    }

    return renderedCode.stream().collect(Collectors.joining(separator));
  }
}
