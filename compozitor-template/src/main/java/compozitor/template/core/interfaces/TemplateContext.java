package compozitor.template.core.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.velocity.VelocityContext;

public class TemplateContext {
  private final VelocityContext context;

  TemplateContext() {
    this(new VelocityContext());
  }

  TemplateContext(VelocityContext context) {
    this.context = context;
  }

  public static TemplateContext create() {
    return new TemplateContext();
  }

  public static TemplateContext valueOf(VelocityContext context) {
    return new TemplateContext(context);
  }

  public TemplateContext add(TemplateContextData... entries) {
    for (TemplateContextData entry : this.iterable(entries)) {
      this.context.put(entry.key(), entry.value());
    }
    return this;
  }

  private Iterable<TemplateContextData> iterable(TemplateContextData... entries) {
    if (entries == null) {
      return new ArrayList<>();
    }

    return Arrays.asList(entries);
  }

  VelocityContext getVelocityContext() {
    return context;
  }
}
